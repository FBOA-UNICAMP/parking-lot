package org.eclipse.concierge.example.parking_lot.panel;

import org.eclipse.concierge.example.parking_lot.service.PanelService;
import org.eclipse.concierge.example.parking_lot.service.impl.PanelServiceImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
	
	ServiceRegistration panelServiceRegistration;
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		PanelService panelService = new PanelServiceImpl();
		panelServiceRegistration = context.registerService(PanelService.class.getName(), panelService, null);
		System.out.println("Panel service started");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		panelServiceRegistration.unregister();
		System.out.println("Panel service stopped");
	}

}
