package org.cs27x.test.exercise;

import java.io.IOException;
import java.nio.file.Path;

import org.cs27x.filewatcher.FileEvent;
import org.cs27x.filewatcher.FileState;
import org.cs27x.filewatcher.FileStatesInterface;

public class FileStatesStub implements FileStatesInterface {

	private final FileState state_;
	
	public FileStatesStub(FileState state) {
		state_ = state;
	}
	
	@Override
	public FileState getState(Path p) {
		return state_;
	}

	@Override
	public FileState getOrCreateState(Path p) {
		return getState(p);
	}

	@Override
	public FileState insert(Path p) throws IOException {
		return null;
	}

	@Override
	public FileEvent filter(FileEvent evt) throws IOException {
		return null;
	}
}

