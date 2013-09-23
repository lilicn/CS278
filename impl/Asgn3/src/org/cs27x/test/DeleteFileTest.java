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
 * This class is used to test the behavior of deleting files
 * 
 * @author Li
 * 
 */
public class DeleteFileTest {
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
		createFiles();
	}

	public static void createFiles() throws IOException, InterruptedException {
		serverFileList = new ArrayList<Path>();
		clientFileList = new ArrayList<Path>();
		for (int i = 0; i < 10; i++) {
			long currentTime = System.currentTimeMillis();
			Path serverFilePath = Paths.get(server + "test_" + currentTime);
			Path clientFilePath = Paths.get(client + "test_" + currentTime);
			serverFileList.add(serverFilePath);
			clientFileList.add(clientFilePath);

			// create file in server
			Files.createFile(serverFilePath);
			Thread.sleep(RESPONSETIME);
		}
	}

	@Test
	public void deleteTest() throws IOException, InterruptedException {
		for (int i = serverFileList.size() - 1; i >= 0; i--) {
			Path[] paths = { serverFileList.get(i), clientFileList.get(i) };
			int j = i % 2 == 0 ? 0 : 1;

			Files.delete(paths[j]);
			Thread.sleep(RESPONSETIME);
			assertTrue(paths[~j + 2] + " not deleted when delete file - "
					+ paths[j], !Files.exists(paths[~j + 2]));
			assertTrue(
					"files in two directory are different when delete file - "
							+ paths[j], FileOperation.compareInDir(new File(
							server), new File(client)));
		}
	}

	@AfterClass
	public static void clean() throws IOException, InterruptedException {
		FileUtils.cleanDirectory(new File(server));
		Thread.sleep(RESPONSETIME * 50);
		FileUtils.cleanDirectory(new File(client));
	}

}
