package com.insilicosoft.portal.svc.simulationinvoke.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insilicosoft.portal.svc.simulationinvoke.event.SimulationCreate;
import com.insilicosoft.portal.svc.simulationinvoke.value.AppManagerResponse;

@Service
public class RestInvocationServiceImpl implements InvocationService {

  private static final Logger logger = LoggerFactory.getLogger(RestInvocationServiceImpl.class);
  private static final ObjectMapper objectMapper = new ObjectMapper();

  private final RestClient restClientAppMgr;
  private final RestClient restClientResults;

  public RestInvocationServiceImpl(final RestClient restClientAppMgr,
                                   final RestClient restClientResults) {
    this.restClientAppMgr = restClientAppMgr;
    this.restClientResults = restClientResults;
  }

  @Override
  public void invoke(SimulationCreate simulationCreate) {
    logger.debug("~invoke() : Called for '{}'", simulationCreate);

    String body = null;
    try {
      body = objectMapper.writeValueAsString(simulationCreate);
    } catch (JsonProcessingException e) {
      logger.error("~invoke() : JSON processing exception for '{}'", simulationCreate);
    }

    if (body == null)
      return;

    final long simulationId = simulationCreate.simulationId();

    final ResponseEntity<AppManagerResponse> respEntAppMgr = restClientAppMgr.post()
                                                                             .contentType(MediaType.APPLICATION_JSON)
                                                                             .body(body)
                                                                             .retrieve()
                                                                             .toEntity(AppManagerResponse.class);
    final AppManagerResponse respAppMgr = respEntAppMgr.getBody();
    // This is a UUID assigned to the simulation by app-manager
    final String appMgrId = respAppMgr.success().id();
    logger.debug("~invoke() : Simulation '{}' invocation generated an app-manager assigned UUID of '{}'",
                 simulationId, appMgrId);

    final ResponseEntity<Void> respEntResults = restClientResults.patch()
                                                                 .uri("/results/{id}", simulationId)
                                                                 .contentType(MediaType.APPLICATION_JSON)
                                                                 .body("{ \"appManagerId\": \"" + appMgrId + "\" }")
                                                                 .retrieve()
                                                                 .toBodilessEntity();
    if (!respEntResults.getStatusCode().is2xxSuccessful())
      logger.warn("~invoke() : Simulation '{}' results-svc patch to update app-manager assigned UUID to '{}' unsuccessful",
                  simulationId, appMgrId);

  }

}