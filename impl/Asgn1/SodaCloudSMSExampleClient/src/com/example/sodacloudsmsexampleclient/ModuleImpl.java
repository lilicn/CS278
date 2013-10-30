package com.example.sodacloudsmsexampleclient;

import java.util.HashMap;
import java.util.Map;

import org.magnum.soda.example.sms.SMSManager;
import org.magnum.soda.example.sms.SMSManagerImpl;

import android.content.Context;

/**
 * Asgn Step 4: Create an implementation of interface Module that creates a
 * mapping of classes to specific object instances. The goal of this module
 * implementation will be to decouple the use of the "components" from their
 * creation.
 * 
 * 
 */

public class ModuleImpl implements Module {

	private Map<Class<?>, Object> hm_ = new HashMap<Class<?>, Object>();

	/**
	 * 
	 * This method returns the component that is bound to a given type.
	 * 
	 * @param type
	 *            - type type of component to retrieve
	 * @return
	 */
	@Override
	public <T> T getComponent(Class<T> type) {
		// TODO Auto-generated method stub
		return (T) this.hm_.get(type);
	}

	/**
	 * 
	 * Bind a component to a type.
	 * 
	 * @param <T>
	 * 
	 * @param type
	 *            - the type to bind the component to
	 * @param component
	 *            - the object instance to associate with the type key
	 */
	@Override
	public <T> void setComponent(Class<T> type, T component) {
		// TODO Auto-generated method stub
		this.hm_.put(type, component);
	}

}
