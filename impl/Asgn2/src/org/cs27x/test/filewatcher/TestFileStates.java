package org.cs27x.test.filewatcher;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent.Kind;

import org.cs27x.filewatcher.FileEvent;
import org.cs27x.filewatcher.FileState;
import org.cs27x.filewatcher.FileStates;
import org.junit.Test;

public class TestFileStates {

	@Test
	public void testGetOrCreateState() throws IOException {
		// test with null path
		// fail in null path
		// add if(p==null) return null
		FileStates fileStates = new FileStates();
		assertNull(fileStates.getOrCreateState(null));

		// use an exist and a non-exist file as test case
		Path[] paths = { Paths.get("test-data/invariant/1"),
				Paths.get("test-data/invariant/2") };
		if (!Files.exists(paths[0]))
			Files.createFile(paths[0]);
		if (Files.exists(paths[1]))
			Files.delete(paths[1]);

		// file and exist
		// create state for a new path
		fileStates = new FileStates();
		FileState tempFS = fileStates.getOrCreateState(paths[0]);
		assertEquals(-1, tempFS.getSize());
		assertNull(tempFS.getLastModificationDate());

		// file and not exist
		fileStates = new FileStates();
		tempFS = fileStates.getOrCreateState(paths[1]);
		assertEquals(-1, tempFS.getSize());
		assertNull(tempFS.getLastModificationDate());

		// file exist and get state of an old path
		tempFS = fileStates.getOrCreateState(paths[0]);
		assertEquals(-1, tempFS.getSize());
		assertNull(tempFS.getLastModificationDate());

		// file not exist and get state of an old path
		tempFS = fileStates.getOrCreateState(paths[1]);
		assertEquals(-1, tempFS.getSize());
		assertNull(tempFS.getLastModificationDate());

		// clean
		if (Files.exists(paths[1]))
			Files.delete(paths[1]);
	}

	@Test
	public void testGetState() throws IOException {
		// test with null path
		// fail in null path
		// add if(p==null) return null;
		FileStates fileStates = new FileStates();
		assertNull(fileStates.getState(null));

		// use an exist and a non-exist file or dir as test case
		Path[] paths = { Paths.get("test-data/invariant/1"),
				Paths.get("test-data/invariant/2") };
		if (!Files.exists(paths[0]))
			Files.createFile(paths[0]);
		if (Files.exists(paths[1]))
			Files.delete(paths[1]);

		// if the path is a new path
		fileStates = new FileStates();
		assertNull(fileStates.getState(paths[0]));
		assertNull(fileStates.getState(paths[1]));

		// if this path in states_ already
		// create a new path firstly
		// exist file
		FileState tempFS = fileStates.getOrCreateState(paths[0]);
		assertEquals(-1, tempFS.getSize());
		assertNull(tempFS.getLastModificationDate());
		// get the path
		assertEquals(-1, fileStates.getState(paths[0]).getSize());
		assertNull(fileStates.getState(paths[0]).getLastModificationDate());

		// non exist file
		tempFS = fileStates.getOrCreateState(paths[1]);
		assertEquals(-1, tempFS.getSize());
		assertNull(tempFS.getLastModificationDate());
		// get the path
		assertEquals(-1, fileStates.getState(paths[1]).getSize());
		assertNull(fileStates.getState(paths[1]).getLastModificationDate());

		// clean
		if (Files.exists(paths[0]))
			Files.delete(paths[0]);

	}

	@Test
	public void testInsert() throws IOException {
		// assumming each insert, only one file or dir is related
		// insert null path
		// fail in null path
		// add if(p==null) return null;
		FileStates fileStates = new FileStates();
		assertNull(fileStates.insert(null));

		// insert exist dir path
		// fail when insert dir
		// add if(Files.isDirectory(p)) size = getFolderSize(p.toFile());
		fileStates = new FileStates();
		Path p = Paths.get("test-data/");
		if (!Files.exists(p))
			Files.createDirectory(p);
		FileState tempFS = fileStates.insert(p);
		long size = getFolderSize(p.toFile());
		assertEquals(size, tempFS.getSize());
		assertFalse(tempFS.getLastModificationDate().equals(0));

		// insert exist file path
		fileStates = new FileStates();
		p = Paths.get("test-data/invariant/2");
		if (!Files.exists(p))
			Files.createFile(p);
		tempFS = fileStates.insert(p);
		assertEquals(Files.size(p), tempFS.getSize());
		assertFalse(tempFS.getLastModificationDate().equals(0));
		// clean
		if (Files.exists(p))
			Files.delete(p);

		// insert non-exist path
		// fail in non-exist path
		// add if(!Files.exists(p)) return null;
		fileStates = new FileStates();
		p = Paths.get("test-data/invariant/3");
		if (Files.exists(p))
			Files.delete(p);
		assertNull(fileStates.insert(p));
		assertNull(fileStates.getState(p));
	}

	public long getFolderSize(File dir) {
		long size = 0;
		for (File file : dir.listFiles()) {
			if (file.isFile()) {
				size += file.length();
			} else
				size += getFolderSize(file);
		}
		return size;
	}

	@Test
	public void testFilter() throws IOException {
		// use ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE, OVERFLOW and null as
		// test case
		Kind<?>[] kinds = { ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE, OVERFLOW,
				null };
		Path p = Paths.get("test-data/invariant/2");
		if (!Files.exists(p))
			Files.createDirectory(p);
		for (Kind<?> kind : kinds) {
			FileEvent fileEvent = new FileEvent(kind, p);
			FileStates fileStates = new FileStates();

			// states==null
			if (kind == ENTRY_CREATE || kind == ENTRY_MODIFY)
				assertEquals(fileEvent, fileStates.filter(fileEvent));
			else
				assertNull(fileStates.filter(fileEvent));

			// states!=null
			// same filetime and size, return null
			FileState tempFS = fileStates.insert(p);
			if (kind == ENTRY_CREATE || kind == ENTRY_MODIFY) {
				assertNull(fileStates.filter(fileEvent));
			}else{
				// size != -1
				assertEquals(fileEvent,fileStates.filter(fileEvent));
			}
			
			// new filetime, return this event
			Files.delete(p);
			Files.createDirectory(p);
			if (kind == ENTRY_CREATE || kind == ENTRY_MODIFY) {
				assertEquals(fileEvent,fileStates.filter(fileEvent));
			}else{
				// size == -1
				assertNull(fileStates.filter(fileEvent));
			}

		}

	}

}
