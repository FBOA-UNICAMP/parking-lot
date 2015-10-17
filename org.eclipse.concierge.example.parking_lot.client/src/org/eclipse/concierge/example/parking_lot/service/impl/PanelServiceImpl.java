package org.eclipse.concierge.example.parking_lot.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.eclipse.concierge.example.parking_lot.service.PanelService;

public class PanelServiceImpl implements PanelService {
	DateFormat dateFormat;
	public PanelServiceImpl(){
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	@Override
	public void updatePanelWithSensorAndStatus(int sensorId, ParkingSpaceStatus status) {
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime()) + ": Sensor[" + sensorId + "]'s status: " + status.toString());	
	}
}
