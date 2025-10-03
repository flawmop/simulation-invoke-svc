package com.insilicosoft.portal.svc.simulationinvoke.value;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
  @JsonSubTypes.Type(SuccessData.class)
})
public interface Success {
  String id();
  String ip();
}