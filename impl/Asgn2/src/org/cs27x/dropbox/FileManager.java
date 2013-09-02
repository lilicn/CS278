package org.cs27x.dropbox;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

public interface FileManager {
	
	public Path resolve(String relativePathName);

	public boolean exists(Path p);
	
	public void write(Path p, byte[] data, boolean overwrite) throws IOException;
	
	public void delete(Path p) throws IOException;
	
	public FileTime getLastModifiedTime(Path p) throws IOException;
	
}
