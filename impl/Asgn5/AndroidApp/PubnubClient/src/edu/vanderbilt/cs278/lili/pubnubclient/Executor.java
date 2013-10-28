package edu.vanderbilt.cs278.lili.pubnubclient;

import android.widget.RelativeLayout;

public interface Executor<T> {
	public boolean isValid();
	public void exector(String body, RelativeLayout layout);
	public T getValue();
}
