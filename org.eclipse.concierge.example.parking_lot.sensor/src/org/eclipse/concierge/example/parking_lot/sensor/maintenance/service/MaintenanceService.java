package org.eclipse.concierge.example.parking_lot.sensor.maintenance.service;

public interface MaintenanceService {

	public int getId();
	
	public void setStatusBroadcastPeriod(int period);
	public int getStatusBroadcastPeriod();
	
	public void setSimulation(boolean simulate);
	public boolean isSimulating();
	
}
