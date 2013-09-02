package org.cs27x.test.exercise;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static org.mockito.Mockito.mock;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.cs27x.dropbox.DropboxCmd;
import org.cs27x.dropbox.DropboxCmdProcessor;
import org.cs27x.dropbox.FileManager;
import org.cs27x.filewatcher.FileEvent;
import org.cs27x.filewatcher.FileStates;
import org.junit.Assert;
import org.junit.Test;
public class DropboxCmdProcessorTestInClass {

	@Test
	public void testCreator() {

		FileManagerStubInClass fileManagerStub = new FileManagerStubInClass();
		FileStatesStubInClass fileStatesStub = new FileStatesStubInClass();

		boolean testBooleanValue = false;
		Path testPath = Paths.get("/test/file.txt");

		fileManagerStub.setBoolean(testBooleanValue);
		fileManagerStub.setPath(testPath);

		FileEvent testFileEvent = new FileEvent(ENTRY_CREATE, testPath);

		fileStatesStub.setFileEvent(testFileEvent);

		DropboxCmdProcessor testDCP = new DropboxCmdProcessor(fileStatesStub,
				fileManagerStub);

		Assert.assertNotNull(testDCP);
	}

	
	@Test 
	public void testUpdateFileState(){
		
		FileManagerStubInClass fileManagerStub = new FileManagerStubInClass();
		FileStatesStubInClass fileStatesStub = new FileStatesStubInClass();

		boolean testBooleanValue = false;
		Path testPath = Paths.get("/test/file.txt");

		fileManagerStub.setBoolean(testBooleanValue);
		fileManagerStub.setPath(testPath);

		FileEvent testFileEvent = new FileEvent(ENTRY_CREATE, testPath);

		fileStatesStub.setFileEvent(testFileEvent);

		DropboxCmdProcessor testDCP = new DropboxCmdProcessor(fileStatesStub,
				fileManagerStub);
		
		Path pathToTest = Paths.get("/test/file.txt");
		DropboxCmd cmd = new DropboxCmd();
		///
		// TODO config cmd
		//
		
		testDCP.updateFileState(cmd, pathToTest);
		
	}
	
	@Test
	public void testUpdateFileStateMock(){
		FileManager mockFM =  mock(FileManager.class);
		//when(mockFM.).thenReturn();
		FileStates mockFS = mock(FileStates.class);
		
		
		DropboxCmdProcessor testDCP = new DropboxCmdProcessor(mockFS, mockFM);
		
		
		
	}
	
}
