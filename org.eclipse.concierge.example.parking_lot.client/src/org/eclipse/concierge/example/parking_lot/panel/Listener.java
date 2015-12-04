package org.eclipse.concierge.example.parking_lot.panel;

import org.eclipse.concierge.example.parking_lot.sensor.monitor.service.SensorMonitorService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.eclipse.concierge.example.parking_lot.sensor.monitor.service.SensorMonitorService;


public class Listener implements ServiceListener {
	Panel panel;
	BundleContext context;
	public Listener(BundleContext context){
		this.context = context;
		panel = new Panel();
		ServiceReference reference = context.getServiceReference(SensorMonitorService.class.getName());
		if(reference != null){
			SensorMonitorService service = (SensorMonitorService)context.getService(reference);
		
			try {
	        	service.test();
	        	this.setupTrackedService(service);
	        } catch (RuntimeException e) {
	        	System.out.println("service.test() exception" + e);
	        }
		}
	}
	
	public void serviceChanged(ServiceEvent e) {
		SensorMonitorService service;
		switch (e.getType()) {
			case ServiceEvent.REGISTERED:
				System.out.println("Service REGISTERED");
				service = (SensorMonitorService)context.getService(e.getServiceReference());
				this.setupTrackedService(service);
				break;
			case ServiceEvent.MODIFIED:
				System.out.println("Service MODIFIED");
				break;
			case ServiceEvent.UNREGISTERING:
				System.out.println("Service UNREGISTERED");
				if(e.getServiceReference() != null){
					service = (SensorMonitorService)context.getService(e.getServiceReference());
				
					try {	
						panel.stopMonitoringSensor((SensorMonitorService)service);
					} catch (Exception nse) {
						System.out.println("Remove Tracked Serviec Error "+ nse);
					}
				}
				break;
			default:
				break;
		}
	}

	public void shutdown() {
		System.out.println("Shudown Panel SensorTracker");
		ServiceReference ref = this.context.getServiceReference(SensorMonitorService.class.getName());
		System.out.println("reference: " + ref);
		try {	
			SensorMonitorService sensor = (SensorMonitorService)this.context.getService(ref);
			panel.stopMonitoringSensor(sensor);
			this.context.ungetService(ref);
		} catch (Exception nse) {
			System.out.println("Shutdown Tracked Serviec Error "+ nse);
		}
		panel.shutdown();		
	}
	
	private void setupTrackedService(SensorMonitorService service) {
		try {	
			panel.startMonitoringSensor(service);
		} catch (Exception nse) {
			System.out.println("Setup Tracked Serviec Error "+ nse);
		}
	}
	
}
