package com.example.sodacloudsmsexampleclient;

import org.magnum.soda.proxy.ObjRef;

/**
 * Asgn Step 6: Create an implementation of interface ExternalObjRef.
 * 
 */

public class ExternalObjRefImpl implements ExternalObjRef {

	private ObjRef objRef_;
	private String pubsubHost_;

	/**
	 * set ObjRef
	 * 
	 * @param oref
	 */
	@Override
	public void setObjRef(ObjRef oref) {
		this.objRef_ = oref;
	}

	/**
	 * set pubsubHost
	 * 
	 * @param pubsubHost
	 */
	@Override
	public void setPubSubHost(String pubsubHost) {
		this.pubsubHost_ = pubsubHost;
	}

	/**
	 * get objRef
	 */
	@Override
	public ObjRef getObjRef() {
		// TODO Auto-generated method stub
		return this.objRef_;
	}

	/**
	 * get pubsubHost
	 */
	@Override
	public String getPubSubHost() {
		// TODO Auto-generated method stub
		return this.pubsubHost_;
	}

	/**
	 * The toString() implementation should return a String in the following
	 * format:
	 * 
	 * getPubSubHost()+"|"+getObjRef().getUri()
	 * 
	 * @return
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getPubSubHost());
		sb.append("|");
		sb.append(this.getObjRef().getUri());
		return sb.toString();
	}

}
