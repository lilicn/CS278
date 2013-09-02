package org.cs27x.test.exercise;

import java.io.IOException;
import java.nio.file.Path;

import org.cs27x.filewatcher.FileEvent;
import org.cs27x.filewatcher.FileState;
import org.cs27x.filewatcher.FileStatesInterface;

public class FileStatesStubInClass implements FileStatesInterface {

	FileState fileState = null;
	FileEvent fileEvent = null;

	public void setFileState(FileState fileState) {
		this.fileState = fileState;
	}

	public void setFileEvent(FileEvent fileEvent) {
		this.fileEvent = fileEvent;
	}

	@Override
	public FileState getState(Path p) {
		return this.fileState;
	}

	@Override
	public FileState getOrCreateState(Path p) {
		return this.fileState;
	}

	@Override
	public FileState insert(Path p) throws IOException {
		return this.fileState;
	}

	@Override
	public FileEvent filter(FileEvent evt) throws IOException {
		return this.fileEvent;
	}

}
