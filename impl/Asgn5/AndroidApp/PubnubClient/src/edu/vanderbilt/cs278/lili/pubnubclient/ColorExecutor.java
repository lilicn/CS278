package edu.vanderbilt.cs278.lili.pubnubclient;

import android.widget.RelativeLayout;

/**
 * ColorExecutor is an implement of Executor, and it can change the background
 * color according to the message body.
 * 
 * @author Li
 * 
 */

public class ColorExecutor implements Executor<Integer> {

	private String body;

	/**
	 * check if the message body is number
	 */
	@Override
	public boolean isValid() {
		return body.matches("-?\\d+");
	}
	
	/**
	 * set the background color of layout to the integer - body
	 */
	@Override
	public void exector(String body, RelativeLayout layout, SavedState state) {
		this.body = body;
		if (isValid())
			layout.setBackgroundColor(getValue());
		state.setColorState(getValue());
	}

	@Override
	public Integer getValue() {
		if (isValid()) {
			return Integer.parseInt(body);
		}
		return null;
	}

}
