package org.eclipse.concierge.example.parking_lot.sensor.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

import org.eclipse.concierge.example.parking_lot.sensor.state.SensorState;

public class Sensor {
	
	private int id;
	private boolean active;
	private boolean broadcast;
	private int broadcastPeriod;
	private SensorState status;
	private SensorMonitoringInterface monitorer;
	
	private int minTime = 500;
	private int maxTime = 30000;
	private int fixedTime = 10000;
	Random rand;
	Timer T1;
	Timer T2;
	
	private class ListenerT1 implements ActionListener {

		private Sensor sensor;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(status == SensorState.FREE)
				status = SensorState.BUSY;
			else status = SensorState.FREE;
			monitorer.sensorStatusDidChange(this.getSensor());
			T1.stop();
			int randTime = minTime + rand.nextInt(maxTime-minTime+1);
			T1.setInitialDelay(randTime);
			T2.stop();
			T1.restart();
			T2.restart();
		}

		public Sensor getSensor() {
			return sensor;
		}

		public void setSensor(Sensor sensor) {
			this.sensor = sensor;
		}
	}
	
	private class ListenerT2 implements ActionListener {

		Sensor sensor;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			monitorer.sensorStatusDidChange(this.getSensor());
		}
		
		public Sensor getSensor() {
			return sensor;
		}

		public void setSensor(Sensor sensor) {
			this.sensor = sensor;
		}
	}
	
	public Sensor(){
		rand = new Random();
		int randTime = minTime + rand.nextInt(maxTime-minTime+1);
		System.out.println(randTime);
		this.setupTimers(randTime);
	}
	
	public Sensor(int id, SensorState status, SensorMonitoringInterface monitorer){
		this.id = id;
		this.status = status;
		this.monitorer = monitorer;
		rand = new Random();
		int randTime = minTime + rand.nextInt(maxTime-minTime+1);
		this.setupTimers(randTime);
	}
	
	private void setupTimers(int randTime) {
		ListenerT1 listnerT1 = new ListenerT1();
		listnerT1.setSensor(this);
		ListenerT2 listnerT2 = new ListenerT2();
		listnerT2.setSensor(this);
		T1 = new Timer(randTime, listnerT1);
		T2 = new Timer(fixedTime, listnerT2);
		status = SensorState.FREE;
		T1.start();
		T2.start();
	}
	
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
