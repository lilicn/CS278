package org.vt.smssec;

public class Util {

	public static String getMSG(String cmd){
		int start = cmd.indexOf("\"");
		int end = cmd.indexOf("\"", start + 1);
		return cmd.substring(start + 1, end);
	}
}
