package org.clear.server.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


@PersistenceCapable
public class Post {

	// Every persistent object needs a primary key.
		@PrimaryKey
		@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
		private Key key;

		@Persistent
		private double lat;
		
		@Persistent
		private double lon;
		
		@Persistent
		private String time;
		
		@Persistent
		private Set<String> userList;
		
		public void save(PersistenceManager pm) {
			// To save an object annotated with @PersistenceCapable,
			// you just need to ask the PersistenceManager to make it
			// persistent (e.g., store it).
			pm.makePersistent(this);
		}
		
		public double getLat(){
			return this.lat;
		}
		
		public double getLon(){
			return this.lon;
		}
		
		public String getTime(){
			return this.time;
		}
		
		public void setTime(String time){
			this.time = time;
		}
		
		public void setLat(String lat){
			this.lat = Double.parseDouble(lat);
		}
		
		public void setLon(String lon){
			this.lon = Double.parseDouble(lon);
		}
		
		public void addUser(String user){
			if(userList==null){
				userList = new HashSet<String>();
			}
			userList.add(user);
		}
		public Set<String> getList(){
			return this.userList;
		}
		
		@JsonIgnore
		public Key getKey() {
			return key;
		}

		@JsonIgnore
		public void setKey(Key key) {
			this.key = key;
		}


		public static Post byKey(String key, PersistenceManager pm) {
			Post post = null;

			try {
				// Fetching a specific object by key
				Key k = KeyFactory.stringToKey(key);
				post = pm.getObjectById(Post.class, k);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return post;
		}

		public static Post byPlaceAndTime(String lat, String lon, String time, PersistenceManager pm) {

			List<Post> results = null;

			// We can declare a query that finds stored objects with
			// member variables that meet a specific criteria. This is
			// roughly equivalent to looping over a list of Java Friends objects
			// and finding the subset where getUser().equals(u). For security
			// purposes, we declare the type of the parameter "u".
			Query query = pm.newQuery("select from " + Post.class.getName()
					+ " where lat==la && lon==lo && time==t");
			query.declareParameters("double la, double lo, String t");

			// When the query is executed, we pass in a value for every parameter.
			// In this case, we are binding a value for "u".
			results = (List<Post>) query.execute(Double.parseDouble(lat),Double.parseDouble(lon),time);

			return (results != null && results.size() == 1) ? results.get(0) : null;
		}
	
}
