package edu.vanderbilt.cs278.lili.pubnubclient;

import java.util.HashMap;

/**
 * ExecutorFactory can be used to set and get the Executor according to the
 * message type
 * 
 * @author Li
 * 
 */
public class ExecutorFactory {
	private HashMap<String, Executor<?>> hm = new HashMap<String, Executor<?>>();

	public Executor<?> getExecByType(String msgType) {
		return hm.get(msgType);
	}

	public void setExec(String msgType, Executor<?> exe) {
		hm.put(msgType, exe);
	}

	public boolean isValidType(String msgType) {
		return hm.containsKey(msgType);
	}
}
