package org.codehaus.spice.event.impl;

import org.codehaus.spice.event.EventSink;
import org.codehaus.spice.event.EventValve;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

/**
 * @author Peter Donald
 * @version $Revision: 1.1 $ $Date: 2003/12/16 02:03:12 $
 */
public class ValveControlledSinkTestCase
    extends MockObjectTestCase
{
    public void testNullSinkPassedIntoCtor()
        throws Exception
    {
        final Mock mockValve = new Mock( EventValve.class );
        final EventValve valve = (EventValve)mockValve.proxy();
        try
        {
            new ValveControlledSink( null, valve );
        }
        catch( final NullPointerException npe )
        {
            assertEquals( "npe.getMessage()", "sink", npe.getMessage() );
            return;
        }
        fail( "Expected a NPE when passing sink into Ctor" );
    }

    public void testNullValvePassedIntoCtor()
        throws Exception
    {
        final Mock mockSink = new Mock( EventSink.class );
        final EventSink sink = (EventSink)mockSink.proxy();
        try
        {
            new ValveControlledSink( sink, null );
        }
        catch( final NullPointerException npe )
        {
            assertEquals( "npe.getMessage()", "valve", npe.getMessage() );
            return;
        }
        fail( "Expected a NPE when passing valve into Ctor" );
    }

    public void testCreation()
        throws Exception
    {
        final Mock mockValve = new Mock( EventValve.class );
        final EventValve valve = (EventValve)mockValve.proxy();

        final Mock mockSink = new Mock( EventSink.class );
        final EventSink sink = (EventSink)mockSink.proxy();
        final ValveControlledSink vcs = new ValveControlledSink( sink, valve );
        assertEquals( "vcs.m_sink", sink, vcs.m_sink );
        assertEquals( "vcs.m_valve", valve, vcs.m_valve );

        mockSink.verify();
        mockValve.verify();
    }

    public void testGetSyncLock()
        throws Exception
    {
        final Mock mockValve = new Mock( EventValve.class );
        final EventValve valve = (EventValve)mockValve.proxy();

        final Mock mockSink = new Mock( EventSink.class );
        final Object lock = new Object();
        mockSink.expects( once() ).method( "getSinkLock" ).withNoArguments().will( returnValue( lock ) );

        final EventSink sink = (EventSink)mockSink.proxy();
        final ValveControlledSink vcs = new ValveControlledSink( sink, valve );

        assertEquals( "vcs.getSinkLock()", lock, vcs.getSinkLock() );

        mockSink.verify();
        mockValve.verify();
    }

    public void testAddEventsThatAccepted()
        throws Exception
    {
        final Object[] events = new Object[ 0 ];
        final Mock mockValve = new Mock( EventValve.class );
        mockValve.expects( once() ).method( "acceptEvents" ).with( eq( events)).will( returnValue( true ));
        final EventValve valve = (EventValve)mockValve.proxy();

        final Mock mockSink = new Mock( EventSink.class );
        mockSink.expects( once() ).method( "addEvents" ).with( eq( events)).will( returnValue( true ));
        final EventSink sink = (EventSink)mockSink.proxy();
        final ValveControlledSink vcs = new ValveControlledSink( sink, valve );

        assertEquals( "vcs.addEvents( events )", true, vcs.addEvents( events ) );

        mockSink.verify();
        mockValve.verify();
    }

    public void testAddEventsThatNotAccepted()
        throws Exception
    {
        final Object[] events = new Object[ 0 ];
        final Mock mockValve = new Mock( EventValve.class );
        mockValve.expects( once() ).method( "acceptEvents" ).with( eq( events)).will( returnValue( false ));
        final EventValve valve = (EventValve)mockValve.proxy();

        final Mock mockSink = new Mock( EventSink.class );
        final EventSink sink = (EventSink)mockSink.proxy();
        final ValveControlledSink vcs = new ValveControlledSink( sink, valve );

        assertEquals( "vcs.addEvents( events )", false,
                      vcs.addEvents( events ) );

        mockSink.verify();
        mockValve.verify();
    }

    public void testAddEventThatAccepted()
        throws Exception
    {
        final Object event = new Object();
        final Mock mockValve = new Mock( EventValve.class );
        mockValve.expects( once() ).method( "acceptEvent" ).with( eq( event)).will( returnValue( true ));
        final EventValve valve = (EventValve)mockValve.proxy();

        final Mock mockSink = new Mock( EventSink.class );
        mockSink.expects( once() ).method( "addEvent" ).with( eq( event)).will( returnValue( true ));
        final EventSink sink = (EventSink)mockSink.proxy();
        final ValveControlledSink vcs = new ValveControlledSink( sink, valve );

        assertEquals( "vcs.addEvent( event )", true, vcs.addEvent( event ) );

        mockSink.verify();
        mockValve.verify();
    }

    public void testAddEventThatNotAccepted()
        throws Exception
    {
        final Object event = new Object();
        final Mock mockValve = new Mock( EventValve.class );
        mockValve.expects( once() ).method( "acceptEvent" ).with( eq( event)).will( returnValue( false ));
        final EventValve valve = (EventValve)mockValve.proxy();

        final Mock mockSink = new Mock( EventSink.class );
        final EventSink sink = (EventSink)mockSink.proxy();
        final ValveControlledSink vcs = new ValveControlledSink( sink, valve );

        assertEquals( "vcs.addEvent( event )", false,
                      vcs.addEvent( event ) );

        mockSink.verify();
        mockValve.verify();
    }
}
