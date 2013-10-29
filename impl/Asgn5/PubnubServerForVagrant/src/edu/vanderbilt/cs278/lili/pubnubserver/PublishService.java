package edu.vanderbilt.cs278.lili.pubnubserver;

import java.util.Hashtable;
import java.util.Scanner;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;

public class PublishService {
	private final static String CHANNEL = "edu_vanderbilt_cs278_lili_pubnub_channel_2013";
	private Pubnub pubnub = new Pubnub("demo", "demo", "", false);
	public void publish(final String msg) {		
		Hashtable<String, String> args = new Hashtable<String, String>(2);
		args.put("message", msg);
		args.put("channel", CHANNEL);
		pubnub.publish(args, new Callback() {
			@Override
			public void successCallback(String channel, Object message) {				
				System.out.println("Successfully PUBLISH : " + msg);
			}

			@Override
			public void errorCallback(String channel, PubnubError error) {
				System.out.println("PUBLISH : " + error);
			}
		});
	}

	public void welcome(){
		System.out.println("Welcome to the pubnub server.");
		System.out.println("Have you open your client already? ");
		System.out.println("Now you can publish your int color, and see what the change is in client.");
		System.out.println("(Input quit if you want to quit)");
	}
	
	public void defaultPublish(){
		System.out.println("Now send a default msg to your clients.");
		ColorMsg msg = new ColorMsg(255,0,255);
		publish(msg.getMsg());
	}
	
	public void getUserInput(){
		boolean boo = true;
		Scanner reader = new Scanner(System.in);
		while(boo){
			System.out.println("Now Please input three int (0-255) to represant RED, GREEN and Blue color:");	
			System.out.println("(eg:13,45,41)");
			System.out.println(">>");
			String str = reader.next();
			if(str.matches("\\d+,\\d+,\\d+")){
				String[] ss = str.split(",");
				int red = Integer.parseInt(ss[0]);
				int green = Integer.parseInt(ss[1]);
				int blue = Integer.parseInt(ss[2]);
				ColorMsg msg = new ColorMsg(red,green,blue);
				if(msg.isValid()){
					publish(msg.getMsg());
				}else System.out.println("Not valid input");
			}else if(str.equalsIgnoreCase("quit")){
				boo = false;
				System.out.println("Bye");			
			}else System.out.println("The input format is not right");
		}	
		reader.close();
		pubnub.shutdown();	
	}
	public static void main(String[] args) {
		PublishService pub = new PublishService();
		pub.welcome();
		//pub.defaultPublish();
		pub.getUserInput();
	}

}
