package org.cs27x.test.dropbox;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.cs27x.dropbox.DropboxCmd;
import org.cs27x.dropbox.DropboxProtocol;
import org.cs27x.dropbox.DropboxTransport;
import org.cs27x.dropbox.FileManager;
import org.cs27x.dropbox.DropboxCmd.OpCode;
import org.cs27x.filewatcher.FileStates;
import org.junit.Test;

public class TestDropboxProtocol {

	@Test
	public void testDropboxProtocol() {
		DropboxTransport transport = mock(DropboxTransport.class);
		FileStates states = mock(FileStates.class);
		FileManager filemgr = mock(FileManager.class);

		DropboxProtocol protocol = new DropboxProtocol(transport, states,
				filemgr);
		assertFalse(protocol.equals(null));
	}

	@Test
	public void testConnect() {
		DropboxTransport transport = mock(DropboxTransport.class);
		FileStates states = mock(FileStates.class);
		FileManager filemgr = mock(FileManager.class);

		DropboxProtocol protocol = new DropboxProtocol(transport, states,
				filemgr);
		protocol.connect("peer");
		verify(transport, times(1)).connect(any(String.class));
	}

	@Test
	public void testPublish() {
		DropboxTransport transport = mock(DropboxTransport.class);
		FileStates states = mock(FileStates.class);
		FileManager filemgr = mock(FileManager.class);

		DropboxProtocol protocol = new DropboxProtocol(transport, states,
				filemgr);
		protocol.publish(new DropboxCmd());
		verify(transport, times(1)).publish(any(DropboxCmd.class));
	}

	@Test
	public void testAddFile() throws IOException {
		DropboxTransport transport = mock(DropboxTransport.class);
		FileStates states = mock(FileStates.class);
		FileManager filemgr = mock(FileManager.class);

		DropboxProtocol protocol = new DropboxProtocol(transport, states,
				filemgr);

		Path p = Paths.get("test-data/1");

		// if file exist
		if (!Files.exists(p))
			Files.createFile(p);
		protocol.addFile(p);
		verify(transport, times(1)).publish(any(DropboxCmd.class));

		// if file is a dir and exist
		// fail
		// add if(!Files.isDirectory(p))
		if (!Files.exists(p))
			Files.createDirectory(p);
		protocol.addFile(p);
		verify(transport, times(2)).publish(any(DropboxCmd.class));

		// if file not exist
		// fail
		// add if(Files.exists(p))
		if (Files.exists(p))
			Files.delete(p);
		protocol.addFile(p);
		verify(transport, times(3)).publish(any(DropboxCmd.class));

		// if file is a directory and not exist
		if (Files.exists(p))
			Files.delete(p);
		protocol.addFile(p);
		verify(transport, times(4)).publish(any(DropboxCmd.class));
	}

	@Test
	public void testRemoveFile() {
		DropboxTransport transport = mock(DropboxTransport.class);
		FileStates states = mock(FileStates.class);
		FileManager filemgr = mock(FileManager.class);

		DropboxProtocol protocol = new DropboxProtocol(transport, states,
				filemgr);

		Path p = Paths.get("test-data/1");
		protocol.removeFile(p);
		verify(transport, times(1)).publish(any(DropboxCmd.class));
	}

	@Test
	public void testUpdateFile() throws IOException {
		DropboxTransport transport = mock(DropboxTransport.class);
		FileStates states = mock(FileStates.class);
		FileManager filemgr = mock(FileManager.class);

		DropboxProtocol protocol = new DropboxProtocol(transport, states,
				filemgr);
		// if file exist
		Path p = Paths.get("test-data/1");
		if (!Files.exists(p))
			Files.createFile(p);
		protocol.updateFile(p);
		verify(transport, times(1)).publish(any(DropboxCmd.class));

		// if file not exist
		// fail
		// add if(Files.exists(p))
		if (Files.exists(p))
			Files.delete(p);
		protocol.updateFile(p);
		verify(transport, times(2)).publish(any(DropboxCmd.class));

		// if it is a dir
		// fail
		// add if(!Files.isDirectory(p))
		if (!Files.exists(p))
			Files.createDirectory(p);
		protocol.updateFile(p);
		verify(transport, times(3)).publish(any(DropboxCmd.class));
	}

}
