package edu.vanderbilt.cs278.lili.pubnubclient.test;

import java.util.Hashtable;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;
import android.widget.RelativeLayout;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubException;

import edu.vanderbilt.cs278.lili.pubnubclient.MainActivity;

public class MainActivityTest extends ActivityUnitTestCase<MainActivity> {
	private final static String CHANNEL = "edu_vanderbilt_cs278_lili_pubnub_channel_2013";
	private MainActivity activity_;
	private RelativeLayout layout_;
	private final static String TAG = "MainActivityTest";
	private boolean isReceived = false;
	public MainActivityTest() {
		super(MainActivity.class);	
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		Intent intent = new Intent(getInstrumentation().getTargetContext(),
		        MainActivity.class);
		    startActivity(intent, null, null);
		this.activity_ = getActivity();
		this.layout_ = (RelativeLayout) this.activity_.findViewById(edu.vanderbilt.cs278.lili.pubnubclient.R.id.layout);
	}

	@SuppressLint("NewApi")
	@SmallTest
	public void testSub() throws InterruptedException {
		Log.d(TAG,"ready to publish");
		layout_ = (RelativeLayout) activity_.findViewById(edu.vanderbilt.cs278.lili.pubnubclient.R.id.layout);
		ColorDrawable cd = (ColorDrawable) layout_.getBackground();
		Log.d(TAG,"Before publish: "+cd.getColor()+"");
		// publish a change color request
		try {
			subscribe();
		} catch (PubnubException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		publish("color::-10897");
		while(!isReceived){
			Thread.sleep(20000000);
			Log.d(TAG,"wait for receive ... ");
		}
		
		layout_ = (RelativeLayout) activity_.findViewById(edu.vanderbilt.cs278.lili.pubnubclient.R.id.layout);
		cd = (ColorDrawable) layout_.getBackground();
		Log.d(TAG,"After publish: "+cd.getColor()+"");
		assertTrue("check background color has been changed",cd.getColor()==-10897);
	}
	
	/**
	 * Create another subscribe to receive msg
	 * @throws PubnubException
	 */
	public void subscribe() throws PubnubException{
		Pubnub pubnub = new Pubnub("demo", "demo", "", false);
		Hashtable<String, String> args = new Hashtable<String, String>(1);
		args.put("channel", CHANNEL);
		pubnub.subscribe(args, new Callback() {

			@Override
			public void successCallback(String channel, Object message) {
				if (message instanceof String) {
					if(message.equals("color::-10897"))
						isReceived = true;
				}
			}
		});
	}
	
	public void publish(final String msg) {
		Pubnub pubnub = new Pubnub("demo", "demo", "", false);
		Log.d(TAG,"make pubnub "+(pubnub!=null));
		Hashtable<String, String> args = new Hashtable<String, String>(2);
		args.put("message", msg);
		args.put("channel", CHANNEL);
		pubnub.publish(args);
	}
	
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}
}
