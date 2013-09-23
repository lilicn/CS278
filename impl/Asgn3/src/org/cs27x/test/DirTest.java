package org.cs27x.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.cs27x.util.Args;
import org.cs27x.util.FileOperation;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This class is used to test the behavior of creating, modifying and deleting
 * directory
 * 
 * @author Li
 * 
 */
public class DirTest {
	private static String server;
	private static String client;
	private static int RESPONSETIME;

	@BeforeClass
	public static void init() throws IOException {
		String[] strs = Args.getArgs();
		server = strs[0];
		client = strs[1];
		RESPONSETIME = Args.DEFAULTRESPONSETIME;
	}

	@Test
	public void dirTest() throws IOException, InterruptedException {
		long currentTime = System.currentTimeMillis();
		String serverDir = server + "test_" + currentTime;
		String clientDir = client + "test_" + currentTime;
		Path serverDirPath = Paths.get(serverDir);
		Path clientDirPath = Paths.get(clientDir);

		// create dir
		Files.createDirectory(serverDirPath);
		Thread.sleep(RESPONSETIME);
		assertTrue("file not created", Files.exists(clientDirPath));
		assertTrue("new file is not directory", clientDirPath.toFile()
				.isDirectory());
		assertTrue("files in two directories are different when create dir",
				FileOperation.compareInDir(new File(server), new File(client)));

		// modify dir name
		Path newServerDirPath = Paths.get(serverDir + "new");
		Path newClientDirPath = Paths.get(clientDir + "new");
		Files.move(serverDirPath, newServerDirPath);
		Thread.sleep(RESPONSETIME * 5);
		assertTrue("file name not modified - new file not created",
				Files.exists(newClientDirPath));
		assertTrue("file name not modified - old file not deleted",
				!Files.exists(clientDirPath));
		assertTrue("new file is not directory", newClientDirPath.toFile()
				.isDirectory());
		assertTrue(
				"files in two directories are different when modify dir name",
				FileOperation.compareInDir(new File(server), new File(client)));

		// add file in dir
		String serverFile = serverDir + "new/" + currentTime;
		String clientFile = clientDir + "new/" + currentTime;
		Path serverFilePath = Paths.get(serverFile);
		Path clientFilePath = Paths.get(clientFile);
		Files.createFile(serverFilePath);
		Thread.sleep(RESPONSETIME * 5);
		assertTrue("file not created when add file in dir",
				Files.exists(clientFilePath));
		assertTrue("new file is not a file", clientFilePath.toFile().isFile());
		assertTrue("new directories are different when add file in dir",
				FileOperation.compareDir(newServerDirPath.toFile(),
						newClientDirPath.toFile()));
		assertTrue("files in server/client are different when add file in dir",
				FileOperation.compareInDir(new File(server), new File(client)));

		// modify content of file in dir
		FileUtils.write(serverFilePath.toFile(),
				"# modify file @ " + System.currentTimeMillis() + "/r/n");
		Thread.sleep(RESPONSETIME * 5);
		assertTrue(
				" content is different when modify file content in dir",
				FileUtils.contentEquals(serverFilePath.toFile(),
						clientFilePath.toFile()));
		assertTrue(
				"new directories are different when modify file content in dir",
				FileOperation.compareDir(newServerDirPath.toFile(),
						newClientDirPath.toFile()));
		assertTrue(
				"files in two directory are different when modify file content in dir",
				FileOperation.compareInDir(new File(server), new File(client)));

		// delete dir
		FileUtils.deleteDirectory(newServerDirPath.toFile());
		Thread.sleep(RESPONSETIME * 5);
		assertTrue("Dir not deleted", !Files.exists(newClientDirPath));
		assertTrue("File in Dir not deleted", !Files.exists(clientFilePath));
		assertTrue("files in two directories are different when delete dir",
				FileOperation.compareInDir(new File(server), new File(client)));
	}

	@AfterClass
	public static void clean() throws IOException, InterruptedException {
		FileUtils.cleanDirectory(new File(server));
		Thread.sleep(RESPONSETIME * 50);
		FileUtils.cleanDirectory(new File(client));
	}

}
