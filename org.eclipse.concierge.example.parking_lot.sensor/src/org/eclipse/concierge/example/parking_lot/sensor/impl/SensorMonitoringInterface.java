package org.eclipse.concierge.example.parking_lot.sensor.impl;

import org.eclipse.concierge.example.parking_lot.service.PanelService.ParkingSpaceStatus;

public interface SensorMonitoringInterface {
	
	public void sensorStatusDidChange(int id, ParkingSpaceStatus status);
	
	public void sensorStatusTimeToUpdate(int id, ParkingSpaceStatus status);
	
}