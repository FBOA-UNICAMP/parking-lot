package org.eclipse.concierge.example.parking_lot.sensor;

import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import org.eclipse.concierge.example.parking_lot.api.SensorManagerInterface;
import org.eclipse.concierge.example.parking_lot.api.SensorState;
import org.eclipse.concierge.shell.commands.ShellCommandGroup;
import org.osgi.framework.BundleContext;

public class SensorManager implements SensorManagerInterface {

	private Sensor sensor;
	BundleContext context;
	int numberWatchers;
	Boolean stopping;
	
	// SensorAdmin Constructor
	
	public SensorManager(BundleContext context) {
		this.context = context;
		numberWatchers = 0;
		stopping = false;
		createSensor();
	}
	
	private void createSensor(){
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter sensor id: ");
		sensor = new Sensor(reader.nextInt());
		this.context.registerService(ShellCommandGroup.class.getName(), sensor, null);
	}
	
	// Sensor Interface Implementation - Internal Bundle Monitoring
	public int getSensorId() {
		return sensor.getId();
	}

	public SensorState getSensorState() {
		return sensor.getStatus();
	}

	public Boolean isStopping() {
		return stopping;
	}

	public void watch() {
		numberWatchers++;
	}

	public void stopWatching() {
		numberWatchers--;
	}
	
	public synchronized void shutdown(){
		stopping = true;
		while(numberWatchers > 0){
			try { wait( 1000 ); } catch( InterruptedException e ) {}
		}
	}
}