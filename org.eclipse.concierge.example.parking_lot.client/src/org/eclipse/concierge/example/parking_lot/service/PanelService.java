package org.eclipse.concierge.example.parking_lot.service;

public interface PanelService {
	public enum ParkingSpaceStatus {
	    FREE, BUSY;
	}
	public void updatePanelWithSensorAndStatus(int sensorId, ParkingSpaceStatus status);
}
