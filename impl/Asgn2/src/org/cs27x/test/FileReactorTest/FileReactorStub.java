package org.cs27x.test.FileReactorTest;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.cs27x.filewatcher.FileEvent;
import org.cs27x.filewatcher.FileEventHandler;
import org.cs27x.filewatcher.FileReactorInterface;

public class FileReactorStub implements FileReactorInterface {

	private class Dispatcher implements Runnable {
		private final FileEventHandler handler_;
		private final FileEvent event_;

		public Dispatcher(FileEventHandler handler, FileEvent event) {
			super();
			handler_ = handler;
			event_ = event;
		}

		public void run() {
			try {
				handler_.handle(event_);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	private final Path toWatch_;
	private ExecutorService executor_;
	private List<FileEventHandler> fileHandlers_ = new ArrayList<>();
	private boolean running_;

	private WatchEvent<?> watchEvent;
	
	public FileReactorStub(Path toWatch, ExecutorService executor) {
		super();
		toWatch_ = toWatch.toAbsolutePath();
		executor_ = executor;
	}

	public FileReactorStub(Path toWatch) {
		this(toWatch, Executors.newSingleThreadExecutor());
	}

	@Override
	public void start() throws IOException {
		running_ = true;
		watchLoop();
	}

	public void singleWatch(Kind<?> kind) throws IOException {
		WatchService watcher = FileSystems.getDefault().newWatchService();
		toWatch_.register(watcher, kind);

		// wait for key to be signaled
		WatchKey key = null;
		try {
			key = watcher.take();
		} catch (InterruptedException x) {
			
		}
		if (key != null) {
			System.out.println("0");
			for (WatchEvent<?> event : key.pollEvents()) {
				watchEvent = event;
				System.out.println("Processing event...");
			}
		}
	}

	public WatchEvent<?> getWatchEvent(){
		return watchEvent;
	}
	
	private void watchLoop() throws IOException {
		WatchService watcher = FileSystems.getDefault().newWatchService();
		toWatch_.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		boolean isFirst = true;
		while (running_) {

			// wait for key to be signaled
			WatchKey key;
			try {

				key = watcher.take();

			} catch (InterruptedException x) {
				return;
			}

			if (key != null) {
				for (WatchEvent<?> event : key.pollEvents()) {
					
					if (!running_) {
						return;
					}
					System.out.println("Processing event...");
					if(isFirst){
						watchEvent = event;
						isFirst = false;
					}
					processEvent(event);

				}

				// Reset the key -- this step is critical if you want to
				// receive further watch events. If the key is no longer valid,
				// the directory is inaccessible so exit the loop.
				boolean valid = key.reset();
				if (!valid) {
					break;
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void processEvent(WatchEvent<?> event) {
		WatchEvent.Kind<?> kind = event.kind();
		// This key is registered only
		// for ENTRY_CREATE events,
		// but an OVERFLOW event can
		// occur regardless if events
		// are lost or discarded.
		if (kind != OVERFLOW) {

			// The filename is the
			// context of the event.
			WatchEvent<Path> ev = (WatchEvent<Path>) event;
			Path filename = ev.context();

			// Resolve the filename against the directory.
			// If the filename is "test" and the directory is "foo",
			// the resolved name is "test/foo".
			Path child = toWatch_.resolve(filename);
			
			try {
				FileEvent evt = new FileEvent(kind, child);
				dispatch(evt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void stop() {
		running_ = false;
		Thread.interrupted();
	}

	public void dispatch(final FileEvent evt) {
		for (FileEventHandler h : fileHandlers_) {
			executor_.submit(new Dispatcher(h, evt));
		}
	}

	@Override
	public void addHandler(FileEventHandler h) {
		fileHandlers_.add(h);
	}

	@Override
	public void removeHandler(FileEventHandler h) {
		fileHandlers_.remove(h);
	}

}
