package org.cs27x.filewatcher;

import java.io.IOException;
import java.nio.file.Path;

public interface FileStatesInterface {

	public FileState getState(Path p);
	public FileState getOrCreateState(Path p);
	public FileState insert(Path p) throws IOException;
	public FileEvent filter(FileEvent evt) throws IOException;
}
