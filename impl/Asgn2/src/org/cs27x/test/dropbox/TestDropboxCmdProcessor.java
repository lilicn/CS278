package org.cs27x.test.dropbox;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

import org.cs27x.dropbox.DropboxCmd;
import org.cs27x.dropbox.DropboxCmd.OpCode;
import org.cs27x.dropbox.DropboxCmdProcessor;
import org.cs27x.dropbox.FileManager;
import org.cs27x.filewatcher.FileState;
import org.cs27x.filewatcher.FileStates;
import org.cs27x.filewatcher.FileStatesInterface;
import org.cs27x.test.exercise.FileManagerStub;
import org.cs27x.test.exercise.FileStatesStub;
import org.junit.Test;

public class TestDropboxCmdProcessor {

	@Test
	public void testRemoveWithStub() {
		final FileState state = new FileState(0, FileTime.fromMillis(0));

		DropboxCmdProcessor proc = new DropboxCmdProcessor(new FileStatesStub(
				state), new FileManagerStub(FileTime.fromMillis(0)));

		DropboxCmd cmd = new DropboxCmd();
		cmd.setOpCode(OpCode.REMOVE);
		cmd.setPath("foo");
		proc.updateFileState(cmd, Paths.get("foo"));

		assertEquals(-1, state.getSize());
	}
	
	@Test
	public void testAddUpdateMock() throws Exception {	
		String file = "foo";
		Path path = Paths.get(file );
		final FileState state = new FileState(0, FileTime.fromMillis(0));
		final FileTime changeTime = FileTime.fromMillis(101);

		FileStatesInterface states = mock(FileStates.class);
		FileManager mgr = mock(FileManager.class);
		when(mgr.getLastModifiedTime(any(Path.class))).thenReturn(changeTime);
		when(states.getOrCreateState(any(Path.class))).thenReturn(state);
		when(states.getState(any(Path.class))).thenReturn(state);
		
		DropboxCmdProcessor proc = new DropboxCmdProcessor(states, mgr);

		// add cmd
		// fail if file non-exist
		DropboxCmd cmd = new DropboxCmd();
		cmd.setOpCode(OpCode.ADD);
		cmd.setPath(file);
		byte[] data = new byte[1011];
		cmd.setData(data);
		
		proc.updateFileState(cmd, path);

		assertEquals(changeTime, state.getLastModificationDate());
		assertEquals(data.length, state.getSize());

		state.setLastModificationDate(FileTime.fromMillis(1));
		state.setSize(0);
		
		// update cmd
		cmd.setOpCode(OpCode.UPDATE);
		cmd.setPath(file );
		cmd.setData(data);
		proc.updateFileState(cmd, Paths.get(file));

		assertEquals(changeTime, state.getLastModificationDate());
		assertEquals(data.length, state.getSize());
	}

	@Test
	public void testCmdReceived() throws IOException {
		String file = "foo";
		Path path = Paths.get(file );
		final FileState state = new FileState(0, FileTime.fromMillis(0));
		final FileTime changeTime = FileTime.fromMillis(101);
		
		FileStatesInterface states = mock(FileStates.class);
		FileManager mgr = mock(FileManager.class);
		
		when(mgr.getLastModifiedTime(any(Path.class))).thenReturn(changeTime);
		when(states.getOrCreateState(any(Path.class))).thenReturn(state);
		when(states.getState(any(Path.class))).thenReturn(state);
		
		DropboxCmdProcessor proc = new DropboxCmdProcessor(states, mgr);	
		
		DropboxCmd cmd = new DropboxCmd();
		cmd.setPath(file);
		byte[] data = new byte[1011];
		cmd.setData(data);
		
		// add
		cmd.setOpCode(OpCode.ADD);
		proc.cmdReceived(cmd);
		verify(mgr,times(1)).write(any(Path.class), any(byte[].class), any(boolean.class));
		
		// update
		cmd.setOpCode(OpCode.UPDATE);
		proc.cmdReceived(cmd);
		verify(mgr,times(2)).write(any(Path.class), any(byte[].class), any(boolean.class));
		
		// remove
		cmd.setOpCode(OpCode.REMOVE);
		proc.cmdReceived(cmd);
		verify(mgr,times(1)).delete(any(Path.class));
	}

}
