package edu.vanderbilt.cs278.lili.pubnubserver;

public class Util {
	/**
	 * Check users input to ensure empty input
	 * @param strs
	 * @return
	 */
	public static boolean checkNullInput(String... strs) {
		for (String s : strs) {
			if (s == null || s == "")
				return false;
		}
		return strs.length>0;
	}
}
