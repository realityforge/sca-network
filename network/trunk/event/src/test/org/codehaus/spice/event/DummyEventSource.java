package org.codehaus.spice.event;

/**
 * @author Peter Donald
 * @version $Revision: 1.1.1.1 $ $Date: 2004/06/19 11:20:08 $
 */
public class DummyEventSource
    implements EventSource
{
    public Object[] getEvents( final int count )
    {
        return new Object[]{getEvent()};
    }

    public Object getSourceLock()
    {
        return this;
    }

    public Object getEvent()
    {
        return "Event";
    }
}
