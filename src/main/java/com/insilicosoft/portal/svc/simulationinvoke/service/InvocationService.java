package com.insilicosoft.portal.svc.simulationinvoke.service;

import com.insilicosoft.portal.svc.simulationinvoke.event.SimulationCreate;

public interface InvocationService {

  /**
   * Invoke a simulation.
   * 
   * @param simulationCreate Simulation to invoke (based on create event record).
   */
  void invoke(SimulationCreate simulationCreate);

}