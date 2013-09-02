package org.cs27x.test.FileReactorTest;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.cs27x.filewatcher.FileReactor;
import org.cs27x.filewatcher.FileReactorInterface;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestFileReactor2 {
	private static String toWatchDir_;
	private static Path path_;

	@BeforeClass
	public static void before() throws IOException {
		toWatchDir_ = "test-data/specialDir/";
		path_ = Paths.get(toWatchDir_);
		if (Files.exists(path_)) {
			FileUtils.deleteDirectory(path_.toFile());
		}
		Files.createDirectory(path_);
	}
	
	@Test
	public void testFileReactorPathExecutorService() {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		FileReactor fileReactor = new FileReactor(path_, executor);
		assertFalse(fileReactor.equals(null));
	}

	@Test
	public void testFileReactorPath() {
		FileReactor fileReactor = new FileReactor(path_);
		assertFalse(fileReactor.equals(null));
	}

	/**
	 * the following four methods should go with GoWithTestFileReactor2.java
	 * 
	 * @throws IOException
	 */
	@Test
	public void testWatchServiceCreateD() throws IOException {
		final FileReactorStub fileReactor = new FileReactorStub(path_);
		fileReactor.singleWatch(ENTRY_CREATE);

		// run CreateNewFile
		WatchEvent<?> event = fileReactor.getWatchEvent();
		assertEquals(ENTRY_CREATE, event.kind());
	}
	
	// fail
	// cannot detect file in the above dir
	@Test
	public void testWatchServiceCreateF() throws IOException {

		final FileReactorStub fileReactor = new FileReactorStub(path_);
		fileReactor.singleWatch(ENTRY_CREATE);

		// run CreateNewFile
		WatchEvent<?> event = fileReactor.getWatchEvent();
		assertEquals(ENTRY_CREATE, event.kind());
	}

	@Test
	public void testWatchServiceDelete() throws IOException {
		final FileReactorStub fileReactor = new FileReactorStub(path_);
		fileReactor.singleWatch(ENTRY_DELETE);

		WatchEvent<?> event = fileReactor.getWatchEvent();
		assertEquals(ENTRY_DELETE, event.kind());
	}

	@Test
	public void testWatchServiceModify() throws IOException {
		final FileReactorStub fileReactor = new FileReactorStub(path_);

		fileReactor.singleWatch(ENTRY_MODIFY);

		WatchEvent<?> event = fileReactor.getWatchEvent();
		assertEquals(ENTRY_MODIFY, event.kind());
		System.out.println(event.context());
	}

	@AfterClass
	public static void after() throws IOException {
		if (Files.exists(path_)) {
			FileUtils.deleteDirectory(path_.toFile());
		}
	}
}
