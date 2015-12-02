package org.eclipse.concierge.example.parking_lot.sensor.monitor.service;

import org.eclipse.concierge.example.parking_lot.sensor.state.SensorState;

public interface SensorMonitorServiceMonitorerInterface {
	
	public void sensorMonitorServiceDidUpdate(int sensorId, SensorState status);
	
}
