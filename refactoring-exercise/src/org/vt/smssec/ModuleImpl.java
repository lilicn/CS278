package org.vt.smssec;

import java.util.HashMap;
import java.util.Map;

public class ModuleImpl<T> implements Module<T> {
	private Map<String, Class<? extends T>> hm_ = new HashMap<String, Class<? extends T>>();

	@Override
	public Class<? extends T> getComponent(String str) {
		return this.hm_.get(str);
	}

	@Override
	public void setComponent(String str, Class<? extends T> type) {
		this.hm_.put(str, (Class<? extends T>) type);
	}

	@Override
	public T getInstance(String str) throws InstantiationException,
			IllegalAccessException {
		return getComponent(str) == null ? null : getComponent(str)
				.newInstance();
	}

}
