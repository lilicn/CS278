package edu.vanderbilt.cs278.lili.pubnubclient.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.vanderbilt.cs278.lili.pubnubclient.ColorExecutor;
import edu.vanderbilt.cs278.lili.pubnubclient.MyExecutorFactory;

public class MyExecutorFactoryTest {
	private MyExecutorFactory exeFac_ = new MyExecutorFactory();
	@Test
	public void testGetExecByType() {
		assertTrue("test getExecByType with invalid type",exeFac_.getExecByType("Camera")==null);
		assertTrue("test getExecByType with valid type",exeFac_.getExecByType("color") instanceof ColorExecutor);
	}

	@Test
	public void testIsValidType() {
		assertTrue("test isValidType with invalid type",exeFac_.isValidType("Camera")== false);
		assertTrue("test isValidType with valid type",exeFac_.isValidType("color") == true);
	}

}
