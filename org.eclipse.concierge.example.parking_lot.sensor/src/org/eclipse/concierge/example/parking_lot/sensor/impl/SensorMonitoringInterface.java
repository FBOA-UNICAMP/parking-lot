package org.eclipse.concierge.example.parking_lot.sensor.impl;

import org.eclipse.concierge.example.parking_lot.sensor.impl.Sensor.SensorStatus;

public interface SensorMonitoringInterface {
	
	public void sensorStatusDidChange(int id, SensorStatus status);
	
	public void sensorStatusTimeToUpdate(int id, SensorStatus status);
	
}