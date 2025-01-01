package com.insilicosoft.portal.svc.simulationinvoke.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insilicosoft.portal.svc.simulationinvoke.event.SimulationMessage;

@Service
public class RestInvocationServiceImpl implements InvocationService {

  private static final Logger logger = LoggerFactory.getLogger(RestInvocationServiceImpl.class);
  private static final ObjectMapper objectMapper = new ObjectMapper();

  private final RestClient restClient;

  public RestInvocationServiceImpl(final RestClient restClient) {
    this.restClient = restClient;
  }

  @Override
  public void invoke(SimulationMessage simulationMessage) {
    logger.debug("~invoke() : Called for {}", simulationMessage);
    String body = "";
    try {
      body = objectMapper.writeValueAsString(simulationMessage);
    } catch (JsonProcessingException e) {
      body = "{ 'error': '" + e.getMessage() + "' }";
    }
    restClient.post().contentType(MediaType.APPLICATION_JSON).body(body).retrieve().toBodilessEntity();
  }

}