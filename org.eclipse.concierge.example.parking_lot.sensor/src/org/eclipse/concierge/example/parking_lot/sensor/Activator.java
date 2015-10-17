package org.eclipse.concierge.example.parking_lot.sensor;

import org.eclipse.concierge.example.parking_lot.service.PanelService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {

	ServiceReference panelServiceReference;
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		panelServiceReference = context.getServiceReference(PanelService.class.getName());
		PanelService panelService = (PanelService)context.getService(panelServiceReference);
		panelService.updatePanelWithSensorAndStatus(10, true);
		System.out.println("Starting");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		context.ungetService(panelServiceReference);
		System.out.println("Stopping");

	}

}
