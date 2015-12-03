package org.eclipse.concierge.example.parking_lot.panel;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
//import org.osgi.util.tracker.ServiceTracker;

import org.eclipse.concierge.example.parking_lot.sensor.monitor.service.SensorMonitorService;

@SuppressWarnings("rawtypes")
public class SensorMonitorServiceTracker extends ServiceTracker {

	private Panel panel;
	
	ServiceReference ref;
	
	@SuppressWarnings("unchecked")
	public SensorMonitorServiceTracker(BundleContext context, Panel panel) {
        super(context, SensorMonitorService.class.getName(),null);
        System.out.println("ServiceTracker Created");
        this.panel = panel;
    }
	
	@SuppressWarnings("unchecked")
	public Object addingService(ServiceReference reference) {
        
		System.out.println("Inside SensorMonitorService.addingService " + reference.getBundle());
		
		SensorMonitorService service = (SensorMonitorService)this.context.getService(reference);
		System.out.println("Got serviceRef [" + reference.getBundle() + "] Service [" + service + "].");
		
        try {
        	service.test();
        	this.setupTrackedService(reference);
        } catch (RuntimeException e) {
        	System.out.println("service.test() exception" + e);
        }
		
        System.out.println("Calling super!");
        
        return service;
    }
    
	@SuppressWarnings("unchecked")
	public void removedService(ServiceReference reference, Object service) {
        System.out.println("Inside SensorMonitorService.removedService " + reference.getBundle());
        
        System.out.println("Removing tracked service ref(" + reference.getBundle() + ")");
		try {	
			panel.stopMonitoringSensor((SensorMonitorService)service);
		} catch (Exception nse) {
			System.out.println("Remove Tracked Serviec Error "+ nse);
		}
        
        super.removedService(reference, service);
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
		
		
		this.close();
		
	}
	
	private void setupTrackedService(ServiceReference reference) {
		
		System.out.println("Setting up tracked service ref(" + reference.getBundle() + ")");
		try {	
			SensorMonitorService sensor = (SensorMonitorService)this.context.getService(reference);
			panel.startMonitoringSensor(sensor);
		} catch (Exception nse) {
			System.out.println("Setup Tracked Serviec Error "+ nse);
		}
		
	}
	
}
