package org.cs27x.test.filewatcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.file.Path;
import java.nio.file.WatchEvent.Kind;

import org.cs27x.filewatcher.FileEvent;
import org.junit.Test;

public class TestFileEvent {
	
	@Test
	public void testFileEvent() {
		Kind<?> mock_kind = mock(Kind.class);
		Path mock_path = mock(Path.class);
		FileEvent fileEvent = new FileEvent(mock_kind,mock_path);
		assertFalse(fileEvent.equals(null));
	}

	@Test
	public void testGetFile() {
		Kind<?> mock_kind = mock(Kind.class);
		Path mock_path = mock(Path.class);
		when(mock_path.toString()).thenReturn("mock_path");
		FileEvent fileEvent = new FileEvent(mock_kind,mock_path);
		assertEquals("mock_path",fileEvent.getFile().toString());
	}

	@Test
	public void testGetEventType() {
		Kind<?> mock_kind = mock(Kind.class);
		Path mock_path = mock(Path.class);
		when(mock_kind.name()).thenReturn("event type");
		FileEvent fileEvent = new FileEvent(mock_kind,mock_path);
		assertEquals("event type",fileEvent.getEventType().name());
	}

}
