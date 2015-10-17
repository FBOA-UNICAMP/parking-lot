package org.eclipse.concierge.example.parking_lot.service.impl;

import org.eclipse.concierge.example.parking_lot.service.PanelService;

public class PanelServiceImpl implements PanelService {
	
	@Override
	public void updatePanelWithSensorAndStatus(int sensorId, boolean isFree) {
		System.out.println("Sensor: " + sensorId + "; isFree: " + Boolean.toString(isFree));	
	}
}
