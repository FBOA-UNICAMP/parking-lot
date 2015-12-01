package org.eclipse.concierge.example.parking_lot.sensor.maintenance.service;

public interface SensorMaintenanceService {

	public int getId();
	
	public void setActive(boolean active);
	public boolean isActive();
	
	public void setStatusBroadcastPeriod(int period);
	public int getStatusBroadcastPeriod();
	
	
	
}
