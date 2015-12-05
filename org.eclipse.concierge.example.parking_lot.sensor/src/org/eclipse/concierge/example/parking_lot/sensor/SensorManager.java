package org.eclipse.concierge.example.parking_lot.sensor;

import java.util.Enumeration;
import java.util.Vector;
import org.eclipse.concierge.example.parking_lot.api.PanelManagerInterface;
import org.eclipse.concierge.shell.commands.ShellCommandGroup;
import org.osgi.framework.BundleContext;

public class SensorManager implements SensorMonitoringInterface {

	private Sensor sensor;
	private Vector<PanelManagerInterface> panels;
	BundleContext context;
	// SensorAdmin Constructor
	
	public SensorManager(BundleContext context) {
		this.context = context;
		createSensor();
	}
	
	private void createSensor(){
		Sensor pointer = new Sensor(1);
		pointer.setMonitorer(this);
		context.registerService(ShellCommandGroup.class.getName(), sensor, null);
	}
	
	public void addPanel(PanelManagerInterface panel) {
		if(panel.addSensor(sensor.getId(), sensor.getStatus())){
			panels.add(panel);
		}
	}
	
	public void shutdown() {
		Enumeration<PanelManagerInterface> en = panels.elements();
		while(en.hasMoreElements())
			en.nextElement().removeSensor(sensor.getId());
	}
	
	// Sensor Interface Implementation - Internal Bundle Monitoring
	
	public void sensorStatusDidChange(Sensor sensor) {
		Enumeration<PanelManagerInterface> en = panels.elements();
		while(en.hasMoreElements())
			en.nextElement().updateSensor(sensor.getId(), sensor.getStatus());
	}

	public void sensorStatusTimeToUpdate(Sensor sensor) {
		Enumeration<PanelManagerInterface> en = panels.elements();
		while(en.hasMoreElements())
			en.nextElement().updateSensor(sensor.getId(), sensor.getStatus());
	}
}