package org.eclipse.concierge.example.parking_lot.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.eclipse.concierge.example.parking_lot.sensor.impl.Sensor.SensorStatus;
import org.eclipse.concierge.example.parking_lot.service.PanelService;
import org.eclipse.concierge.example.parking_lot.service.PanelService.ParkingSpaceStatus;

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



//private ParkingSpaceStatus parkingSpaceStatusForSensorStatus(SensorStatus sensorStatus) {
//	
//	ParkingSpaceStatus parkingSpaceStatus;
//	
//	switch (sensorStatus) {
//		case FREE: 
//			parkingSpaceStatus = ParkingSpaceStatus.AVAILABLE;
//			break;
//		case BUSY:
//			parkingSpaceStatus =  ParkingSpaceStatus.OCCUPIED;
//			break;
//		case UNKNOWN:
//		default:
//			parkingSpaceStatus = ParkingSpaceStatus.UNKNOWN;
//			break;
//	}
//		
//	return parkingSpaceStatus;
//	
//}