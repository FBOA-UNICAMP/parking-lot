package org.eclipse.concierge.example.parking_lot.api;

public interface SensorManagerInterface {
	public int getSensorId();
	public SensorState getSensorState();
	public Boolean isStopping();
	public void watch();
	public void stopWatching();
}
