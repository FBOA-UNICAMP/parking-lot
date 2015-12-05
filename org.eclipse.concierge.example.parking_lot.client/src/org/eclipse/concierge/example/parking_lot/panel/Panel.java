package org.eclipse.concierge.example.parking_lot.panel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import org.eclipse.concierge.example.parking_lot.api.SensorState;

public class Panel  {
	
	private Frame mainFrame;
	
	public Panel(){
		mainFrame = new Frame("Sensor Status");
		mainFrame.setAutoRequestFocus(false);
		mainFrame.setSize(400,400);
		mainFrame.setLayout(new GridLayout(0, 5));
		mainFrame.setVisible(true);  
	}
	
	public void addLabel(Label l){
		mainFrame.add(l);
		mainFrame.setVisible(true);
	}
	public void update() {
		mainFrame.setVisible(true);
	}
	public void removeLabel(Label l){
		mainFrame.remove(l);
		mainFrame.setVisible(true);
	}
	public void shutdown(){
		mainFrame.dispose();
	}
}
