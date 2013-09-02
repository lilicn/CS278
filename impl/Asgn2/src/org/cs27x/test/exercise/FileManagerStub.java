package org.cs27x.test.exercise;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

import org.cs27x.dropbox.FileManager;

public class FileManagerStub implements FileManager {
	
	private final FileTime modificationTime_;
	
	public FileManagerStub(FileTime modtime){
		modificationTime_ = modtime;
	}
	
	@Override
	public void write(Path p, byte[] data, boolean overwrite)
			throws IOException {
	}

	@Override
	public Path resolve(String relativePathName) {
		return null;
	}

	@Override
	public boolean exists(Path p) {
		return false;
	}

	@Override
	public FileTime getLastModifiedTime(Path p) throws IOException {
		return modificationTime_;
	}

	@Override
	public void delete(Path p) throws IOException {
	}
}


