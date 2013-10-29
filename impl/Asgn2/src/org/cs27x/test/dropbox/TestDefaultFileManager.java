package org.cs27x.test.dropbox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.cs27x.dropbox.DefaultFileManager;
import org.cs27x.dropbox.FileManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestDefaultFileManager {
	private static String rootdir = "test-data/specialDir/";
	
	@BeforeClass
	public static void before() throws IOException{
		Path path = Paths.get(rootdir);
		if (Files.exists(path)) {
			FileUtils.deleteDirectory(path.toFile());
		}
		Files.createDirectory(path);
	}
	
	@Test
	public void testDefaultFileManager() throws IOException {
		// not verify the path exist or not
		Path rootpath = Paths.get("test-data/specialDir/11/");		
		FileManager fileManager = new DefaultFileManager(rootpath);
		assertFalse(fileManager.equals(null));
	}

	@Test
	public void testExists() throws IOException {
		Path rootpath = Paths.get(rootdir);		
		FileManager fileManager = new DefaultFileManager(rootpath);
		
		// test an exist and non-exist file
		String file = rootdir+"1.txt";
		Path path = Paths.get(file);
		if(!Files.exists(path)) Files.createFile(path);
		assertEquals(true,fileManager.exists(path));
		if(Files.exists(path)) Files.delete(path);
		assertEquals(false,fileManager.exists(path));	

		// test an exist and non-exist dir
		String dir =  rootdir+"3/";
		path = Paths.get(dir);
		if(!Files.exists(path)) Files.createDirectory(path);
		assertEquals(true,fileManager.exists(path));
		if(Files.exists(path)) Files.delete(path);		
		assertEquals(false,fileManager.exists(path));
	}

	@Test
	public void testWrite() throws IOException {
		// not verify the file exist or not		
		Path rootpath = Paths.get(rootdir);
		
		FileManager fileManager = new DefaultFileManager(rootpath);
		// if it is a file
		Path p = Paths.get(rootdir+"1.txt");
		
		// create file
		boolean overwrite = false;
		
		// if data is null
		// fail in test
		// add if(data!=null)
		byte[] data = null;
		fileManager.write(p, data, overwrite);
		
		// if data is not null
		String s = "testdata";
		data = s.getBytes();
		fileManager.write(p, data, overwrite);
		
		// modify file
		overwrite = true;
		fileManager.write(p, data, overwrite);
		
		// if it is a dir
		p = Paths.get(rootdir+"12/");
		overwrite = false;
		fileManager.write(p, data, overwrite);
		
		// if it is a file in a dir
		if(!Files.exists(Paths.get(rootdir+"1/"))){		
			Files.createDirectory(Paths.get(rootdir+"1/"));
		}
		
		p = Paths.get(rootdir+"1/1.txt");
		//if(!Files.exists(p)) Files.createFile(p);
		overwrite = false;
		fileManager.write(p, data, overwrite);
		
	}

	@Test
	public void testDelete() throws IOException {		
		Path rootpath = Paths.get(rootdir);
		if(!Files.exists(rootpath)) Files.createDirectory(rootpath);
		
		FileManager fileManager = new DefaultFileManager(rootpath);
		
		// test an exist file
		String file = rootdir+"1.txt";
		Path path = Paths.get(file);
		if(!Files.exists(path)) Files.createFile(path);
		fileManager.delete(path);
		assertEquals(false,fileManager.exists(path));
		
		// test a non-exist file
		if(Files.exists(path)) Files.delete(path);
		fileManager.delete(path);
		assertEquals(false,fileManager.exists(path));	

		// test an exist dir
		String dir =  rootdir+"2/";
		path = Paths.get(dir);
		if(!Files.exists(path)) Files.createDirectory(path);
		fileManager.delete(path);
		assertEquals(false,fileManager.exists(path));
		
		// test a non-exist dir
		if(Files.exists(path)) Files.delete(path);
		fileManager.delete(path);
		assertEquals(false,fileManager.exists(path));
	}

	@Test
	public void testResolve() throws IOException {
		Path rootpath = Paths.get(rootdir);
		if(!Files.exists(rootpath)) Files.createDirectory(rootpath);
		
		FileManager fileManager = new DefaultFileManager(rootpath);
		// test a file
		String relativePath = "1.txt";
		assertEquals(Paths.get(rootdir+relativePath),fileManager.resolve(relativePath));
		
		// test a dir
		relativePath = "12/";
		assertEquals(Paths.get(rootdir+relativePath),fileManager.resolve(relativePath));
		
		// test a file in a dir
		relativePath = "12/1.txt";
		assertEquals(Paths.get(rootdir+relativePath),fileManager.resolve(relativePath));
	}
	
	@AfterClass
	public static void after() throws IOException{
		Path path = Paths.get(rootdir);
		if (Files.exists(path)) {
			FileUtils.deleteDirectory(path.toFile());
		}
	}

}
