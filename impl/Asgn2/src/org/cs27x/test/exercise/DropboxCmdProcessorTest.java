package org.cs27x.test.exercise;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
import org.junit.Test;

public class DropboxCmdProcessorTest {

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

		final FileState state = new FileState(0, FileTime.fromMillis(0));
		final FileTime changeTime = FileTime.fromMillis(101);

		FileStates states = mock(FileStates.class);
		FileManager mgr = mock(FileManager.class);
		when(mgr.getLastModifiedTime(any(Path.class))).thenReturn(changeTime);
		when(states.getOrCreateState(any(Path.class))).thenReturn(state);
		when(states.getState(any(Path.class))).thenReturn(state);

		DropboxCmdProcessor proc = new DropboxCmdProcessor(states, mgr);

		DropboxCmd cmd = new DropboxCmd();
		cmd.setOpCode(OpCode.ADD);
		cmd.setPath("foo");
		byte[] data = new byte[1011];
		cmd.setData(data);
		proc.updateFileState(cmd, Paths.get("foo"));

		assertEquals(changeTime, state.getLastModificationDate());
		assertEquals(data.length, state.getSize());

		state.setLastModificationDate(FileTime.fromMillis(1));
		state.setSize(0);

		cmd.setOpCode(OpCode.UPDATE);
		cmd.setPath("foo");
		cmd.setData(data);
		proc.updateFileState(cmd, Paths.get("foo"));

		assertEquals(changeTime, state.getLastModificationDate());
		assertEquals(data.length, state.getSize());
	}

	@Test
	public void testAddUpdateWithStub() {

		final FileState state = new FileState(0, FileTime.fromMillis(0));
		final FileTime changeTime = FileTime.fromMillis(101);

		FileStatesInterface states = new FileStatesStub(state);
		FileManager mgr = new FileManagerStub(changeTime);

		DropboxCmdProcessor proc = new DropboxCmdProcessor(states, mgr);

		DropboxCmd cmd = new DropboxCmd();
		cmd.setOpCode(OpCode.ADD);
		cmd.setPath("foo");
		byte[] data = new byte[1011];
		cmd.setData(data);
		proc.updateFileState(cmd, Paths.get("foo"));

		assertEquals(changeTime, state.getLastModificationDate());
		assertEquals(data.length, state.getSize());

		state.setLastModificationDate(FileTime.fromMillis(1));
		state.setSize(0);

		cmd.setOpCode(OpCode.UPDATE);
		cmd.setPath("foo");
		cmd.setData(data);
		proc.updateFileState(cmd, Paths.get("foo"));

		assertEquals(changeTime, state.getLastModificationDate());
		assertEquals(data.length, state.getSize());
	}

}
