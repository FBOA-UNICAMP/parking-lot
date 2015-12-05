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
		panels = new Vector<PanelManagerInterface>();
		createSensor();
	}
	
	private void createSensor(){
		sensor = new Sensor(1);
		sensor.setMonitorer(this);
		this.context.registerService(ShellCommandGroup.class.getName(), sensor, null);
	}
	
	public void addPanel(PanelManagerInterface panel) {
		System.out.println("Trying to add sensor["+sensor.getId()+"] to panel " + panel);
		if(panel.addSensor(sensor.getId(), sensor.getStatus())){
			panels.add(panel);
		}
		System.out.println("Panel successfully added");
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