package org.cs27x.test.dropbox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

import org.cs27x.dropbox.DropboxCmd;
import org.cs27x.dropbox.DropboxCmd.OpCode;
import org.junit.Test;

public class TestDropboxCmd {

	@Test
	public void testGetAndSetFrom() {
		DropboxCmd cmd = new DropboxCmd();
		assertFalse(cmd.equals(null));

		// null
		assertNull(cmd.getFrom());
		String from = "testFrom";

		// not null
		cmd.setFrom("testFrom");
		assertEquals(from, cmd.getFrom());
	}

	@Test
	public void testGetAndSetPath() {
		DropboxCmd cmd = new DropboxCmd();

		// null
		assertNull(cmd.getPath());
		String path = "test path";

		// not null
		cmd.setPath(path);
		assertEquals(path, cmd.getPath());
	}

	@Test
	public void testGetAndSetData() {
		DropboxCmd cmd = new DropboxCmd();

		// null
		assertNull(cmd.getPath());
		String str = "test data";
		byte[] data = str.getBytes();

		// not null
		cmd.setData(data);
		assertEquals(data, cmd.getData());
	}

	@Test
	public void testGetAndSetOpCode() {
		DropboxCmd cmd = new DropboxCmd();

		// null
		assertNull(cmd.getOpCode());	
		
		// not null
		OpCode[] opCodes={OpCode.ADD, OpCode.REMOVE, OpCode.UPDATE, OpCode.SYNC, OpCode.GET};
		for(OpCode opCode:opCodes){
			cmd.setOpCode(opCode);
			assertEquals(opCode, cmd.getOpCode());
		}	
	}

}
