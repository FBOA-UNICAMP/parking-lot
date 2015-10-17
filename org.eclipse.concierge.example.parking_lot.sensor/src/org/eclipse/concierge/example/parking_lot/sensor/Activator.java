package org.eclipse.concierge.example.parking_lot.sensor;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import org.eclipse.concierge.example.parking_lot.service.PanelService;

import org.eclipse.concierge.example.parking_lot.sensor.impl.Sensor;
import org.eclipse.concierge.example.parking_lot.sensor.impl.SensorAdmin;


public class Activator implements BundleActivator {

	Sensor sensor;
	SensorAdmin sensorAdmin;
	ServiceReference panelServiceReference;
	
	public void start(BundleContext context) throws Exception {
		
		panelServiceReference = context.getServiceReference(PanelService.class.getName());
		PanelService panelService = (PanelService)context.getService(panelServiceReference);
		
		sensor = new Sensor();
		sensorAdmin = new SensorAdmin(panelService);
		sensorAdmin.manageSensor(sensor);
		
	}

	public void stop(BundleContext context) throws Exception {
		
		context.ungetService(panelServiceReference);
		panelServiceReference = null;
		sensorAdmin.stopManagingSensor(sensor);
		sensorAdmin = null;
		sensor = null;
		
	}	

}
