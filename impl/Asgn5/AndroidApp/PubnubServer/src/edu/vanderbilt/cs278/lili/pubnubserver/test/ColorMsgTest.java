package edu.vanderbilt.cs278.lili.pubnubserver.test;

import org.junit.Test;

import junit.framework.TestCase;
import edu.vanderbilt.cs278.lili.pubnubserver.ColorMsg;

public class ColorMsgTest extends TestCase {
	
	@Test
	public void testWithInvalidValue() {
		assertFalse("testWithInvalidValue isValid",new ColorMsg(-1,2,3).isValid());
		assertFalse("testWithInvalidValue isValid",new ColorMsg(1,-2,3).isValid());
		assertFalse("testWithInvalidValue isValid",new ColorMsg(1,2,-3).isValid());		
	}
	@Test
	public void testWithValidValue() {
		ColorMsg cmsg = new ColorMsg(1,2,3);
		assertTrue("testWithValidValue isValid",cmsg.isValid());
		assertTrue("testWithValidValue getValue", cmsg.getValue()==(-1 * ((1 << 16) + (2 << 8) + 3)));		
	}	
}
