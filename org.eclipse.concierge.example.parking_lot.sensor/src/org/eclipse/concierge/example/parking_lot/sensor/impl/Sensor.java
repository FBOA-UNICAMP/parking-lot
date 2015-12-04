package org.eclipse.concierge.example.parking_lot.sensor.impl;

//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.concierge.example.parking_lot.sensor.state.SensorState;

public class Sensor {
	
	private int id;
	private boolean active;
	private boolean broadcast;
	private int broadcastPeriod;
	private SensorState status;
	private SensorMonitoringInterface monitorer;
	
//	private int minTime = 500;
//	private int maxTime = 30000;
//	private int fixedTime = 10000;
	
	Random rand;
	
	Timer broadcastTimer;
	Timer stateChangeTimer;
	
//	private class ListenerT1 implements ActionListener {
//
//		private Sensor sensor;
//		
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			if(status == SensorState.FREE)
//				status = SensorState.BUSY;
//			else status = SensorState.FREE;
//			monitorer.sensorStatusDidChange(this.getSensor());
//			T1.stop();
//			int randTime = minTime + rand.nextInt(maxTime-minTime+1);
//			T1.setInitialDelay(randTime);
//			T2.stop();
//			T1.restart();
//			T2.restart();
//		}
//
//		public Sensor getSensor() {
//			return sensor;
//		}
//
//		public void setSensor(Sensor sensor) {
//			this.sensor = sensor;
//		}
//	}
	
//	private class ListenerT2 implements ActionListener {
//
//		Sensor sensor;
//		
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			monitorer.sensorStatusDidChange(this.getSensor());
//		}
//		
//		public Sensor getSensor() {
//			return sensor;
//		}
//
//		public void setSensor(Sensor sensor) {
//			this.sensor = sensor;
//		}
//	}
	
	public Sensor(int id){
		System.out.println("Inside Sensor()");
		
		this.id = id;
		this.broadcastPeriod = 5; // Seconds
		this.status = SensorState.UNKNOWN;
		broadcastTimer = null;
		stateChangeTimer = null;
		
		rand = new Random();
		
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
//		System.out.println("Sensor (" + this.id +") Broadcast State: " + this.status);
		monitorer.sensorStatusTimeToUpdate(this);
	}
	
//	int randTime = minTime + rand.nextInt(maxTime-minTime+1);
//	System.out.println("Times RandTime " + randTime);
//	this.setupTimers(randTime);
	
	class BroadcastTask extends TimerTask {
		Sensor sensor;
		public void run() {
//			System.out.format("Timer Event\n");
			this.sensor.broadcastState();
		}
		// Sensor Getters & Setter
		public Sensor getSensor() {
			return sensor;
		}
		public void setSensor(Sensor sensor) {
			this.sensor = sensor;
		}
	} 
	
//	private void setupTimers(int randTime) {
//		try {
//			System.out.println("Inside Setup Timers");
//			ListenerT1 listnerT1 = new ListenerT1();
//			listnerT1.setSensor(this);
//			ListenerT2 listnerT2 = new ListenerT2();
//			listnerT2.setSensor(this);
//			T1 = new Timer(randTime, listnerT1);
//			T2 = new Timer(fixedTime, listnerT2);
//			System.out.println("Starting Timers");
//			status = SensorState.FREE;
//			T1.start();
//			T2.start();
//		} catch (Exception e) {
//			System.out.println("Exeption Creating Timers ");
//		}
//	}
	
	// Public Methods
	
	public void activate() {
		active = true;
	}
	
	public void shutDown() {
		active = false;
		// pause stuff
	}
	
	
//	// Asynch
//	public updateStatus() {
//		
//	}
//	
//	// Private Methods
//	
//	private boolean readStatus() {
//		return false;
//	}
//	
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

}
