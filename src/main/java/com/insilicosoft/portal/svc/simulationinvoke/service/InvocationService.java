package com.insilicosoft.portal.svc.simulationinvoke.service;

import com.insilicosoft.portal.svc.simulationinvoke.event.SimulationMessage;

public interface InvocationService {

  /**
   * Invoke a simulation.
   * 
   * @param simulationMessage Simulation to invoke.
   */
  void invoke(SimulationMessage simulationMessage);

}