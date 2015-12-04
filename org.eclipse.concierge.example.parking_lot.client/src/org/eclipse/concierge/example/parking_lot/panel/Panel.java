package org.eclipse.concierge.example.parking_lot.panel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import org.eclipse.concierge.example.parking_lot.sensor.monitor.service.SensorMonitorService;
import org.eclipse.concierge.example.parking_lot.sensor.monitor.service.SensorMonitorServiceMonitorerInterface;
import org.eclipse.concierge.example.parking_lot.sensor.state.SensorState;

public class Panel implements SensorMonitorServiceMonitorerInterface {
	
	DateFormat dateFormat;
	private Frame mainFrame;
	private HashMap<Integer, Label> sensorStatusLabel;
	public Panel(){
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		sensorStatusLabel = new HashMap<Integer, Label>();
		prepareGUI();
	}
	private void prepareGUI(){
		mainFrame = new Frame("Sensor Status");
		mainFrame.setAutoRequestFocus(false);
		mainFrame.setSize(400,400);
		mainFrame.setLayout(new GridLayout(0, 5));
		/*mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				prepareGUI();
				for(Integer key: sensorStatusLabel.keySet()){
					mainFrame.add(sensorStatusLabel.get(key));
				}
			}
		});*/
		mainFrame.setVisible(true);  
	}
	

	public void startMonitoringSensor(SensorMonitorService sensor) {
		System.out.println("Panel startMonitoringSensor(" + sensor + ")");
		sensor.setSensorMonitorServiceMonitorer(this);
	}
	
	public void stopMonitoringSensor(SensorMonitorService sensor) {
		System.out.println("Panel stopMonitoringSensor(" + sensor + ")");
		sensor.unsetSensorMonitorServiceMonitorer(this);
	}
	
	// Interface
	
	public void sensorMonitorServiceDidUpdate(int sensorId, SensorState status) {
		Calendar cal = Calendar.getInstance();
		Label currentLabel;
//		System.out.println(dateFormat.format(cal.getTime()) + ": Sensor[" + sensorId + "]'s status: " + status.toString());
		if(!sensorStatusLabel.containsKey(sensorId)){
			currentLabel = new Label("",Label.CENTER);
			currentLabel.setText("Sensor["+Integer.toString(sensorId)+"]");
			sensorStatusLabel.put(sensorId, currentLabel);
			mainFrame.add(currentLabel);
		}
		
		currentLabel = sensorStatusLabel.get(sensorId);
		switch (status){
			case FREE: 
				currentLabel.setBackground(new Color(0, 255, 0));
//				currentLabel.setOpaque(true);
				break;
			case BUSY:
				currentLabel.setBackground(new Color(255, 0, 0));
//				currentLabel.setOpaque(true);
				break;
			case UNKNOWN:
				currentLabel.setBackground(new Color(192,192,192));
				break;
		}
		
		mainFrame.setVisible(true); 
	}
	public void shutdown(){
		mainFrame.dispose();
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