package org.eclipse.concierge.example.parking_lot.panel;

import java.io.IOException;

import org.eclipse.concierge.example.parking_lot.api.SensorMonitorService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

import ch.ethz.iks.r_osgi.RemoteOSGiService;
import ch.ethz.iks.r_osgi.RemoteServiceReference;
import ch.ethz.iks.r_osgi.URI;


public class Listener implements ServiceListener {
	Panel panel;
	BundleContext context;
	private static final URI uri = new URI("r-osgi://localhost:9278");
	public Listener(BundleContext context) throws BundleException{
		this.context = context;
		panel = new Panel();
		final ServiceReference rosgiRef = context.getServiceReference(RemoteOSGiService.class.getName());
		if (rosgiRef == null) { 
			throw new BundleException("No R-OSGi found"); 
		} 
		RemoteOSGiService remote = (RemoteOSGiService) context.getService(rosgiRef);
		try{
			remote.connect(uri);
			final RemoteServiceReference[] srefs =
					remote.getRemoteServiceReferences(uri, SensorMonitorService.class.getName(), null);
			System.out.println("Panel found " + srefs.length + " services on startup");
			for(int i = 0; i < srefs.length; i++){
				SensorMonitorService service = (SensorMonitorService)remote.getRemoteService(srefs[i]);
				System.out.println(service);
				try {
		        	service.test();
		        	this.setupTrackedService(service);
		        } catch (RuntimeException e) {
		        	System.out.println("service.test() exception" + e);
		        }
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}/*
		ServiceReference reference = context.getServiceReference(SensorMonitorService.class.getName());
		if(reference != null){
			SensorMonitorService service = (SensorMonitorService)context.getService(reference);
		
			try {
	        	service.test();
	        	this.setupTrackedService(service);
	        } catch (RuntimeException e) {
	        	System.out.println("service.test() exception" + e);
	        }
		}*/
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
						System.out.println("Remove Tracked Service Error "+ nse);
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
			System.out.println("Shutdown Tracked Service Error "+ nse);
		}
		panel.shutdown();		
	}
	
	private void setupTrackedService(SensorMonitorService service) {
		try {
			System.out.println("Got this far");
			panel.startMonitoringSensor(service);
			System.out.println("Maybe here?");
		} catch (Exception nse) {
			System.out.println("Setup Tracked Service Error "+ nse);
		}
	}
	
}
