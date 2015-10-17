package org.eclipse.concierge.example.parking_lot.sensor.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;
import org.eclipse.concierge.example.parking_lot.service.PanelService.ParkingSpaceStatus;

public class Sensor {
	
	private int id;
	private ParkingSpaceStatus status;
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
			if(status == ParkingSpaceStatus.FREE)
				status = ParkingSpaceStatus.BUSY;
			else status = ParkingSpaceStatus.FREE;
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
		status = ParkingSpaceStatus.FREE;
		T1.start();
		T2.start();
	}
	
	public Sensor(int id, ParkingSpaceStatus status, SensorMonitoringInterface monitorer){
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
	
	public SensorMonitoringInterface getMonitorer() {
		return monitorer;
	}

	public void setMonitorer(SensorMonitoringInterface monitorer) {
		this.monitorer = monitorer;
	}

}
