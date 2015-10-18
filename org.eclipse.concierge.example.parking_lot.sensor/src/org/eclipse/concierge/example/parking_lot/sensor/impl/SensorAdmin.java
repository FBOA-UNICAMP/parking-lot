package org.eclipse.concierge.example.parking_lot.sensor.impl;

import org.eclipse.concierge.example.parking_lot.service.PanelService;
import org.eclipse.concierge.example.parking_lot.service.PanelService.ParkingSpaceStatus;

import org.eclipse.concierge.example.parking_lot.sensor.impl.Sensor.SensorStatus;

public class SensorAdmin implements SensorMonitoringInterface {

	private PanelService panelService;
	private Sensor managedSensor;
	
	// SensorAdmin Constructor
	
	public SensorAdmin(PanelService panelService) {
		super();
		this.panelService = panelService;
	}
	
	// SensorAdmin Public Methods
	
	public boolean isManagingSensor(Sensor sensor) {
		
		if ((managedSensor != null) && (managedSensor.getId() == sensor.getId())) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public void manageSensor(Sensor sensor) {
		
		this.stopManagingSensor(managedSensor);
		managedSensor = sensor;
		managedSensor.setMonitorer(this);
		
	}
	
	public void stopManagingSensor(Sensor sensor) {
		
		if (this.isManagingSensor(managedSensor)) {
			managedSensor.setMonitorer(null);
			managedSensor = null;
		}
		
	}
	
	// Sensor Interface Implementation
	
	public void sensorStatusDidChange(int id, SensorStatus status) {
		
		panelService.updatePanelWithSensorAndStatus(id, this.parkingSpaceStatusForSensorStatus(status));		
		
	}

	public void sensorStatusTimeToUpdate(int id, SensorStatus status) {
		
		panelService.updatePanelWithSensorAndStatus(id, this.parkingSpaceStatusForSensorStatus(status));	
		
	}
	
	private ParkingSpaceStatus parkingSpaceStatusForSensorStatus(SensorStatus sensorStatus) {
		
		ParkingSpaceStatus parkingSpaceStatus;
		
		switch (sensorStatus) {
			case FREE: 
				parkingSpaceStatus = ParkingSpaceStatus.AVAILABLE;
				break;
			case BUSY:
				parkingSpaceStatus =  ParkingSpaceStatus.OCCUPIED;
				break;
			case UNKNOWN:
			default:
				parkingSpaceStatus = ParkingSpaceStatus.UNKNOWN;
				break;
		}
			
		return parkingSpaceStatus;
		
	}
	
}