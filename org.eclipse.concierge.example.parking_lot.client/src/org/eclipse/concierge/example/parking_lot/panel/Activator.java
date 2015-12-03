package org.eclipse.concierge.example.parking_lot.panel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import java.util.Calendar;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import org.eclipse.concierge.example.parking_lot.sensor.monitor.service.SensorMonitorService;
//import org.eclipse.concierge.example.parking_lot.sensor.monitor.service.SensorMonitorServiceMonitorerInterface;
//import org.eclipse.concierge.example.parking_lot.sensor.state.SensorState;

public class Activator implements BundleActivator {
	
	SensorMonitorServiceTracker sensorMonitorServiceTracker;
	SensorMonitorService sensorMonitorService;
	DateFormat dateFormat;
	Panel panel;

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		panel = new Panel();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		System.out.println("Panel service started. Tracking For SensorMonitorServices.");
		
		sensorMonitorServiceTracker = new SensorMonitorServiceTracker(context, panel);
		
		System.out.println("Oppenning Service Tracker");
		sensorMonitorServiceTracker.open();
        
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Stopping Panel...");
		
		sensorMonitorServiceTracker.shutdown();
		System.out.println("Panel Stopped");
	}

	
	

}
