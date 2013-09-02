package org.cs27x.test.filewatcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

import org.cs27x.filewatcher.FileState;
import org.junit.Test;

public class TestFileState {

	@Test
	public void testFileState() {
		FileState fileState = new FileState(-1,null);
		assertFalse(fileState.equals(null));
	}

	@Test
	public void testGetAndSetSize() {
		for(int i = -100; i<=100; i++){
			FileState fileState = new FileState(i,null);
			assertEquals(i,fileState.getSize());
			for(int j = -100; j<=100; j++){
				fileState.setSize(j);
				assertEquals(j,fileState.getSize());
			}
		}	
	}

	@Test
	public void testGetAndSetLastModificationDate() {
		FileState fileState = new FileState(-1,null);
		assertNull(fileState.getLastModificationDate());
		
		FileTime ft = FileTime.fromMillis(100);
		fileState.setLastModificationDate(ft);
		assertEquals(ft,fileState.getLastModificationDate());	
	}

}
