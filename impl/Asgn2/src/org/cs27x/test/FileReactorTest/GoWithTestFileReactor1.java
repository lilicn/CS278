package org.cs27x.test.FileReactorTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FileUtils;

// this class will be called when testWatchServiceCreate() in TestFileReactor is called.
public class GoWithTestFileReactor1 {
	private Path filePath_;
	public GoWithTestFileReactor1(String toWatchDir) throws IOException, InterruptedException {
		this.filePath_ = Paths.get(toWatchDir + "1.txt");
		createFile();
		delete();
		modify();	
		delete();
	}
	
	
	public void createFile() throws IOException{	
		if(Files.exists(filePath_)) Files.delete(filePath_);
		if (!Files.exists(filePath_))
			Files.createFile(filePath_);
	}
	
	public void modify() throws IOException{
		createFile();
		File file = filePath_.toFile();
		FileUtils.write(file, "modify file");
	}
	
	public void delete() throws IOException{
		if(Files.exists(filePath_)) Files.delete(filePath_);
	}
	
	public static void main(String[] args) throws IOException, InterruptedException{
		String toWatchDir = "test-data/specialDir/";
		GoWithTestFileReactor1 object = new GoWithTestFileReactor1(toWatchDir);
	}
}
