package org.cs27x.filewatcher;

import java.io.IOException;

public interface FileReactorInterface {

	public void start() throws IOException;
	public void stop();
	public void addHandler(FileEventHandler h);
	public void removeHandler(FileEventHandler h);
	
	
}
