package org.eclipse.concierge.example.parking_lot.sensor.impl;

import org.eclipse.concierge.example.parking_lot.sensor.monitor.service.SensorMonitorService;
import org.eclipse.concierge.example.parking_lot.sensor.monitor.service.SensorMonitorServiceMonitorerInterface;
import org.eclipse.concierge.example.parking_lot.sensor.state.SensorState;
import org.eclipse.concierge.shell.commands.ShellCommandGroup;

public class SensorAdmin implements SensorMonitoringInterface, SensorMonitorService, ShellCommandGroup {

	private Sensor managedSensor;
	private boolean paused;
	
	private SensorMonitorServiceMonitorerInterface monitorServiceMonitorer;
	
	// SensorAdmin Constructor
	
	public SensorAdmin(Sensor sensor) {
		System.out.println("Inside SensorAdmin");
		this.manageSensor(sensor);
		this.setPaused(true);
		System.out.println("Leaving SensorAdmin");
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
	
	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
		if (paused) {
			managedSensor.pauseBroadcast();
		} else {
			managedSensor.startBroadcast();
		}
	}
	
	// Sensor Interface Implementation - Internal Bundle Monitoring
	
	public void sensorStatusDidChange(Sensor sensor) {
		
		this.updateSensorMonitorServiceMonitorer(sensor);		
		
	}

	public void sensorStatusTimeToUpdate(Sensor sensor) {
		
		this.updateSensorMonitorServiceMonitorer(sensor);	
		
	}
	
	private void updateSensorMonitorServiceMonitorer(Sensor sensor) {
		
		if (monitorServiceMonitorer != null) {
			monitorServiceMonitorer.sensorMonitorServiceDidUpdate(sensor.getId(), sensor.getStatus());
		}
		
	}
	
	
	// Sensor Monitor Service Implementation
	
	public void test() {
		
		System.out.println("Sensor Test Method!");
		
	}
	
	public void setSensorMonitorServiceMonitorer(SensorMonitorServiceMonitorerInterface sensorServiceMonitorer) {
		
		monitorServiceMonitorer = sensorServiceMonitorer;
		
	}

	public void unsetSensorMonitorServiceMonitorer(SensorMonitorServiceMonitorerInterface sensorServiceMonitorer) {
		
		monitorServiceMonitorer = null;
		
	}

	public String getHelp() {
		return "Sensor Commands:\n\tsensor.busy\n\tsensor.free\n\tsensor.unknown\n";
	}

	public String getGroup() {
		return "sensor";
	}

	public void handleCommand(String command, String[] args) throws Exception {
		if ("free".equals(command)) {
			managedSensor.setStatus(SensorState.FREE);
		} else if ("busy".equals(command)) {
			managedSensor.setStatus(SensorState.BUSY);
		} else if ("unknown".equals(command)) {
			managedSensor.setStatus(SensorState.UNKNOWN);
		} else {
			System.out.println("unknown command " + command);
		}
	}
	
}