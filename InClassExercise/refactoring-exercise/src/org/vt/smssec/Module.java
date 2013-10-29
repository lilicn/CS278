package org.vt.smssec;

public interface Module<T> {

	public Class<? extends T> getComponent(String str);

	public void setComponent(String str, Class<? extends T> type);
	
	public abstract T getInstance(String str) throws InstantiationException, IllegalAccessException;
	

}

