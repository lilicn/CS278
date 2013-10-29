package org.cs27x.filewatcher;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileStates implements FileStatesInterface {

	private Map<String, FileState> states_ = new ConcurrentHashMap<>();

	public FileState getState(Path p) {
		if(p==null) return null; //add after test
		String key = p.toAbsolutePath().toString();
		return states_.get(key);
	}

	public FileState getOrCreateState(Path p) {
		if(p==null) return null; //add after test
		String key = p.toAbsolutePath().toString();
		FileState state = states_.get(key);
		if (state == null) {
			state = new FileState(-1, null);
			states_.put(key, state);
		}
		return state;
	}

	public FileState insert(Path p) throws IOException {
		if(p==null) return null; // add after test
		String key = p.toAbsolutePath().toString();
		
		if(!Files.exists(p)) return null; // add after test
				
		long size = Files.size(p);
		
		if(Files.isDirectory(p)) size = getFolderSize(p.toFile()); // add after test

		FileTime mod = Files.getLastModifiedTime(p);
		FileState state = new FileState(size, mod);

		states_.put(key, state);		
		return state;
	}
	
	// add after test
	public long getFolderSize(File dir) {
	    long size = 0;
	    for (File file : dir.listFiles()) {
	        if (file.isFile()) {
	            size += file.length();
	        }
	        else
	            size += getFolderSize(file);
	    }
	    return size;
	}

	private FileEvent filterCreateOrUpdate(FileEvent evt, FileState state,
			Path p) throws IOException {
		FileEvent rslt = evt;

		if (state != null) {
			long size = Files.size(p);
			FileTime time = Files.getLastModifiedTime(evt.getFile());

			rslt = (size != state.getSize() || time.compareTo(state
					.getLastModificationDate()) > 0) ? evt : null;
		}

		return rslt;
	}

	private FileEvent filterDelete(FileEvent evt, FileState state, Path p) {
		FileEvent rslt = null;

		if (state != null && state.getSize() != -1) {
			state.setSize(-1);
			rslt = evt;
		}
		return rslt;
	}

	public FileEvent filter(FileEvent evt) throws IOException {
		Path p = evt.getFile().toAbsolutePath();

		FileState state = states_.get(p.toString());

		FileEvent event = (evt.getEventType() == ENTRY_CREATE || evt
				.getEventType() == ENTRY_MODIFY) ? filterCreateOrUpdate(evt,
				state, p) : filterDelete(evt, state, p);
				
		return event;
	}

}
