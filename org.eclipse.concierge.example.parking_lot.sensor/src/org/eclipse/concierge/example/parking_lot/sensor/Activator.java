package org.eclipse.concierge.example.parking_lot.sensor;

import org.eclipse.concierge.example.parking_lot.api.SensorManagerInterface;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import ch.ethz.iks.r_osgi.RemoteOSGiService;
import java.util.Hashtable;

public class Activator implements BundleActivator {
	SensorManager sensorManager;

	@Override
	public void start(BundleContext context) throws Exception {
		sensorManager = new SensorManager(context);
		final Hashtable properties = new Hashtable();
		properties.put(RemoteOSGiService.R_OSGi_REGISTRATION, Boolean.TRUE);
		context.registerService(SensorManagerInterface.class.getName(), sensorManager, properties);
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		sensorManager.shutdown();
	}	
}
