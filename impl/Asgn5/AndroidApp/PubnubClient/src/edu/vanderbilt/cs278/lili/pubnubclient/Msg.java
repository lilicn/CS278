package edu.vanderbilt.cs278.lili.pubnubclient;

/**
 * Msg class is used to store the message and parse the message type and body.
 * 
 * @author Li
 * 
 */
public class Msg {
	private String msg;

	public Msg(String msg) {
		this.msg = msg;
	}

	public String getType() {
		return msg.split("::")[0];
	}

	public String getBody() {
		return msg.split("::")[1];
	}
}
