package com.insilicosoft.portal.svc.simulationinvoke.service;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.insilicosoft.portal.svc.simulationinvoke.event.SimulationMessage;

import reactor.core.publisher.Flux;

@Configuration
public class InvokeSimulation {

  private static final Logger log = LoggerFactory.getLogger(InvokeSimulation.class);

  @Bean
  Consumer<Flux<SimulationMessage>> invokeSimulation() {
    return flux -> flux.map(simulationMessage -> simulationMessage.modelId())
                       .doOnNext(modelId -> log.info("The simulation with model id {} is invoked", modelId))
                       .subscribe();
  }

}