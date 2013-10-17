package org.vt.smssec;

public interface Module {

	public <T> Class<T> getComponent(String str);

	public <T> void setComponent(String str,Class<T> type);
	
	public abstract <T> T getInstance(String str) throws InstantiationException, IllegalAccessException;
	

}

