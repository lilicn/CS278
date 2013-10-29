package org.cs27x.test.exercise;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

import org.cs27x.dropbox.FileManager;

public class FileManagerStubInClass implements FileManager {

	Path path_;
	boolean mBoolean;

	public void setPath(Path path) {
		this.path_ = path;
	}

	public void setBoolean(boolean booleanValue) {
		this.mBoolean = booleanValue;
	}

	@Override
	public Path resolve(String relativePathName) {
		return this.path_;
	}

	@Override
	public boolean exists(Path p) {
		return this.mBoolean;
	}

	@Override
	public void write(Path p, byte[] data, boolean overwrite)
			throws IOException {
	}

	@Override
	public void delete(Path p) throws IOException {
	}

	@Override
	public FileTime getLastModifiedTime(Path p) throws IOException {
		return null;
	}
}
