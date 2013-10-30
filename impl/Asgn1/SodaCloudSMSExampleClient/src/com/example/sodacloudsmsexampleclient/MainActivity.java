package com.example.sodacloudsmsexampleclient;

import org.magnum.soda.example.sms.SMSManager;
import org.magnum.soda.example.sms.SMSManagerImpl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button connect_;
	private Button scan_;
	private EditText objRef_;
	private EditText server_;

	/**
	 * Asgn Step 5: Instantiate an instance of your Module implementation and
	 * assign it to the configuration variable. Note, your module instance
	 * should be configured to map:
	 * 
	 * SMSManager --> SMSManagerImpl ObjRefExtractor --> QRCodeObjRefExtractor
	 * 
	 */
	private Module configuration_;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		configuration_ = new ModuleImpl();
		configuration_.setComponent(SMSManager.class, new SMSManagerImpl(this));
		configuration_.setComponent(ObjRefExtractor.class,
				new QRCodeObjRefExtractor());

		connect_ = (Button) findViewById(R.id.connect);
		scan_ = (Button) findViewById(R.id.scan);
		objRef_ = (EditText) findViewById(R.id.objRef);
		server_ = (EditText) findViewById(R.id.server);
	}

	/**
	 * onClick method for the connect button
	 * 
	 * @param v
	 */
	public void clickConnect(View v) {
		String server = server_.getText().toString();
		String oref = objRef_.getText().toString();
		if (server.length() > 0 && oref.length() > 0) {
			connect(server, oref);
		}
	}

	/**
	 * onClick method for the scan button
	 * 
	 * @param v
	 */
	public void clickScan(View v) {
		initiateScan();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/**
	 * Called when connect button is onClick or by onActivityResult method
	 * 
	 * @param server
	 * @param oref
	 */
	public void connect(String server, String oref) {
		Toast.makeText(this, "Connecting to: " + server + "...",
				Toast.LENGTH_LONG).show();
		Intent i = new Intent(this, SMSBridgeActivity.class);
		i.putExtra("ref", server + "|" + oref);
		startActivity(i);
	}

	/**
	 * Called when scan button is onClick
	 */
	public void initiateScan() {
		Intent intent = new Intent("com.google.zxing.client.android.SCAN");
		intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
		startActivityForResult(intent, 0);
	}

	/**
	 * get the result after calling the startActivityForResult(intent,0) by
	 * initiateScan()
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");

				ObjRefExtractor extractor = configuration_
						.getComponent(ObjRefExtractor.class);
				ExternalObjRef ref = extractor.extract(contents);

				connect(ref.getPubSubHost(), ref.getObjRef().getUri());

			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
			}
		}
	}
}
