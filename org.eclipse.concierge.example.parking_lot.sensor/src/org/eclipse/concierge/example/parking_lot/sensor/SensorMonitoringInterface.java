package org.eclipse.concierge.example.parking_lot.sensor;

public interface SensorMonitoringInterface {
	
	public void sensorStatusDidChange(Sensor sensor);
	public void sensorStatusTimeToUpdate(Sensor sensor);
	
}