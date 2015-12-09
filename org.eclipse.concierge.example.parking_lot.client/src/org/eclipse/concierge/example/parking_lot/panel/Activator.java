package org.eclipse.concierge.example.parking_lot.panel;

import java.io.IOException;

import org.eclipse.concierge.example.parking_lot.api.SensorManagerInterface;
import org.eclipse.concierge.shell.commands.ShellCommandGroup;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;

import ch.ethz.iks.r_osgi.RemoteOSGiService;
import ch.ethz.iks.r_osgi.RemoteServiceReference;
import ch.ethz.iks.r_osgi.URI;

public class Activator implements BundleActivator, ShellCommandGroup {
	PanelManagerImpl panelManager;
	BundleContext context;
	RemoteOSGiService remote;
	public void start(BundleContext context) throws Exception {
		this.context = context;
		panelManager = new PanelManagerImpl();
		this.context.registerService(ShellCommandGroup.class.getName(), this, null);
		final ServiceReference rosgiRef = context.getServiceReference(RemoteOSGiService.class.getName());
		if (rosgiRef == null) { 
			throw new BundleException("No R-OSGi found"); 
		}
		System.out.println("Found R-OSGI reference " + rosgiRef);
		remote = (RemoteOSGiService) context.getService(rosgiRef);
	}

	public void stop(BundleContext context) throws Exception {
		panelManager.shutdown();
	}	

	public String getHelp() {
		return "Adding Sensors to panel:\n\tpanel.addsensor <SensorURI>\n";
	}

	public String getGroup() {
		return "panel";
	}

	public void handleCommand(String command, String[] args) throws Exception {
		if ("addsensor".equals(command)) {
			if(args.length != 1){
				System.out.println("Wrong number of arguments");
			} else {
			connectToSensor(new URI(args[0]));
			}
		} else {
			System.out.println("unknown command " + command);
		}
	}
	public void connectToSensor(URI uri) {
		try{
			System.out.println("Trying to connect "+remote+" to " + uri);
			remote.connect(uri);
			System.out.println("Connected to " + uri);
			final RemoteServiceReference[] srefs =
					remote.getRemoteServiceReferences(uri, SensorManagerInterface.class.getName(), null);
			System.out.println(srefs);
			for(int j = 0; srefs!=null && j < srefs.length; j++){
				SensorManagerInterface service = (SensorManagerInterface)remote.getRemoteService(srefs[j]);
				panelManager.addSensorManager(service);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
