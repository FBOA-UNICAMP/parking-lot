package org.eclipse.concierge.example.parking_lot.sensor;

import org.eclipse.concierge.example.parking_lot.sensor.impl.Sensor;
import org.eclipse.concierge.example.parking_lot.sensor.impl.SensorAdmin;
import org.osgi.framework.Bundle;
import org.osgi.framework.ServiceFactory;
import org.osgi.framework.ServiceRegistration;

public class SensorMonitorServiceFactory implements ServiceFactory {
	
	Sensor sensor;
	SensorAdmin sensorAdmin;
	int referenceCounter = 0;
	
    public Object getService(Bundle bundle, ServiceRegistration registration) {
        
    	System.out.println("Sensor Factory Get Service");
    	
    	if (sensorAdmin == null) {
    		System.out.println("SensorFactory Will Create Sensor");
    		sensor = new Sensor(5);
    		System.out.println("SensorFatory Did Create Sensor " + sensor);
        	sensorAdmin = new SensorAdmin(sensor);
        	System.out.println("Create object of SensorAdmin for " + bundle.getSymbolicName());
    	} else {
    		System.out.println("Returning created object of SensorAdmin for " + bundle.getSymbolicName());
    	}
    	
    	if (sensorAdmin.isPaused()) {
			sensorAdmin.setPaused(false);
		}
    	
    	referenceCounter++;
    	System.out.println("Number of bundles using service " + referenceCounter);
    	
    	return sensorAdmin;
    }
    
    public void ungetService(Bundle bundle, ServiceRegistration registration, Object service) {
        
        referenceCounter--;
        System.out.println("Number of bundles using SensorAdmin " + referenceCounter);
        
        if (referenceCounter == 0) {
        	System.out.println("Put SensorAdmin to 'sleep'");
        	sensorAdmin.setPaused(true);
        }
    }
    
    public void shutdown() {
    	
    	sensorAdmin.setPaused(true);
    	sensorAdmin = null;
    	
    }
    
}
