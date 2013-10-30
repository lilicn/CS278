package org.cs27x.di;

import java.util.HashMap;
import java.util.List;

import org.cs27x.di.model.Photo;

public class PhotoStorageImpl implements PhotoStorage {

	private DataStore dataStore_;
	private HashMap<String, Photo> hm_ = new HashMap<String, Photo>();
	
	@Override
	public DataStore getDataStore() {
		// TODO Auto-generated method stub
		return dataStore_;
	}

	@Override
	public void setDataStore(DataStore ds) {
		// TODO Auto-generated method stub
		dataStore_ = ds;
	}

	@Override
	public Photo storePhoto(byte[] imgdata) {
		// TODO Auto-generated method stub
		String id = System.currentTimeMillis()+"";
		Photo p = new Photo(id,imgdata);
		hm_.put(id, p);
		return p;
	}

	@Override
	public Photo getPhotoByID(String id) {
		// TODO Auto-generated method stub
		return hm_.get(id);
	}

	@Override
	public List<Photo> listPhotos() {
		// TODO Auto-generated method stub
		return (List<Photo>) hm_.values();
	}

}
