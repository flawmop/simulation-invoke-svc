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

  private final RestClient restClient;

  public RestInvocationServiceImpl(final RestClient restClient) {
    this.restClient = restClient;
  }

  @Override
  public void invoke(SimulationCreate simulationCreate) {
    logger.debug("~invoke() : Called for '{}'", simulationCreate);
    String body = "";
    try {
      body = objectMapper.writeValueAsString(simulationCreate);
    } catch (JsonProcessingException e) {
      body = "{ 'error': '" + e.getMessage() + "' }";
    }
    final ResponseEntity<AppManagerResponse> responseEntity = restClient.post()
                                                                        .contentType(MediaType.APPLICATION_JSON)
                                                                        .body(body)
                                                                        .retrieve()
                                                                        .toEntity(AppManagerResponse.class);
    final AppManagerResponse response = responseEntity.getBody();
    // This is a UUID assigned to the simulation by app-manager
    final String responseId = response.success().id();

    logger.debug("~invoke() : app-manager assigned UUID is '{}'", responseId);
  }

}