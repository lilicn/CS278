package edu.vanderbilt.cs278.lili.pubnubserver;

public abstract class Msg<T> {
	public abstract String getName();

	public abstract T getValue();

	public abstract boolean isValid();

	public String getMsg() {
		return getName() + "::" + getValue();
	}
}
