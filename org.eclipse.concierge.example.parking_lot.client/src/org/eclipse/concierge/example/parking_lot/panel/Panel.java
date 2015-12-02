package org.eclipse.concierge.example.parking_lot.panel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;
import org.eclipse.concierge.example.parking_lot.sensor.monitor.service.SensorMonitorServiceMonitorerInterface;
import org.eclipse.concierge.example.parking_lot.sensor.state.SensorState;

public class Panel implements SensorMonitorServiceMonitorerInterface {
	DateFormat dateFormat;
	private JFrame mainFrame;
	private HashMap<Integer, JLabel> sensorStatusLabel;
	public Panel(){
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		sensorStatusLabel = new HashMap();
		prepareGUI();
	}
	private void prepareGUI(){
		mainFrame = new JFrame("Sensor Status");
		mainFrame.setSize(400,400);
		mainFrame.setLayout(new GridLayout(0, 5));
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				prepareGUI();
				for(Integer key: sensorStatusLabel.keySet()){
					mainFrame.add(sensorStatusLabel.get(key));
				}
			}
		});
		mainFrame.setVisible(true);  
	}
	@Override
	public void sensorMonitorServiceDidUpdate(int sensorId, SensorState status) {
		//Calendar cal = Calendar.getInstance();
		JLabel currentLabel;
		//System.out.println(dateFormat.format(cal.getTime()) + ": Sensor[" + sensorId + "]'s status: " + status.toString());
		if(!sensorStatusLabel.containsKey(sensorId)){
			currentLabel = new JLabel("",JLabel.CENTER);
			currentLabel.setText("Sensor["+Integer.toString(sensorId)+"]");
			sensorStatusLabel.put(sensorId, currentLabel);
			mainFrame.add(currentLabel);
		}
		
		currentLabel = sensorStatusLabel.get(sensorId);
		switch (status){
			case FREE: 
				currentLabel.setBackground(new Color(0, 255, 0));
				currentLabel.setOpaque(true);
				break;
			case BUSY:
				currentLabel.setBackground(new Color(255, 0, 0));
				currentLabel.setOpaque(true);
				break;
			case UNKNOWN:
				break;
		}
		
		mainFrame.setVisible(true); 
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