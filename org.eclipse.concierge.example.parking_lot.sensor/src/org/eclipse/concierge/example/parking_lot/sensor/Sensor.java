package org.eclipse.concierge.example.parking_lot.sensor;

public class Sensor {
	
	private int id;
	private ParkingSpaceStatus status;
	private SensorDelegate delegate;
	
	public ParkingSpaceStatus getStatus() {
		return status;
	}
	public void setStatus(ParkingSpaceStatus status) {
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public SensorDelegate getDelegate() {
		return delegate;
	}
	public void setDelegate(SensorDelegate delegate) {
		this.delegate = delegate;
	}
}

enum ParkingSpaceStatus {
    FREE, BUSY;
}

interface SensorDelegate {
	public void sensorStatusDidChange(int id, ParkingSpaceStatus status);
	public void sensorStatusTimeToUpdate(int id, ParkingSpaceStatus status);
}
