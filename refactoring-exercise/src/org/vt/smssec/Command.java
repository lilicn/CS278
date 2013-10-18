package org.vt.smssec;

import android.content.Context;


public interface Command {

	/**
	 * 
	 * @param context the required para for all Command classes
	 * @param objs not required and may be changable
	 */
	public abstract void execute(Context context, Object... objs);
}
