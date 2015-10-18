package org.eclipse.concierge.example.parking_lot.sensor.monitor.service;

public interface SensorMonitorServiceMonitorerInterface {
	
	public enum SensorStatus {
	    FREE,
	    BUSY,
	    UNKNOWN;
	}
	
	public void sensorMonitorServiceDidUpdate(int sensorId, SensorStatus status);
	
}
