package org.cs27x.dropbox.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * This class gathers all unit tests
 * 
 * @author Li
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ DefaultFileManagerTest.class, AnExampleUnitTest.class,
		DropboxCmdProcessorTest.class, FileStatesTest.class })
public class AllTests {

}
