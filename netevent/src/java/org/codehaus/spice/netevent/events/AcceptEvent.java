package org.codehaus.spice.netevent.events;

import java.nio.channels.Channel;

/**
 * Event to indicate Channel was accepted.
 * 
 * @author Peter Donald
 * @version $Revision: 1.1.1.1 $ $Date: 2004/06/19 11:20:13 $
 */
public class AcceptEvent
    extends ChannelEvent
{
    /**
     * Create Event.
     * 
     * @param channel the channel
     */
    public AcceptEvent( final Channel channel )
    {
        super( channel, null );
    }
}
