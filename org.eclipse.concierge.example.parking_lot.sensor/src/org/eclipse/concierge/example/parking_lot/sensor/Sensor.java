package org.eclipse.concierge.example.parking_lot.sensor;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.concierge.example.parking_lot.api.SensorState;
import org.eclipse.concierge.shell.commands.ShellCommandGroup;

public class Sensor implements  ShellCommandGroup{
	
	private int id;
	private boolean active;
	private boolean broadcast;
	private int broadcastPeriod;
	private SensorState status;
	private SensorMonitoringInterface monitorer;
	Timer broadcastTimer;
	Timer stateChangeTimer;
	
	public Sensor(int id){
		this.id = id;
		this.broadcastPeriod = 5; // Seconds
		this.status = SensorState.UNKNOWN;
		broadcastTimer = null;
		stateChangeTimer = null;		
	}
	
	public void startBroadcast() {
		System.out.println("Inside StartBroadCast.");
		if (broadcastTimer == null) {
			System.out.println("Starting Broadcast Timer.");
			broadcastTimer = new Timer(true);
			BroadcastTask broadcast = new BroadcastTask();
			broadcast.setSensor(this);
			broadcastTimer.schedule(broadcast, 0, this.broadcastPeriod * 1000);
			System.out.println("Timer Started.");
		} else {
			System.out.println("Broadcast Timer already started.");
		}
	}
	
	public void pauseBroadcast() {
		System.out.println("Inside PauseBroadcast.");
		if (broadcastTimer != null) {
			System.out.println("Stoppin Broadcast Timer");
			broadcastTimer.cancel();
			broadcastTimer = null;
			System.out.println("Broadcast Timer Stopped.");
		} else {
			System.out.println("Broadcast Timer not started.");
		}
	}
	
	private void broadcastState() {
		monitorer.sensorStatusTimeToUpdate(this);
	}

	class BroadcastTask extends TimerTask {
		Sensor sensor;
		public void run() {
			this.sensor.broadcastState();
		}
		public Sensor getSensor() {
			return sensor;
		}
		public void setSensor(Sensor sensor) {
			this.sensor = sensor;
		}
	}
	
	// Getters and Setters
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public SensorState getStatus() {
		return status;
	}

	public void setStatus(SensorState status) {
		this.status = status;
		monitorer.sensorStatusDidChange(this);
	}

	public SensorMonitoringInterface getMonitorer() {
		return monitorer;
	}

	public void setMonitorer(SensorMonitoringInterface monitorer) {
		this.monitorer = monitorer;
	}
	
	public boolean isActive() {
		return active;
	}

	public boolean isBroadcasting() {
		return broadcast;
	}

	public void setBroadcast(boolean broadcast) {
		this.broadcast = broadcast;
	}

	public int getBroadcastPeriod() {
		return broadcastPeriod;
	}

	public void setBroadcastPeriod(int broadcastPeriod) {
		this.broadcastPeriod = broadcastPeriod;
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
			this.setStatus(SensorState.FREE);
		} else if ("busy".equals(command)) {
			this.setStatus(SensorState.BUSY);
		} else if ("unknown".equals(command)) {
			this.setStatus(SensorState.UNKNOWN);
		} else {
			System.out.println("unknown command " + command);
		}
	}
}
