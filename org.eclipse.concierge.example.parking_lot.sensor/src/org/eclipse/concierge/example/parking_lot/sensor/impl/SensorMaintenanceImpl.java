package org.eclipse.concierge.example.parking_lot.sensor.impl;

import org.eclipse.concierge.example.parking_lot.sensor.maintenance.service.SensorMaintenanceService;

public class SensorMaintenanceImpl implements SensorMaintenanceService {

	Sensor sensor;
	
	public SensorMaintenanceImpl(Sensor sensor) {
		super();
		this.sensor = sensor;
	}

	// SensorMaintnanceService Interface Implementation
	
	public int getId() {
		return sensor.getId();
	}

	public void setStatusBroadcastPeriod(int period) {
		// TODO Auto-generated method stub

	}

	public int getStatusBroadcastPeriod() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setSimulation(boolean simulate) {
		// TODO Auto-generated method stub

	}

	public boolean isSimulating() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setActive(boolean active) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

}
