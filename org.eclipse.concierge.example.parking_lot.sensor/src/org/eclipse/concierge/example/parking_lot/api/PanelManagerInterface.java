package org.eclipse.concierge.example.parking_lot.api;

import org.eclipse.concierge.example.parking_lot.api.SensorState;

public interface PanelManagerInterface {
	public Boolean addSensor(int id, SensorState status);
	public void updateSensor(int id, SensorState status);
	public void removeSensor(int id);
}
