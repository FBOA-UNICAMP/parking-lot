package org.eclipse.concierge.example.parking_lot.sensor;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import org.eclipse.concierge.example.parking_lot.sensor.monitor.service.SensorMonitorService;


public class Activator implements BundleActivator {

	SensorMonitorServiceFactory sensorMonitorServiceFactory;
	ServiceRegistration sensorMonitorServiceRegistration;
	 
	 public void start(BundleContext context) throws Exception {
		 sensorMonitorServiceFactory = new SensorMonitorServiceFactory();
		 sensorMonitorServiceRegistration = context.registerService(SensorMonitorService.class.getName(), sensorMonitorServiceFactory, null);
	 }
	 
	 public void stop(BundleContext context) throws Exception {
		 sensorMonitorServiceRegistration.unregister();
		 sensorMonitorServiceFactory.shutdown();
	 }	

}
