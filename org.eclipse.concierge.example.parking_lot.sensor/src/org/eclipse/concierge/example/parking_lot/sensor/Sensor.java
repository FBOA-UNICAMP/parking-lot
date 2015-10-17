package org.eclipse.concierge.example.parking_lot.sensor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;
import org.eclipse.concierge.example.parking_lot.service.PanelService.ParkingSpaceStatus;

public class Sensor implements ActionListener {
	
	private int id;
	private ParkingSpaceStatus status;
	private SensorDelegate delegate;
	private int minTime = 1000;
	private int maxTime = 30000;
	Random rand;
	Timer T1;
	
	public Sensor() {
		rand = new Random();
		T1 = new Timer(minTime + rand.nextInt(maxTime-minTime+1), this);
		status = ParkingSpaceStatus.FREE;
		T1.start();
	}
	
	public Sensor(int id, ParkingSpaceStatus status, SensorDelegate delegate){
		this.id = id;
		this.status = status;
		this.delegate = delegate;
		rand = new Random();
		T1 = new Timer(minTime + rand.nextInt(maxTime-minTime+1), this);
		T1.start();
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
	
	public SensorDelegate getDelegate() {
		return delegate;
	}
	public void setDelegate(SensorDelegate delegate) {
		this.delegate = delegate;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(this.status == ParkingSpaceStatus.FREE)
			this.status = ParkingSpaceStatus.BUSY;
		else this.status = ParkingSpaceStatus.FREE;
		this.delegate.sensorStatusDidChange(this.id, status);
		T1.stop();
		T1.setDelay(minTime + rand.nextInt(maxTime-minTime+1));
		T1.restart();
	}
}

interface SensorDelegate {
	public void sensorStatusDidChange(int id, ParkingSpaceStatus status);
	public void sensorStatusTimeToUpdate(int id, ParkingSpaceStatus status);
}
