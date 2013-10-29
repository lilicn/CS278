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
 * This class is used to test the behavior of creating files
 * 
 * @author Li
 * 
 */
public class CreateFileTest {
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
	public void serverCreateTest() throws IOException, InterruptedException {
		for (int i = 0; i < 10; i++) {
			long currentTime = System.currentTimeMillis();
			Path serverFilePath = Paths.get(server + "test_" + currentTime);
			Path clientFilePath = Paths.get(client + "test_" + currentTime);
			// create file in server
			Files.createFile(serverFilePath);
			Thread.sleep(RESPONSETIME);
			assertTrue("file not created in client when create file in server",
					Files.exists(clientFilePath));
			assertTrue(
					"new created in client is not a file when create file in server",
					clientFilePath.toFile().isFile());
			assertTrue(
					"content in new file is different when create file in server",
					FileUtils.contentEquals(serverFilePath.toFile(),
							clientFilePath.toFile()));

			assertTrue(
					"files in two directory are different when create file in server",
					FileOperation.compareInDir(new File(server), new File(
							client)));

		}
	}

	@Test
	public void clientCreateTest() throws IOException, InterruptedException {
		for (int i = 0; i < 10; i++) {
			long currentTime = System.currentTimeMillis();
			Path serverFilePath = Paths.get(server + "test_" + currentTime);
			Path clientFilePath = Paths.get(client + "test_" + currentTime);

			// create file in client
			Files.createFile(clientFilePath);
			Thread.sleep(RESPONSETIME * 2);
			assertTrue(
					"file not created in the other side when create file in client",
					Files.exists(serverFilePath));
			assertTrue(
					"new created in server is not a file when create file in client",
					serverFilePath.toFile().isFile());
			assertTrue(
					"content in new file is different when create file in client",
					FileUtils.contentEquals(serverFilePath.toFile(),
							clientFilePath.toFile()));
			assertTrue(
					"files in two directory are different when create file in client",
					FileOperation.compareInDir(new File(server), new File(
							client)));
		}
	}

	@Test
	public void mixCreateTest() throws IOException, InterruptedException {
		for (int i = 0; i < 10; i++) {
			// create file in paths[j]
			long currentTime = System.currentTimeMillis();
			Path[] paths = { Paths.get(server + "test_" + currentTime),
					Paths.get(client + "test_" + currentTime) };
			int j = i % 2 == 0 ? 0 : 1;

			Files.createFile(paths[j]);
			Thread.sleep(RESPONSETIME);
			assertTrue(paths[~j + 2] + " not created",
					Files.exists(paths[~j + 2]));
			assertTrue(paths[~j + 2] + " is not a file", paths[~j + 2].toFile()
					.isFile());
			assertTrue(
					"content of " + paths[~j + 2] + " is different",
					FileUtils.contentEquals(paths[j].toFile(),
							paths[~j + 2].toFile()));
			assertTrue("files in two directory are different when create file "
					+ paths[j], FileOperation.compareInDir(new File(server),
					new File(client)));

			// create file in paths[~j+2]
			currentTime = System.currentTimeMillis();
			paths[0] = Paths.get(server + "test_" + currentTime);
			paths[1] = Paths.get(client + "test_" + currentTime);

			Files.createFile(paths[~j + 2]);
			Thread.sleep(RESPONSETIME);
			assertTrue(paths[j] + " not created", Files.exists(paths[j]));
			assertTrue(paths[j] + " is not a file", paths[j].toFile().isFile());
			assertTrue(
					"content of " + paths[j] + " is different",
					FileUtils.contentEquals(paths[j].toFile(),
							paths[~j + 2].toFile()));
			assertTrue("files in two directory are different when create file "
					+ paths[~j + 2], FileOperation.compareInDir(
					new File(server), new File(client)));
		}
	}

	@AfterClass
	public static void clean() throws IOException, InterruptedException {
		FileUtils.cleanDirectory(new File(server));
		Thread.sleep(RESPONSETIME * 50);
		FileUtils.cleanDirectory(new File(client));
	}
}
