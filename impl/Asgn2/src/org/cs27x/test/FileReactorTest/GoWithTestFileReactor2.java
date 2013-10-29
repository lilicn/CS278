package org.cs27x.test.FileReactorTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

// this class will be called when testWatchServiceCreate() in FileReactorTest is called.
public class GoWithTestFileReactor2 {
	private Path filePath_;
	private Path dirPath_;
	public GoWithTestFileReactor2(String toWatchDir) throws IOException, InterruptedException {
		this.dirPath_ = Paths.get(toWatchDir + "1/");
		this.filePath_ = Paths.get(toWatchDir + "1/1.txt");
		createDir();
		Thread.sleep(2);
		createFile();
		Thread.sleep(2);
		delete();
		Thread.sleep(2);
		modify();	
		Thread.sleep(2);
		delete();
	}
	
	public void createDir() throws IOException{
		if(Files.exists(dirPath_)) Files.delete(dirPath_);
		if (!Files.exists(dirPath_))
			Files.createDirectory(dirPath_);
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
		GoWithTestFileReactor2 object = new GoWithTestFileReactor2(toWatchDir);
	}
}
