package org.eclipse.concierge.example.parking_lot.sensor;

public class Sensor {
	
	public enum ParkingSpaceStatus {
	    FREE, BUSY;
	}
	
	private int id;
	private ParkingSpaceStatus status;
	
	
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
}
