package org.cs27x.di;

import java.util.HashMap;

public class DataStoreImpl implements DataStore {

	private HashMap<String, byte[]> hm_ = new HashMap<String, byte[]>(); 
	
	@Override
	public void store(String id, byte[] data) {
		// TODO Auto-generated method stub
		hm_.put(id, data);
	}

	@Override
	public byte[] get(String id) {
		// TODO Auto-generated method stub
		return hm_.get(id);
	}

}
