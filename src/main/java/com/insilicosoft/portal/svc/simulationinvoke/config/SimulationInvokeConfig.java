package com.insilicosoft.portal.svc.simulationinvoke.config;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.insilicosoft.portal.svc.simulationinvoke.event.SimulationMessage;

import reactor.core.publisher.Flux;

@Configuration
public class SimulationInvokeConfig {

  private static final Logger log = LoggerFactory.getLogger(SimulationInvokeConfig.class);

  @Bean
  Consumer<Flux<SimulationMessage>> simulationInvoke() {
    return flux -> flux.doOnNext(simulationMessage -> log.info("The simulation {} is invoked", simulationMessage))
                       .subscribe();
  }

}