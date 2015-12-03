package org.eclipse.concierge.example.parking_lot.panel;

import org.osgi.framework.ServiceReference;

public abstract interface ServiceTrackerCustomizer
{
  public abstract Object addingService(ServiceReference paramServiceReference);
  
  public abstract void modifiedService(ServiceReference paramServiceReference, Object paramObject);
  
  public abstract void removedService(ServiceReference paramServiceReference, Object paramObject);
}
