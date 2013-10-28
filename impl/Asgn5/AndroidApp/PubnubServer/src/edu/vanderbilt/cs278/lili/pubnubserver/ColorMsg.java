package edu.vanderbilt.cs278.lili.pubnubserver;

/**
 * ColorMsg class stores color instance via red, green and blue values.
 * 
 * @author Li
 */
public class ColorMsg extends Msg<Integer> {
	private int red;
	private int green;
	private int blue;

	/**
	 * only receive integers
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 */
	public ColorMsg(int red, int green, int blue) {
		this.red = toFinalValue(red);
		this.green = toFinalValue(green);
		this.blue = toFinalValue(blue);
	}

	/**
	 * assign it if color value is from 0 - 255, otherwise assign it to -1
	 * @param val
	 * @return
	 */
	private int toFinalValue(int val) {

		if (val <= 255 && val >= 0) {
			return val;
		}

		return -1;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "color";
	}

	/**
	 * 0-7 is blue 8-15 is green 16-23 is red
	 */
	@Override
	public Integer getValue() {
		// TODO Auto-generated method stub
		return -1 * ((red << 16) + (green << 8) + blue);
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return red != -1 && green != -1 && blue != -1;
	}
}
