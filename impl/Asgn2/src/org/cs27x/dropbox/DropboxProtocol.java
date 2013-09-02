package org.cs27x.dropbox;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.IOUtils;
import org.cs27x.dropbox.DropboxCmd.OpCode;
import org.cs27x.filewatcher.FileStates;

public class DropboxProtocol {

	private final DropboxTransport transport_;
	
	private final DropboxCmdProcessor cmdProcessor_;

	public DropboxProtocol(DropboxTransport transport, FileStates states, FileManager filemgr) {
		transport_ = transport;
		cmdProcessor_ = new DropboxCmdProcessor(states,filemgr);
		transport_.addListener(cmdProcessor_);
	}

	public void connect(String initialPeer) {
		transport_.connect(initialPeer);
	}

	public void publish(DropboxCmd cmd) {
		transport_.publish(cmd);
	}

	public void addFile(Path p) throws IOException {
		DropboxCmd cmd = new DropboxCmd();
		cmd.setOpCode(OpCode.ADD);
		cmd.setPath(p.getFileName().toString());
		try {
			// add after test
			// only setData when it is not a dir and file exist
			if(!Files.isDirectory(p) && Files.exists(p)){
				try (InputStream in = Files.newInputStream(p)) {
					byte[] data = IOUtils.toByteArray(in);
					cmd.setData(data);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		publish(cmd);
	}

	public void removeFile(Path p) {
		DropboxCmd cmd = new DropboxCmd();
		cmd.setOpCode(OpCode.REMOVE);
		cmd.setPath(p.getFileName().toString());
		publish(cmd);
	}

	public void updateFile(Path p) throws IOException {
		DropboxCmd cmd = new DropboxCmd();
		cmd.setOpCode(OpCode.UPDATE);
		cmd.setPath(p.getFileName().toString());

		try {
			// modify after test
			// not add data if it is directory or not exist
			if(Files.exists(p) && !Files.isDirectory(p)){
				try (InputStream in = Files.newInputStream(p)) {
					byte[] data = IOUtils.toByteArray(in);
					cmd.setData(data);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		publish(cmd);
	}
}
