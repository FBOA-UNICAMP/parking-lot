package org.eclipse.concierge.example.parking_lot.service.impl;

import org.eclipse.concierge.example.parking_lot.service.PanelService;

public class PanelServiceImpl implements PanelService {
	
	@Override
	public void updatePanelWithSensorAndStatus(int sensorId, ParkingSpaceStatus status) {
		System.out.println("Sensor: " + sensorId + "; status: " + status.toString());	
	}
}
