package org.cs27x.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.cs27x.util.Args;
import org.cs27x.util.FileOperation;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * This class is used to test the behavior of modifying files
 * 
 * @author Li
 * 
 */
public class ModifyFileTest {
	private static String server;
	private static String client;
	private static int RESPONSETIME;
	private static List<Path> serverFileList;
	private static List<Path> clientFileList;

	@BeforeClass
	public static void init() throws IOException, InterruptedException {
		String[] strs = Args.getArgs();
		server = strs[0];
		client = strs[1];
		RESPONSETIME = Args.DEFAULTRESPONSETIME;

		initCreateFiles();
	}

	public static void initCreateFiles() throws IOException,
			InterruptedException {
		serverFileList = new ArrayList<Path>();
		clientFileList = new ArrayList<Path>();
		for (int i = 0; i < 10; i++) {
			Path serverFilePath = Paths.get(server + "test_" + i);
			Path clientFilePath = Paths.get(client + "test_" + i);
			Files.createFile(serverFilePath);
			serverFileList.add(serverFilePath);
			clientFileList.add(clientFilePath);
			Thread.sleep(RESPONSETIME);
		}
	}

	@Test
	public void modifyNameTest() throws IOException, InterruptedException {
		// modify from server
		for (int i = serverFileList.size() - 1; i >= 0; i--) {
			Path[] oldPaths = { serverFileList.get(i), clientFileList.get(i) };
			long currentTime = System.currentTimeMillis();
			Path[] newPaths = { Paths.get(server + "test_" + currentTime),
					Paths.get(client + "test_" + currentTime) };
			int j = i % 2 == 0 ? 0 : 1;
			// modify oldPaths[j] to newPaths[j]
			FileUtils.moveFile(oldPaths[j].toFile(), newPaths[j].toFile());

			serverFileList.set(i, Paths.get(server + "test_" + currentTime));
			clientFileList.set(i, Paths.get(client + "test_" + currentTime));

			Thread.sleep(RESPONSETIME);
			assertTrue(
					newPaths[~j + 2].toString()
							+ " content is different when modify file name",
					FileUtils.contentEquals(newPaths[0].toFile(),
							newPaths[1].toFile()));

			assertTrue(
					"files in two directory are different when modify file name of "
							+ oldPaths[j], FileOperation.compareInDir(new File(
							server), new File(client)));
		}
	}

	@Test
	public void modifyContentTest() throws IOException, InterruptedException {
		// modify from server
		for (int i = serverFileList.size() - 1; i >= 0; i--) {
			Path[] paths = { serverFileList.get(i), clientFileList.get(i) };
			int j = i % 2 == 0 ? 0 : 1;

			// write new line in paths[j]
			FileUtils.write(paths[j].toFile(),
					"# modify file @ " + System.currentTimeMillis() + "/r/n");
			Thread.sleep(RESPONSETIME * 5);
			assertTrue(
					paths[~j + 2].toString() + " content is not modifed",
					FileUtils.contentEquals(paths[0].toFile(),
							paths[1].toFile()));
			assertTrue(
					"files in two directory are different when modify file content of "
							+ paths[j], FileOperation.compareInDir(new File(
							server), new File(client)));

			// append new line in paths[~j+2]
			FileUtils.write(paths[~j + 2].toFile(),
					"# modify file @ " + System.currentTimeMillis() + "/r/n",
					true);
			Thread.sleep(RESPONSETIME * 5);
			assertTrue(
					paths[j].toString() + " content is not modifed",
					FileUtils.contentEquals(paths[0].toFile(),
							paths[1].toFile()));
			assertTrue(
					"files in two directory are different when modify file content of "
							+ paths[~j + 2], FileOperation.compareInDir(
							new File(server), new File(client)));

			// append new line in path[j]
			FileUtils.write(paths[j].toFile(),
					"# modify file @ " + System.currentTimeMillis() + "/r/n",
					true);
			Thread.sleep(RESPONSETIME * 5);
			assertTrue(
					paths[~j + 2].toString() + " content is not modifed",
					FileUtils.contentEquals(paths[0].toFile(),
							paths[1].toFile()));
			assertTrue(
					"files in two directory are different when modify file content of "
							+ paths[j], FileOperation.compareInDir(new File(
							server), new File(client)));

			// clear content in path[~j+2]
			FileUtils.write(paths[~j + 2].toFile(), "");
			Thread.sleep(RESPONSETIME * 5);
			assertTrue(
					paths[j].toString() + " content is not modifed",
					FileUtils.contentEquals(paths[0].toFile(),
							paths[1].toFile()));
			assertTrue(
					"files in two directory are different when modify file content of "
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
