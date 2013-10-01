package org.cs27x.dropbox;

import org.cs27x.filewatcher.FileEventSource;
import org.cs27x.filewatcher.FileWatcherSource;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;


public class DropboxModule extends AbstractModule {

	@Override
	protected void configure() {
		// TODO Auto-generated method stub
		//bind(DropboxClient.class).to(FileManager.class);
		
		//bind(FileEventSource.class).to(Dropbox.class);
		//bind(DropboxTransport.class).to(Dropbox.class);
		bind(FileEventSource.class).to(FileWatcherSource.class);
		bind(DropboxTransport.class).to(HazelcastTransport.class);
		bind(FileManager.class).to(FileManagerImpl.class);
		
		bindConstant().annotatedWith(Names.named("rootDir")).to("./test-data/");
		bindConstant().annotatedWith(Names.named("toWatch")).to("./test-data/");
		
	}

}
