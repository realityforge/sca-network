package org.codehaus.spice.event.impl;

import org.codehaus.spice.event.impl.collections.Buffer;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

/**
 * @author Peter Donald
 * @version $Revision: 1.1 $ $Date: 2003/12/16 02:03:12 $
 */
public class DefaultEventQueueTestCase
   extends MockObjectTestCase
{
   public void testNull_buffer_PassedIntoCtor()
      throws Exception
   {
      try
      {
         new DefaultEventQueue( null );
      }
      catch ( final NullPointerException npe )
      {
         assertEquals( "npe.getMessage()", "buffer", npe.getMessage() );
         return;
      }
      fail( "Expected a NPE when passing buffer into Ctor" );
   }

   public void testCreation()
      throws Exception
   {
      final Mock mockBuffer = new Mock( Buffer.class );
      final Buffer buffer = (Buffer) mockBuffer.proxy();
      final DefaultEventQueue queue = new DefaultEventQueue( buffer );
      assertEquals( "buffer", buffer, queue.getBuffer() );
   }

   public void testAddEventSuccessfully()
      throws Exception
   {
      final Object object = new Object();

      final Mock mockBuffer = new Mock( Buffer.class );

      mockBuffer.expects( once() ).method( "add" ).with( eq( object ) ).will(
         returnValue( true ) );

      final Buffer buffer = (Buffer) mockBuffer.proxy();
      final DefaultEventQueue queue = new DefaultEventQueue( buffer );

      final LockChecker checker = new LockChecker( queue.getSourceLock() );
      assertEquals( "Lock Notified pre-add", false, checker.isUnlocked() );
      assertEquals( "queue.addEvent( object )", true, queue.addEvent( object ) );
      assertEquals( "Lock Notified  post-add", true, checker.isUnlocked() );

      mockBuffer.verify();
   }

   public void testAddEventUnsuccessfully()
      throws Exception
   {
      final Object object = new Object();

      final Mock mockBuffer = new Mock( Buffer.class );
      mockBuffer.expects( once() ).method( "add" ).with( eq( object ) ).will( returnValue( false ) );
      final Buffer buffer = (Buffer) mockBuffer.proxy();
      final DefaultEventQueue queue = new DefaultEventQueue( buffer );

      final LockChecker checker = new LockChecker( queue.getSourceLock() );
      assertEquals( "Lock Notified pre-add", false, checker.isUnlocked() );
      assertEquals( "queue.addEvent( object )", false, queue.addEvent( object ) );
      assertEquals( "Lock Notified  post-add", false, checker.isUnlocked() );

      mockBuffer.verify();
   }

   /* TODO: JWW removed to get to compile after extraction from spice
       public void testAddEventsSuccessfully()
           throws Exception
       {
           final Object[] objects = new Object[ 0 ];

           final Mock mockBuffer = new Mock( Buffer.class );
           mockBuffer.expectAndReturn( "addAll", C.args( C.eq( objects ) ), true );
           final Buffer buffer = (Buffer)mockBuffer.proxy();
           final DefaultEventQueue queue = new DefaultEventQueue( buffer );

           final LockChecker checker = new LockChecker( queue.getSourceLock() );
           assertEquals( "Lock Notified pre-add", false, checker.isUnlocked() );
           assertEquals( "queue.addEvents( objects )", true,
                         queue.addEvents( objects ) );
           assertEquals( "Lock Notified  post-add", true, checker.isUnlocked() );

           mockBuffer.verify();
       }
   */

   public void testAddEventsUnsuccessfully()
      throws Exception
   {
      final Object[] objects = new Object[ 0 ];

      final Mock mockBuffer = new Mock( Buffer.class );
      mockBuffer.expects( once() ).method( "addAll" ).with( eq(objects ) ).will( returnValue( false ));
      final Buffer buffer = (Buffer) mockBuffer.proxy();
      final DefaultEventQueue queue = new DefaultEventQueue( buffer );

      final LockChecker checker = new LockChecker( queue.getSourceLock() );
      assertEquals( "Lock Notified pre-add", false, checker.isUnlocked() );
      assertEquals( "queue.addEvents( objects )", false, queue.addEvents( objects ) );
      assertEquals( "Lock Notified  post-add", false, checker.isUnlocked() );

      mockBuffer.verify();
   }
   /* TODO: JWW removed to get to compile after extraction from spice
       public void testGetEventSuccessfully()
           throws Exception
       {
           final Object object = new Object();

           final Mock mockBuffer = new Mock( Buffer.class );
           mockBuffer.expectAndReturn( "size", 1 );
           mockBuffer.expectAndReturn( "pop", object );
           final Buffer buffer = (Buffer)mockBuffer.proxy();

           final DefaultEventQueue queue = new DefaultEventQueue( buffer );
           final LockChecker checker = new LockChecker( queue.getSinkLock() );
           assertEquals( "Lock Notified pre-add", false, checker.isUnlocked() );
           assertEquals( "queue.getEvent()", object, queue.getEvent() );
           assertEquals( "Lock Notified  post-add", true, checker.isUnlocked() );

           mockBuffer.verify();
       }
   */

   public void testGetEventUnsuccessfully()
      throws Exception
   {
      final Mock mockBuffer = new Mock( Buffer.class );
      mockBuffer.expects(once()).method( "size" ).withNoArguments().will( returnValue( 0 ) );
      final Buffer buffer = (Buffer) mockBuffer.proxy();

      final DefaultEventQueue queue = new DefaultEventQueue( buffer );
      final LockChecker checker = new LockChecker( queue.getSinkLock() );
      assertEquals( "Lock Notified pre-add", false, checker.isUnlocked() );
      assertEquals( "queue.getEvent()", null, queue.getEvent() );
      assertEquals( "Lock Notified  post-add", false, checker.isUnlocked() );

      mockBuffer.verify();
   }

   /* TODO: JWW removed to get to compile after extraction from spice
       public void testGetEventsSuccessfully()
           throws Exception
       {
           final Object object = new Object();

           final Mock mockBuffer = new Mock( Buffer.class );
           mockBuffer.expectAndReturn( "size", 1 );
           mockBuffer.expectAndReturn( "pop", object );
           final Buffer buffer = (Buffer)mockBuffer.proxy();

           final DefaultEventQueue queue = new DefaultEventQueue( buffer );
           final LockChecker checker = new LockChecker( queue.getSinkLock() );
           assertEquals( "Lock Notified pre-add", false, checker.isUnlocked() );
           final Object[] events = queue.getEvents( 20 );
           assertEquals( "Lock Notified  post-add", true, checker.isUnlocked() );

           assertEquals( "events.length", 1, events.length );
           assertEquals( "events[ 0 ]", object, events[ 0 ] );

           mockBuffer.verify();
       }
   */

   public void testGetEventsUnsuccessfully()
      throws Exception
   {
      final Mock mockBuffer = new Mock( Buffer.class );
      mockBuffer.expects(once()).method( "size" ).withNoArguments().will( returnValue( 0 ) );
      final Buffer buffer = (Buffer) mockBuffer.proxy();

      final DefaultEventQueue queue = new DefaultEventQueue( buffer );
      final LockChecker checker = new LockChecker( queue.getSinkLock() );
      assertEquals( "Lock Notified pre-add", false, checker.isUnlocked() );
      final Object[] events = queue.getEvents( 20 );
      assertEquals( "Lock Notified  post-add", false, checker.isUnlocked() );

      assertEquals( "events.length", 0, events.length );

      mockBuffer.verify();
   }
}
