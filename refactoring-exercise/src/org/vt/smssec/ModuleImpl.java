package org.vt.smssec;

import java.util.HashMap;
import java.util.Map;

public class ModuleImpl<T> implements Module {
	private Map<String, Class<?>> hm_ = new HashMap<String, Class<?>>();

	@Override
	public Class<T> getComponent(String str) {
		return (Class<T>) this.hm_.get(str);
	}

	@Override
	public <T> void setComponent(String str, Class<T> type) {
		this.hm_.put(str, type);
	}

	@Override
	public <T> T getInstance(String str) throws InstantiationException,
			IllegalAccessException {
		return getComponent(str) == null ? null : (T) getComponent(str)
				.newInstance();
	}

}
