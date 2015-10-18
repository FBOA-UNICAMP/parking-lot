package org.eclipse.concierge.example.parking_lot.sensor.monitor.service;

import org.eclipse.concierge.example.parking_lot.sensor.impl.Sensor.SensorStatus;


public interface SensorMonitorServiceMonitorerInterface {
	
	public void sensorMonitorServiceDidUpdate(int sensorId, SensorStatus status);
	
}
