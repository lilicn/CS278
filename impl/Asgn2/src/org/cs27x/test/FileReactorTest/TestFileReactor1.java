package org.cs27x.test.FileReactorTest;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestFileReactor1 {
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

	/**
	 * the following three methods should go with GoWithTestFileReactor1.java
	 * 
	 * @throws IOException
	 */
	@Test
	public void testWatchServiceCreate() throws IOException {
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
	}

	@AfterClass
	public static void after() throws IOException {
		if (Files.exists(path_)) {
			FileUtils.deleteDirectory(path_.toFile());
		}
	}
}
