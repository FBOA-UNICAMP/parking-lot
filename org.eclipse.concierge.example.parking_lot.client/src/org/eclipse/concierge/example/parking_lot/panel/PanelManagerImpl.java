package org.eclipse.concierge.example.parking_lot.panel;

import org.eclipse.concierge.example.parking_lot.api.PanelManagerInterface;
import org.eclipse.concierge.example.parking_lot.api.SensorState;
import java.awt.*;
import java.util.HashMap;

public class PanelManagerImpl implements PanelManagerInterface {

	private Panel panel;
	private HashMap<Integer, Label> sensorStatusLabel;
	public PanelManagerImpl(){
		panel = new Panel();
		sensorStatusLabel = new HashMap<Integer, Label>();
	}
	
	public Boolean addSensor(int id, SensorState status) {
		Label pointer;
		if(!sensorStatusLabel.containsKey(id)){
			pointer = new Label("",Label.CENTER);
			pointer.setText("Sensor["+Integer.toString(id)+"]");
			pointer.setBackground(getColor(status));
			sensorStatusLabel.put(id, pointer);
			panel.addLabel(pointer);
			return true;
		} else {
			System.out.println("Panel already has sensor[" + id + "]");
			return false;
		}
	}

	public void updateSensor(int id, SensorState status) {
		if(sensorStatusLabel.containsKey(id)){
			sensorStatusLabel.get(id).setBackground(getColor(status));
			panel.update();
		} else {
			System.out.println("Panel doesn't have sensor[" + id + "]");
		}
	}

	public void removeSensor(int id) {
		if(sensorStatusLabel.containsKey(id)){
			panel.removeLabel(sensorStatusLabel.get(id));
			sensorStatusLabel.remove(id);
		} else {
			System.out.println("Panel doesn't have sensor[" + id + "]");
		}
	}
	
	public void shutdown(){
		panel.shutdown();
	}
	
	private Color getColor(SensorState status) {
		switch (status){
		case FREE: 
			return new Color(0, 255, 0);
		case BUSY:
			return new Color(255, 0, 0);
		default:
			return new Color(192,192,192);
		}
	}
}
