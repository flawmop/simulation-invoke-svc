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
  RestClient restClientAppMgr(final @Value("${URL_APP_MANAGER:http://app-manager:8080/}")
                                    String appManagerUrl) {
    return RestClient.builder().baseUrl(appManagerUrl).build();
  }

  @Bean
  RestClient restClientResults(final @Value("${URL_RESULTS_SVC:http://results-svc:9004/}")
                                     String resultsSvcUrl) {
    return RestClient.builder().baseUrl(resultsSvcUrl).build();
  }

  @Bean
  Consumer<Flux<SimulationCreate>> simulationCreate(final InvocationService invocationService) {
    return flux -> flux.doOnNext(simulationCreate -> invocationService.invoke(simulationCreate))
                       .subscribe();
  }

}