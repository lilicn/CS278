package org.cs27x.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.cs27x.util.Args;
import org.cs27x.util.DropboxProcess;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * This class gathers all the integration tests
 * 
 * @author Li
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ CreateFileTest.class, ModifyFileTest.class,
		DeleteFileTest.class, DirTest.class })
public class AllTests {
	private static Process p1;
	private static Process p2;
	private static String server;
	private static String client;
	private static int RESPONSETIME;

	@BeforeClass
	public static void init() throws IOException, InterruptedException {
		String[] strs = Args.getArgs();
		server = strs[0];
		client = strs[1];
		RESPONSETIME = Args.DEFAULTRESPONSETIME * 50;
		FileUtils.cleanDirectory(new File(server));
		FileUtils.cleanDirectory(new File(client));

		long currentTime = System.currentTimeMillis();

		// create log directory
		Path p = Paths.get("log/");
		if (!Files.exists(p))
			Files.createDirectory(p);

		p1 = DropboxProcess.createProcess(server, true, "log/" + currentTime
				+ "_serverlog");

		Thread.sleep(RESPONSETIME * 2);

		p2 = DropboxProcess.createProcess(client, false, "log/" + currentTime
				+ "_clientlog");
		Thread.sleep(RESPONSETIME * 3);
	}

	@AfterClass
	public static void end() throws IOException {
		p2.destroy();
		p1.destroy();
		FileUtils.cleanDirectory(new File(server));
		FileUtils.cleanDirectory(new File(client));
	}

}
