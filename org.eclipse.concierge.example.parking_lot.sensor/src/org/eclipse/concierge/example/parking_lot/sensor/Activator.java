package org.eclipse.concierge.example.parking_lot.sensor;

import org.eclipse.concierge.example.parking_lot.api.PanelManagerInterface;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import ch.ethz.iks.r_osgi.RemoteOSGiService;
import ch.ethz.iks.r_osgi.RemoteServiceReference;
import ch.ethz.iks.r_osgi.URI;
import java.io.IOException;

public class Activator implements BundleActivator, ServiceListener {
	SensorManager sensorManager;
	BundleContext context;
	private static final URI uri = new URI("r-osgi://localhost:9278");
	RemoteOSGiService remote;
	public void start(BundleContext context) throws Exception {
		this.context = context;
		sensorManager = new SensorManager(context);
		final ServiceReference rosgiRef = context.getServiceReference(RemoteOSGiService.class.getName());
		if (rosgiRef == null) { 
			throw new BundleException("No R-OSGi found"); 
		}
		System.out.println("Found R-OSGI reference " + rosgiRef);
		remote = (RemoteOSGiService) context.getService(rosgiRef);
		try{
			remote.connect(uri);
			System.out.println("Connected to " + uri);
			final RemoteServiceReference[] srefs =
					remote.getRemoteServiceReferences(uri, PanelManagerInterface.class.getName(), null);
			System.out.println("Sensor found " + srefs.length + " services on startup");
			for(int i = 0; i < srefs.length; i++){
				System.out.println("Getting service from srefs[" + i+ "]: " + srefs[i]);
				PanelManagerInterface service = (PanelManagerInterface)remote.getRemoteService(srefs[i]);
				System.out.println("Adding service[" + i+ "]: " + service);
				sensorManager.addPanel(service);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
        
	}
	
	public void stop(BundleContext context) throws Exception {
		System.out.println("Stopping Sensor...");
		sensorManager.shutdown();
		System.out.println("Sensor Stopped");
	}

	public void serviceChanged(ServiceEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
