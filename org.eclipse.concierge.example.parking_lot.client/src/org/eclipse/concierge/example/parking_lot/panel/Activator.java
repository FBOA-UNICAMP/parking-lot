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
	
//	SensorMonitorServiceTracker sensorMonitorServiceTracker;
	Listener listener;
	SensorMonitorService sensorMonitorService;
	DateFormat dateFormat;
//	Panel panel;

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
//		panel = new Panel();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
//		System.out.println("Panel service started. Tracking For SensorMonitorServices.");
		
		//sensorMonitorServiceTracker = new SensorMonitorServiceTracker(context, panel);
		listener = new Listener(context);
		context.addServiceListener(listener);
//		sensorMonitorServiceTracker.open();
        
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Stopping Panel...");
		
//		sensorMonitorServiceTracker.shutdown();
		listener.shutdown();
		System.out.println("Panel Stopped");
	}

	
	

}
