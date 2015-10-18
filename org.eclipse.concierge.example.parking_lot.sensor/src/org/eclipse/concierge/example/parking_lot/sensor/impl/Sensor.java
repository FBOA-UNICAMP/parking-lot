package org.eclipse.concierge.example.parking_lot.sensor.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

public class Sensor {
	
	public enum SensorStatus {
	    FREE,
	    BUSY,
	    UNKNOWN;
	}
	
	private int id;
	private boolean active;
	private boolean broadcast;
	private int broadcastPeriod;
	private SensorStatus status;
	private SensorMonitoringInterface monitorer;
	
	private int minTime = 500;
	private int maxTime = 30000;
	private int fixedTime = 10000;
	Random rand;
	Timer T1;
	Timer T2;
	
	private class ListenerT1 implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(status == SensorStatus.FREE)
				status = SensorStatus.BUSY;
			else status = SensorStatus.FREE;
			monitorer.sensorStatusDidChange(id, status);
			T1.stop();
			int randTime = minTime + rand.nextInt(maxTime-minTime+1);
			T1.setInitialDelay(randTime);
			T2.stop();
			T1.restart();
			T2.restart();
		}
	}
	
	private class ListenerT2 implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			monitorer.sensorStatusDidChange(id, status);
		}
		
	}
	
	public Sensor(){
		rand = new Random();
		int randTime = minTime + rand.nextInt(maxTime-minTime+1);
		System.out.println(randTime);
		T1 = new Timer(randTime, new ListenerT1());
		T2 = new Timer(fixedTime, new ListenerT2());
		status = SensorStatus.FREE;
		T1.start();
		T2.start();
	}
	
	public Sensor(int id, SensorStatus status, SensorMonitoringInterface monitorer){
		this.id = id;
		this.status = status;
		this.monitorer = monitorer;
		rand = new Random();
		int randTime = minTime + rand.nextInt(maxTime-minTime+1);
		T1 = new Timer(randTime, new ListenerT1());
		T2 = new Timer(fixedTime, new ListenerT2());
		T1.start();
		T2.start();
	}
	
	// Public Methods
	
	public void activate() {
		
	}
	
	public void shutDown() {
		
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
