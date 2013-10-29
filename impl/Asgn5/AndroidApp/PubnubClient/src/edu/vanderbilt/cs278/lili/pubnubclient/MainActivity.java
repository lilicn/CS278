package edu.vanderbilt.cs278.lili.pubnubclient;

import java.util.Hashtable;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;

/**
 * MainActivity class provides UI to subscribe and show the reaction after get
 * message. Now it can change the background color according to the message
 * received from channel
 * 
 * @author Li
 * 
 */
public class MainActivity extends Activity {
	// tag for Log
	private final String TAG = this.getClass().getSimpleName();
	// channel name to subscribe
	private final static String CHANNEL = "edu_vanderbilt_cs278_lili_pubnub_channel_2013";
	// pubnub object with attributes PUBLISH_KEY(optional),
	// SUBSCRIBE_KEY(Required), SECRET_KEY(optional), CIPHER_KEY(optional),
	// SSL_ON?
	private Pubnub pubnub = new Pubnub("demo", "demo", "", false);
	// layout to be changed later
	private RelativeLayout layout;
	// static handler used in UI thread
	private Handler handler;
	// factory to provide specific executor instance, now it provides only
	// ColorExecutor
	private ExecutorFactory factory = new MyExecutorFactory();
	private final static String COLORKEY = "BACKGROUNDCOLOR";
	private SavedState state = new SavedState();

	/**
	 * Static Handler used in UI thread to handle message received from channel
	 * 
	 * @author Li
	 * 
	 */
	private static class MyHandler extends Handler {
		private RelativeLayout layout;
		private ExecutorFactory factory;
		private String TAG = this.getClass().getSimpleName();
		private SavedState state;

		public MyHandler(RelativeLayout layout, ExecutorFactory factory, SavedState state) {
			this.layout = layout;
			this.factory = factory;
			this.state = state;
		}

		/**
		 * Override method for handleMessage, it will find the specific executor
		 * according to the message type.
		 */
		@Override
		public void handleMessage(Message msg) {
			Msg m = new Msg((String) msg.obj);
			Log.d(TAG, "handleMessage");
			String msgType = m.getType();
			if (factory.isValidType(msgType)){			
				Log.d(TAG, msgType);
				factory.getExecByType(msgType).exector(m.getBody(), layout, state);
			} else {
				Log.e(TAG, "Received message is not valid type");
			}
		}
	}

	/**
	 * Override method for onCreate.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usage);
		this.layout = (RelativeLayout) findViewById(R.id.layout);
		this.handler = new MyHandler(layout, factory,state);
		
		if(savedInstanceState!=null){
			this.layout.setBackgroundColor(savedInstanceState.getInt(COLORKEY));
		}	
	}

	/**
	 * Override method for onCreateOptionMenu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override 
	public void onResume(){
		super.onResume();
		runSubscribe();
	}
	
	@Override
	public void onPause(){
		super.onPause();
		runUnSubscribe();
		handler = null;
	}
	/**
	 * Override method for onOptionsItemSelected
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.subOption:
			runSubscribe();
			return true;
		case R.id.unsubOption:
			runUnSubscribe();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@SuppressLint("NewApi")
	@Override
	protected  void onSaveInstanceState(Bundle savedInstanceState){
		super.onSaveInstanceState(savedInstanceState);
		Log.d(TAG,"saveColor"+state.getColorState());
		savedInstanceState.putInt(COLORKEY, state.getColorState());
	}
	/**
	 * runSubscribe will subscribe our channel via subnub when use choose
	 * subOption in Menu. It will let the handler to handle the received message
	 * whenever there is a callback.
	 */
	public void runSubscribe() {
		Hashtable<String, String> args = new Hashtable<String, String>(1);
		args.put("channel", CHANNEL);
		try {
			pubnub.subscribe(args, new Callback() {
				@Override
				public void connectCallback(String channel, Object message) {
					notifyUser("Successfully SUBSCRIBE!");
				}

				@Override
				public void successCallback(String channel, Object message) {
					if (message instanceof String) {
						handleMsg(message.toString());
					}
				}
			});
		} catch (Exception e) {
			Log.e(TAG, e.toString() + ": " + e.getMessage());
		}
	}

	/**
	 * runUnSubscribe will unsubscribe our channel when use chooses the
	 * unsubOption in the Menu.
	 */
	public void runUnSubscribe() {
		pubnub.unsubscribe(CHANNEL);
		layout.setBackgroundColor(Color.WHITE);
		notifyUser("Successfully UNSUBSCRIBE");
	}

	/**
	 * handle the message from pubnub and let the handler in the UI thread to
	 * deal with it.
	 * 
	 * @param strMsg
	 */
	public void handleMsg(String strMsg) {
		notifyUser("RECEIVE MSG: " + strMsg);
		Message msg = new Message();
		msg.obj = strMsg;
		handler.sendMessage(msg);
	}

	/**
	 * notifyUser will show a message as a toast in the UI
	 * 
	 * @param message
	 */
	private void notifyUser(String message) {
		final String obj = (String) message;
		this.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(getApplicationContext(), obj, Toast.LENGTH_LONG)
						.show();
				Log.i("Received msg : ", obj.toString());
			}
		});
	}
}
