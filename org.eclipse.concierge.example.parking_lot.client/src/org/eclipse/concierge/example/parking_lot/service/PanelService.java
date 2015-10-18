package org.eclipse.concierge.example.parking_lot.service;

public interface PanelService {
	public enum ParkingSpaceStatus {
	    AVAILABLE, 
	    OCCUPIED,
	    UNKNOWN;
	}
	public void updatePanelWithSensorAndStatus(int sensorId, ParkingSpaceStatus status);
}
