package com.insilicosoft.portal.svc.simulationinvoke.config;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import com.insilicosoft.portal.svc.simulationinvoke.event.SimulationCreate;
import com.insilicosoft.portal.svc.simulationinvoke.service.InvocationService;

import reactor.core.publisher.Flux;

@Configuration
public class SimulationInvokeConfig {

  @Bean
  RestClient restClient(final @Value("${URL_APP_MANAGER:http://app-manager:8080/}")
                              String appManagerUrl) {
    return RestClient.builder().baseUrl(appManagerUrl).build();
  }

  @Bean
  Consumer<Flux<SimulationCreate>> simulationInvoke(final InvocationService invocationService) {
    return flux -> flux.doOnNext(simulationCreate -> invocationService.invoke(simulationCreate))
                       .subscribe();
  }

}