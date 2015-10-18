package org.eclipse.concierge.example.parking_lot.panel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.eclipse.concierge.example.parking_lot.sensor.monitor.service.SensorMonitorService;
import org.eclipse.concierge.example.parking_lot.sensor.monitor.service.SensorMonitorServiceMonitorerInterface;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator, SensorMonitorServiceMonitorerInterface {
	
	SensorMonitorServiceTracker sensorMonitorServiceTracker;
	SensorMonitorService sensorMonitorService;
	
	DateFormat dateFormat;

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		System.out.println("Panel service started. Tracking For SensorMonitorServices.");
		
		sensorMonitorServiceTracker = new SensorMonitorServiceTracker(context);
		sensorMonitorServiceTracker.open();
        sensorMonitorService = (SensorMonitorService)sensorMonitorServiceTracker.getService();
		sensorMonitorService.setSensorMonitorServiceMonitorer(this);
        
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Stopping Panel...");
		
		sensorMonitorService.unsetSensorMonitorServiceMonitorer(this);
		sensorMonitorServiceTracker.close();
		
		System.out.println("Panel Stopped");
	}

	// SensorMonitorServiceMonitorerInterface Implementation
	
	public void sensorMonitorServiceDidUpdate(int sensorId, SensorStatus status) {
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime()) + ": Sensor[" + sensorId + "]'s status: " + status.toString());
	}

}
