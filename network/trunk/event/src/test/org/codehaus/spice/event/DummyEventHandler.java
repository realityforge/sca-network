package org.codehaus.spice.event;

/**
 * @author Peter Donald
 * @version $Revision: 1.1.1.1 $ $Date: 2004/06/19 11:20:08 $
 */
public class DummyEventHandler
    extends AbstractEventHandler
{
    private int _callCount;

    public int getCallCount()
    {
        return _callCount;
    }

    public void handleEvent( final Object event )
    {
        _callCount++;
    }
}
