package org.eclipse.concierge.example.parking_lot.sensor;

import org.eclipse.concierge.example.parking_lot.api.SensorState;
import org.eclipse.concierge.shell.commands.ShellCommandGroup;

public class Sensor implements  ShellCommandGroup {
	
	private int id;
	private SensorState status;
	
	public Sensor(int id){
		this.id = id;
		this.status = SensorState.UNKNOWN;	
	}	

	// Getters and Setters
	public int getId() {
		return id;
	}
	
	public SensorState getStatus() {
		return status;
	}

	// Shell methods
	public String getHelp() {
		return "Sensor Commands:\n\tsensor.busy\n\tsensor.free\n\tsensor.unknown\n";
	}

	public String getGroup() {
		return "sensor";
	}

	public void handleCommand(String command, String[] args) throws Exception {
		if ("free".equals(command)) {
			this.status = SensorState.FREE;
		} else if ("busy".equals(command)) {
			this.status = SensorState.BUSY;
		} else if ("unknown".equals(command)) {
			this.status = SensorState.UNKNOWN;
		} else {
			System.out.println("unknown command " + command);
		}
	}
}
