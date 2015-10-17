package org.eclipse.concierge.example.parking_lot.panel.impl;

import org.eclipse.concierge.example.parking_lot.panel.service.Panel;

public class PanelImpl implements Panel {
	
	@Override
	public void updatePanelWithSensorAndStatus(int sensorId, boolean isFree) {
		System.out.println("Sensor: " + sensorId + "; isFree: " + Boolean.toString(isFree));	
	}
}
