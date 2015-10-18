package org.eclipse.concierge.example.parking_lot.panel;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import org.eclipse.concierge.example.parking_lot.sensor.monitor.service.SensorMonitorService;

public class SensorMonitorServiceTracker extends ServiceTracker {

	public SensorMonitorServiceTracker(BundleContext context) {
        super(context, SensorMonitorService.class.getName(),null);
    }
    
	public Object addingService(ServiceReference reference) {
        System.out.println("Inside SensorMonitorService.addingService " + reference.getBundle());
        return super.addingService(reference);
    }
    
	public void removedService(ServiceReference reference, Object service) {
        System.out.println("Inside SensorMonitorService.removedService " + reference.getBundle());
        super.removedService(reference, service);
    }
	
}
