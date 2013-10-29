package edu.vanderbilt.cs278.lili.pubnubserver;

import java.util.Hashtable;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;

/**
 * MainActivity class provides UI to publish message via pubnub.
 * 
 * @author Li
 * 
 */
public class MainActivity extends Activity {

	private final String TAG = this.getClass().getSimpleName();
	private final static String CHANNEL = "edu_vanderbilt_cs278_lili_pubnub_channel_2013";
	private EditText red_;
	private EditText blue_;
	private EditText green_;
	private RelativeLayout layout;
	private Pubnub pubnub = new Pubnub("demo", "demo", "", false);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usage);
		red_ = (EditText) findViewById(R.id.red);
		blue_ = (EditText) findViewById(R.id.blue);
		green_ = (EditText) findViewById(R.id.green);
		layout = (RelativeLayout) findViewById(R.id.layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/**
	 * publishColor method will be called when use clicks the publish button
	 * after set the color values.
	 * 
	 * @param view
	 */
	public void publishColor(View view) {
		if (Util.checkNullInput(red_.getText().toString(), green_.getText()
				.toString(), blue_.getText().toString())) {
			Msg<Integer> colorMsg = new ColorMsg(Integer.parseInt(red_
					.getText().toString()), Integer.parseInt(green_.getText()
					.toString()), Integer.parseInt(blue_.getText().toString()));
			if (colorMsg.isValid()) {
				int value = colorMsg.getValue();
				// set background color
				layout.setBackgroundColor(value);
				// publish
				publish(colorMsg.getMsg());
			} else {
				notifyUser("Color value must be 0 - 255!");
			}
		} else {
			notifyUser("Please input all Color values!");
		}
	}

	/**
	 * publish the message via pubnub.
	 * 
	 * @param msg
	 */
	public void publish(final String msg) {
		Hashtable<String, String> args = new Hashtable<String, String>(2);
		args.put("message", msg);
		args.put("channel", CHANNEL);
		pubnub.publish(args, new Callback() {
			@Override
			public void successCallback(String channel, Object message) {
				notifyUser("PUBLISH : " + msg);
			}

			@Override
			public void errorCallback(String channel, PubnubError error) {
				notifyUser("PUBLISH : " + error);
				Log.e(TAG, error.toString());
			}
		});
	}

	/**
	 * notifyUser with message
	 * 
	 * @param message
	 */
	private void notifyUser(Object message) {
		try {
			if (message instanceof String) {
				final String obj = (String) message;
				this.runOnUiThread(new Runnable() {
					public void run() {
						Toast.makeText(getApplicationContext(), obj,
								Toast.LENGTH_LONG).show();
						Log.i("Received msg : ", obj.toString());
					}
				});

			}
		} catch (Exception e) {
			Log.e(TAG, e.toString() + ": " + e.getMessage());
		}
	}
}
