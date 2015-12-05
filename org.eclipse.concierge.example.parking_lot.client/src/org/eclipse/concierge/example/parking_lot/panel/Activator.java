package org.eclipse.concierge.example.parking_lot.panel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import java.util.Calendar;
import java.util.Hashtable;

import org.eclipse.concierge.example.parking_lot.api.PanelManagerInterface;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import ch.ethz.iks.r_osgi.RemoteOSGiService;

public class Activator implements BundleActivator {
	PanelManagerImpl panelManager;
	public void start(BundleContext context) throws Exception {
		panelManager = new PanelManagerImpl();
		final Hashtable properties = new Hashtable();
		properties.put(RemoteOSGiService.R_OSGi_REGISTRATION, Boolean.TRUE);
		context.registerService(PanelManagerInterface.class.getName(), panelManager, properties);
	}
	public void stop(BundleContext context) throws Exception {
		panelManager.shutdown();
	}
}
	/*
//	SensorMonitorServiceTracker sensorMonitorServiceTracker;
	Listener listener;
	SensorMonitorService sensorMonitorService;
	DateFormat dateFormat;
//	Panel panel;

	public void start(BundleContext context) throws Exception {
//		panel = new Panel();
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
//		System.out.println("Panel service started. Tracking For SensorMonitorServices.");
		
		//sensorMonitorServiceTracker = new SensorMonitorServiceTracker(context, panel);
		listener = new Listener(context);
		context.addServiceListener(listener);
//		sensorMonitorServiceTracker.open();
        
	}
	
	public void stop(BundleContext context) throws Exception {
		System.out.println("Stopping Panel...");
		
//		sensorMonitorServiceTracker.shutdown();
		listener.shutdown();
		System.out.println("Panel Stopped");
	}

}
*/