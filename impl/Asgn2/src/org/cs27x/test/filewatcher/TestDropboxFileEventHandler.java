package org.cs27x.test.filewatcher;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent.Kind;

import org.cs27x.dropbox.DefaultFileManager;
import org.cs27x.dropbox.DropboxProtocol;
import org.cs27x.dropbox.FileManager;
import org.cs27x.filewatcher.DropboxFileEventHandler;
import org.cs27x.filewatcher.FileEvent;
import org.cs27x.filewatcher.FileEventHandler;
import org.cs27x.filewatcher.FileStates;
import org.junit.Test;
public class TestDropboxFileEventHandler {

	
	@Test
	public void testDropboxFileEventHandler() {
		FileManager mockFileManager = mock(FileManager.class);
		FileStates mockFileStates = mock(FileStates.class);
		DropboxProtocol mockDropboxProtocol = mock(DropboxProtocol.class);
		FileEventHandler fileEventHandler = new DropboxFileEventHandler(mockFileManager,mockFileStates,mockDropboxProtocol);
		assertFalse(fileEventHandler.equals(null));
	}

	@Test
	public void testNewFileEventAfterResolve(){
		Path dir = Paths.get("test-data/specialDir/");
		DefaultFileManager fileManager = new DefaultFileManager(dir);
		FileStates mockFileStates = mock(FileStates.class);
		DropboxProtocol mockDropboxProtocol = mock(DropboxProtocol.class);		
		DropboxFileEventHandler fileEventHandler = new DropboxFileEventHandler(fileManager,mockFileStates,mockDropboxProtocol);
		
		// if path is a file
		Path p = Paths.get("1.txt");
		FileEvent fileEvent = new FileEvent(ENTRY_CREATE,p);
		FileEvent newEvt = fileEventHandler.newFileEventAfterResolve(fileEvent);	
		assertEquals(Paths.get("test-data/specialDir/1.txt"),newEvt.getFile());
		
		// if path is a dir
		p = Paths.get("1/");
		fileEvent = new FileEvent(ENTRY_CREATE,p);
		newEvt = fileEventHandler.newFileEventAfterResolve(fileEvent);	
		assertEquals(Paths.get("test-data/specialDir/1/"),newEvt.getFile());
		
		// if path is a file in a dir
		// fail
		p = Paths.get("1/1.txt");
		fileEvent = new FileEvent(ENTRY_CREATE,p);
		newEvt = fileEventHandler.newFileEventAfterResolve(fileEvent);	
		assertEquals(Paths.get("test-data/specialDir/1/1.txt"),newEvt.getFile());		
	}
	
	@Test
	public void testNewFileEventAfterFilter() throws IOException{
		FileManager mockFileManager = mock(FileManager.class);
		FileStates mockFileStates = mock(FileStates.class);
		DropboxProtocol mockDropboxProtocol = mock(DropboxProtocol.class);		
		DropboxFileEventHandler fileEventHandler = new DropboxFileEventHandler(mockFileManager,mockFileStates,mockDropboxProtocol);
		FileEvent evt = mock(FileEvent.class);
		fileEventHandler.newFileEventAfterFilter(evt);
		verify(mockFileStates,times(1)).filter(evt);
	}
	
	@Test
	public void testFinalHandle() throws IOException{
		FileManager mockFileManager = mock(FileManager.class);
		FileStates mockFileStates = mock(FileStates.class);
		DropboxProtocol mockDropboxProtocol = mock(DropboxProtocol.class);		
		DropboxFileEventHandler fileEventHandler = new DropboxFileEventHandler(mockFileManager,mockFileStates,mockDropboxProtocol);
	
		// if event kind is ENTRY_CREATE
		Kind<Path> kind = ENTRY_CREATE;
		FileEvent fileEvent = new FileEvent(kind, null);	
		fileEventHandler.finalHandle(fileEvent);
		verify(mockDropboxProtocol,times(1)).addFile(fileEvent.getFile());
		
		// if event kind is ENTRY_MODIFY
		kind = ENTRY_MODIFY;
		fileEvent = new FileEvent(kind, null);	
		fileEventHandler.finalHandle(fileEvent);
		verify(mockDropboxProtocol,times(1)).updateFile(fileEvent.getFile());
		
		// if event kind is ENTRY_DELETE
		kind = ENTRY_DELETE;
		fileEvent = new FileEvent(kind, null);	
		fileEventHandler.finalHandle(fileEvent);
		verify(mockDropboxProtocol,times(1)).removeFile(fileEvent.getFile());
	}

}
