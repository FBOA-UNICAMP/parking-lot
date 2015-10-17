package org.eclipse.concierge.example.parking_lot.sensor;

import org.eclipse.concierge.example.parking_lot.service.PanelService;
import org.eclipse.concierge.example.parking_lot.service.PanelService.ParkingSpaceStatus;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator, SensorDelegate {

	ServiceReference panelServiceReference;
	private PanelService panelService;
	private Sensor sensor;
	
	public void start(BundleContext context) throws Exception {
		panelServiceReference = context.getServiceReference(PanelService.class.getName());
		panelService = (PanelService)context.getService(panelServiceReference);
		
		sensor = new Sensor(121, ParkingSpaceStatus.FREE, this);
	}

	public void stop(BundleContext context) throws Exception {
		context.ungetService(panelServiceReference);
	}

	// SensorDelegate
	@Override
	public void sensorStatusDidChange(int id, ParkingSpaceStatus status) {
		panelService.updatePanelWithSensorAndStatus(id, status);		
	}

	@Override
	public void sensorStatusTimeToUpdate(int id, ParkingSpaceStatus status) {
		panelService.updatePanelWithSensorAndStatus(id, status);		
	}

}
