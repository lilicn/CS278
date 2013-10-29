package org.cs27x.di;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TagManagerImpl implements TagManager {

	private HashMap<String,Set<String>> hm_ = new HashMap<String,Set<String>>();
	
	@Override
	public void addTag(String photoId, String tag) {
		// TODO Auto-generated method stub
		Set<String> set;
		if(hm_.containsKey(tag)){
			set = hm_.get(tag);
		}
		else{
			set = new HashSet<String>();
		}
		set.add(photoId);
		hm_.put(tag, set);

	}

	@Override
	public void removeTag(String photoId, String tag) {
		// TODO Auto-generated method stub
		if(hm_.containsKey(tag)){
			Set<String> set = hm_.get(tag);
			if(set.contains(photoId)){
				set.remove(photoId);
			}
			
			if(set.isEmpty()){
				hm_.remove(tag);
			}
		}
	}

	@Override
	public Set<String> listTags() {
		// TODO Auto-generated method stub
		return hm_.keySet();
	}

	@Override
	public List<String> listPhotosWithTag(String tag) {
		// TODO Auto-generated method stub
		//return (List<String>) hm_.get(tag);
		return new LinkedList<String>(hm_.get(tag));
	}

}
