package org.cs27x.filewatcher;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.IOException;
import java.nio.file.Path;

import org.cs27x.dropbox.DropboxProtocol;
import org.cs27x.dropbox.FileManager;

public class DropboxFileEventHandler implements FileEventHandler {

	private final DropboxProtocol transport_;
	private final FileStates fileStates_;
	private final FileManager fileHandler_;

	public DropboxFileEventHandler(FileManager hdlr, FileStates states,
			DropboxProtocol transport) {
		super();
		fileStates_ = states;
		transport_ = transport;
		fileHandler_ = hdlr;
	}

	@Override
	public void handle(FileEvent evt) {
		evt = newFileEventAfterResolve(evt);
		try {
			evt = fileStates_.filter(evt);
			finalHandle(evt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// find error after test
	public FileEvent newFileEventAfterResolve(FileEvent evt){
		Path p = evt.getFile();
		p = fileHandler_.resolve(p.getFileName().toString());
		return new FileEvent(evt.getEventType(), p);
	}
	
	public FileEvent newFileEventAfterFilter(FileEvent evt) throws IOException{
		return fileStates_.filter(evt);
	}

	public void finalHandle(FileEvent evt){
		try{
			if (evt != null) {
				if (evt.getEventType() == ENTRY_CREATE) {
					transport_.addFile(evt.getFile());
				} else if (evt.getEventType() == ENTRY_MODIFY) {
					transport_.updateFile(evt.getFile());
				} else if (evt.getEventType() == ENTRY_DELETE) {
					transport_.removeFile(evt.getFile());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
