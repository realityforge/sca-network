package org.codehaus.spice.netevent.handlers;

import org.codehaus.spice.event.AbstractEventHandler;
import org.codehaus.spice.event.EventSink;

/**
 * @author Peter Donald
 * @version $Revision: 1.1.1.1 $ $Date: 2004/06/19 11:20:14 $
 */
public abstract class AbstractDirectedHandler
    extends AbstractEventHandler
{
    /** The destination sink for events. */
    private final EventSink _sink;

    /**
     * Create handler with specified destination sink.
     * 
     * @param sink the destination
     */
    protected AbstractDirectedHandler( final EventSink sink )
    {
        if( null == sink )
        {
            throw new NullPointerException( "sink" );
        }
        _sink = sink;
    }

    /**
     * Return the destination.
     * 
     * @return the destination.
     */
    protected EventSink getSink()
    {
        return _sink;
    }
}
