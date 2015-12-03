package org.eclipse.concierge.example.parking_lot.panel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

public class ServiceTracker
  implements ServiceTrackerCustomizer
{
  private static final boolean DEBUG = false;
  private static final String OBJECTCLASS = "objectClass".toLowerCase();
  private static final String SERVICE_ID = "service.id".toLowerCase();
  protected final BundleContext context;
  protected final Filter filter;
  private Tracked tracked;
  private final ServiceTrackerCustomizer customizer;
  
  public ServiceTracker(BundleContext paramBundleContext, ServiceReference paramServiceReference, ServiceTrackerCustomizer paramServiceTrackerCustomizer)
  {
    this.context = paramBundleContext;
    this.customizer = (paramServiceTrackerCustomizer == null ? this : paramServiceTrackerCustomizer);
    try
    {
      this.filter = paramBundleContext.createFilter('(' + SERVICE_ID + '=' + paramServiceReference.getProperty(SERVICE_ID) + ')');
    }
    catch (InvalidSyntaxException localInvalidSyntaxException)
    {
      throw new RuntimeException("unexpected InvalidSyntaxException: " + localInvalidSyntaxException.getMessage());
    }
  }
  
  public ServiceTracker(BundleContext paramBundleContext, String paramString, ServiceTrackerCustomizer paramServiceTrackerCustomizer)
  {
    this.context = paramBundleContext;
    this.customizer = (paramServiceTrackerCustomizer == null ? this : paramServiceTrackerCustomizer);
    if (paramString == null) {
      throw new NullPointerException();
    }
    try
    {
      this.filter = paramBundleContext.createFilter('(' + OBJECTCLASS + '=' + paramString + ')');
    }
    catch (InvalidSyntaxException localInvalidSyntaxException)
    {
      throw new RuntimeException("unexpected InvalidSyntaxException: " + localInvalidSyntaxException.getMessage());
    }
  }
  
  public ServiceTracker(BundleContext paramBundleContext, Filter paramFilter, ServiceTrackerCustomizer paramServiceTrackerCustomizer)
  {
    if ((paramBundleContext == null) || (paramFilter == null)) {
      throw new NullPointerException();
    }
    this.context = paramBundleContext;
    this.filter = paramFilter;
    this.customizer = (paramServiceTrackerCustomizer == null ? this : paramServiceTrackerCustomizer);
  }
  
  public synchronized void open()
  {
    if (this.tracked == null)
    {
      this.tracked = new Tracked(this.customizer, this.filter);
      
      this.context.addServiceListener(this.tracked);
      ServiceReference[] arrayOfServiceReference;
      try
      {
    	java.lang.String clazz = null;
        arrayOfServiceReference = this.context.getServiceReferences(clazz, this.filter.toString());
      }
      catch (InvalidSyntaxException localInvalidSyntaxException)
      {
        throw new RuntimeException("unexpected InvalidSyntaxException");
      }
      if (arrayOfServiceReference != null)
      {
        int i = arrayOfServiceReference.length;
        for (int j = 0; j < i; j++)
        {
          ServiceReference localServiceReference = arrayOfServiceReference[j];
          if (localServiceReference.getBundle() != null) {
            this.tracked.track(localServiceReference);
          }
        }
      }
    }
  }
  
  public synchronized void close()
  {
    if (this.tracked != null)
    {
      this.tracked.close();
      
      ServiceReference[] arrayOfServiceReference = getServiceReferences();
      
      Tracked localTracked = this.tracked;
      this.tracked = null;
      try
      {
        this.context.removeServiceListener(localTracked);
      }
      catch (IllegalStateException localIllegalStateException) {}
      if (arrayOfServiceReference != null) {
        for (int i = 0; i < arrayOfServiceReference.length; i++) {
          localTracked.untrack(arrayOfServiceReference[i]);
        }
      }
    }
  }
  
  protected void finalize()
    throws Throwable
  {
    close();
  }
  
  public Object addingService(ServiceReference paramServiceReference)
  {
    return this.context.getService(paramServiceReference);
  }
  
  public void modifiedService(ServiceReference paramServiceReference, Object paramObject) {}
  
  public void removedService(ServiceReference paramServiceReference, Object paramObject)
  {
    this.context.ungetService(paramServiceReference);
  }
  
  public Object waitForService(long paramLong)
    throws InterruptedException
  {
    if (paramLong < 0L) {
      throw new IllegalArgumentException("timeout value is negative");
    }
    Object localObject1 = getService();
    while (localObject1 == null)
    {
      Tracked localTracked = this.tracked;
      if (localTracked == null) {
        return null;
      }
      synchronized (localTracked)
      {
        if (localTracked.size() == 0) {
          localTracked.wait(paramLong);
        }
      }
      localObject1 = getService();
      if (paramLong > 0L) {
        return localObject1;
      }
    }
    return localObject1;
  }
  
  public ServiceReference[] getServiceReferences()
  {
    if (this.tracked == null) {
      return null;
    }
    Tracked localTracked = this.tracked;
    
    int i = localTracked.size();
    
    return i == 0 ? null : (ServiceReference[])localTracked.keySet().toArray(new ServiceReference[i]);
  }
  
  public Object[] getServices()
  {
    if (this.tracked == null) {
      return null;
    }
    Tracked localTracked = this.tracked;
    
    return localTracked.size() == 0 ? null : localTracked.values().toArray();
  }
  
  public ServiceReference getServiceReference()
  {
    ServiceReference[] arrayOfServiceReference = getServiceReferences();
    
    int i = arrayOfServiceReference == null ? 0 : arrayOfServiceReference.length;
    if (i > 0)
    {
      int j = 0;
      if (i > 1)
      {
        int[] arrayOfInt = new int[i];
        int k = 0;
        int m = Integer.MIN_VALUE;
        int i1;
        for (int n = 0; n < i; n++)
        {
          Object localObject = arrayOfServiceReference[n].getProperty("service.ranking");
          
          i1 = (localObject instanceof Integer) ? ((Integer)localObject).intValue() : 0;
          
          arrayOfInt[n] = i1;
          if (i1 > m)
          {
            j = n;
            m = i1;
            k = 1;
          }
          else if (i1 == m)
          {
            k++;
          }
        }
        if (k > 1)
        {
          long l1 = Long.MAX_VALUE;
          for (i1 = 0; i1 < i; i1++) {
            if (arrayOfInt[i1] == m)
            {
              long l2 = ((Long)arrayOfServiceReference[i1].getProperty("service.id")).longValue();
              if (l2 < l1)
              {
                j = i1;
                l1 = l2;
              }
            }
          }
        }
      }
      return arrayOfServiceReference[j];
    }
    return null;
  }
  
  public Object getService(ServiceReference paramServiceReference)
  {
    if (this.tracked == null) {
      return null;
    }
    Tracked localTracked = this.tracked;
    
    return localTracked.get(paramServiceReference);
  }
  
  public Object getService()
  {
    ServiceReference localServiceReference = getServiceReference();
    
    return localServiceReference == null ? null : getService(localServiceReference);
  }
  
  public void remove(ServiceReference paramServiceReference)
  {
    if (this.tracked == null) {
      return;
    }
    Tracked localTracked = this.tracked;
    
    localTracked.untrack(paramServiceReference);
  }
  
  public int size()
  {
    if (this.tracked == null) {
      return 0;
    }
    Tracked localTracked = this.tracked;
    
    return localTracked.size();
  }
  
  public int getTrackingCount()
  {
    if (this.tracked == null) {
      return -1;
    }
    Tracked localTracked = this.tracked;
    
    return localTracked.getTrackingCount();
  }
  
  static final class Tracked
    extends HashMap
    implements ServiceListener
  {
    private static final long serialVersionUID = -7420065199791006079L;
    private final ServiceTrackerCustomizer customizer;
    private final Filter filter;
    private final ArrayList adding;
    private int trackingCount;
    private boolean closed;
    
    protected Tracked(ServiceTrackerCustomizer paramServiceTrackerCustomizer, Filter paramFilter)
    {
      this.customizer = paramServiceTrackerCustomizer;
      this.filter = paramFilter;
      this.closed = false;
      this.trackingCount = 0;
      this.adding = new ArrayList(0);
    }
    
    protected void close()
    {
      this.closed = true;
    }
    
    protected int getTrackingCount()
    {
      return this.trackingCount;
    }
    
    public void serviceChanged(ServiceEvent paramServiceEvent)
    {
      if (this.closed) {
        return;
      }
      ServiceReference localServiceReference = paramServiceEvent.getServiceReference();
      switch (paramServiceEvent.getType())
      {
      case 1: 
      case 2: 
        if (this.filter.match(localServiceReference)) {
          track(localServiceReference);
        } else {
          untrack(localServiceReference);
        }
        break;
      case 4: 
        untrack(localServiceReference);
      }
    }
    
    protected void track(ServiceReference paramServiceReference)
    {
      Object localObject1 = get(paramServiceReference);
      if (localObject1 != null)
      {
        this.customizer.modifiedService(paramServiceReference, localObject1);
        
        return;
      }
      synchronized (this)
      {
        if (this.adding.indexOf(paramServiceReference) != -1) {
          return;
        }
        this.adding.add(paramServiceReference);
      }
      int i = 0;
      try
      {
        localObject1 = this.customizer.addingService(paramServiceReference);
      }
      finally
      {
        synchronized (this)
        {
          if (this.adding.remove(paramServiceReference))
          {
            if (localObject1 != null)
            {
              put(paramServiceReference, localObject1);
              
              this.trackingCount += 1;
              
              notifyAll();
            }
          }
          else {
            i = 1;
          }
        }
      }
      if (i != 0) {
        this.customizer.removedService(paramServiceReference, localObject1);
      }
    }
    
    protected void untrack(ServiceReference paramServiceReference)
    {
      Object localObject1;
      synchronized (this)
      {
        if (this.adding.remove(paramServiceReference)) {
          return;
        }
        localObject1 = remove(paramServiceReference);
        if (localObject1 == null) {
          return;
        }
        this.trackingCount += 1;
      }
      this.customizer.removedService(paramServiceReference, localObject1);
    }
    
    public String toString()
    {
      return "Tracked " + this.filter + "";
    }
    
    public boolean equals(Object paramObject)
    {
      return this == paramObject;
    }
  }
}
