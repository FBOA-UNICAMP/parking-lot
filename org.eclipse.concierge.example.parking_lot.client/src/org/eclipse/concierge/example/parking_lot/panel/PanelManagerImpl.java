package org.eclipse.concierge.example.parking_lot.panel;

import org.eclipse.concierge.example.parking_lot.api.SensorManagerInterface;
import org.eclipse.concierge.example.parking_lot.api.SensorState;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class PanelManagerImpl implements Runnable {

	private Panel panel;
	private HashMap<Integer, Label> sensorStatusLabel;
	private HashMap<Integer, SensorManagerInterface> sensorManager;
	private Boolean isRunning;
	Thread thread;
	
	public PanelManagerImpl(){
		panel = new Panel();
		sensorStatusLabel = new HashMap<Integer, Label>();
		sensorManager = new HashMap<Integer, SensorManagerInterface>();
		isRunning = true;
//		run();
		thread = new Thread(this, "Sensor Updater");
		System.out.println(Thread.currentThread().getId() + " Created new thread with id " + thread.getId());
		thread.start();
	}

	// Methods to manipulate the Panel
	private Boolean addSensorToPanel(int id, SensorState status) {
		Label pointer;
		if(!sensorStatusLabel.containsKey(id)){
			pointer = new Label("",Label.CENTER);
			pointer.setText("Sensor["+Integer.toString(id)+"]");
			pointer.setBackground(getColor(status));
			sensorStatusLabel.put(id, pointer);
			panel.addLabel(pointer);
			System.out.println("Sensor added successfully, returning true");
			return true;
		} else {
			System.out.println("Panel already has sensor[" + id + "]");
			return false;
		}
	}

	private void updateSensorInPanel(int id, SensorState status) {
		if(sensorStatusLabel.containsKey(id)){
			sensorStatusLabel.get(id).setBackground(getColor(status));
			panel.update();
		} else {
			System.out.println("Panel doesn't have sensor[" + id + "]");
		}
	}

	private void removeSensorFromPanel(int id) {
		if(sensorStatusLabel.containsKey(id)){
			panel.removeLabel(sensorStatusLabel.get(id));
			sensorStatusLabel.remove(id);
		} else {
			System.out.println("Panel doesn't have sensor[" + id + "]");
		}
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
	
	// Methods to access the sensors
	public void addSensorManager(SensorManagerInterface current) {
		if(addSensorToPanel(current.getSensorId(), current.getSensorState())){
			sensorManager.put(current.getSensorId(), current);
			current.watch();
		}
	}
	
	public synchronized void run() {
		System.out.println("here");
		Thread current = Thread.currentThread();
		System.out.println("here2 " + current + " " + thread);
		System.out.println("Entered run " + current.getId() + " " + thread.getId());
		while(thread == current){
//			System.out.println("Gonna check sensors" + sensorManager.size());
			Iterator it = sensorManager.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry pair = (Map.Entry)it.next();
				if(((SensorManagerInterface) pair.getValue()).isStopping()){
					((SensorManagerInterface) pair.getValue()).stopWatching();
					it.remove();
					removeSensorFromPanel((Integer) pair.getKey());
				} else {
					updateSensorInPanel((Integer) pair.getKey(), ((SensorManagerInterface) pair.getValue()).getSensorState());
				}
			}
			try { wait( 1000 ); } catch( InterruptedException e ) {}
		}
	}
	
	public void shutdown(){
		Iterator it = sensorManager.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry pair = (Map.Entry)it.next();
			((SensorManagerInterface) pair.getValue()).stopWatching();
			it.remove();
		}
		isRunning = false;
		panel.shutdown();
		thread = null;
	}

}
