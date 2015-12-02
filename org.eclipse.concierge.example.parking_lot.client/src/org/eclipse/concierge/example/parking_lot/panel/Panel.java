package org.eclipse.concierge.example.parking_lot.panel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.eclipse.concierge.example.parking_lot.sensor.monitor.service.SensorMonitorServiceMonitorerInterface;
import org.eclipse.concierge.example.parking_lot.sensor.state.SensorState;


public class Panel implements SensorMonitorServiceMonitorerInterface {
	DateFormat dateFormat;
	private JFrame mainFrame;
	private JLabel sensorStatusLabel[];
	private int sensorStatus[];
	public Panel(){
		sensorStatus = new int[100];
		sensorStatusLabel = new JLabel[100];
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		prepareGUI();
	}
	private void prepareGUI(){
		mainFrame = new JFrame("Sensor Status");
		mainFrame.setSize(400,400);
		mainFrame.setLayout(new GridLayout(0, 5));
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				prepareGUI();
			}        
		});
		mainFrame.setVisible(true);  
	}
	@Override
	public void sensorMonitorServiceDidUpdate(int sensorId, SensorState status) {
		//Calendar cal = Calendar.getInstance();
		//System.out.println(dateFormat.format(cal.getTime()) + ": Sensor[" + sensorId + "]'s status: " + status.toString());
		if(sensorStatus[sensorId] == 0){
			sensorStatusLabel[sensorId] = new JLabel("",JLabel.CENTER );
			sensorStatusLabel[sensorId].setText("Sensor["+Integer.toString(sensorId)+"]"); 
			mainFrame.add(sensorStatusLabel[sensorId]);
		}
		switch (status){
			case FREE: 
				sensorStatus[sensorId] = 1;
				sensorStatusLabel[sensorId].setBackground(new Color(0, 255, 0));
				sensorStatusLabel[sensorId].setOpaque(true);
				break;
			case BUSY:
				sensorStatus[sensorId] = 2;
				sensorStatusLabel[sensorId].setBackground(new Color(255, 0, 0));
				sensorStatusLabel[sensorId].setOpaque(true);
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