/*************************************************************************
 * Copyright 2010 Jules White
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/
 * LICENSE-2.0 Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions
 * and limitations under the License.
 **************************************************************************/

package org.vt.smssec;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainPageActivity extends Activity {
	private SMSReceiver receiver = new SMSReceiver();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		Button b = (Button) findViewById(R.id.Button01);
		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainPageActivity.this,
						SecPreferencesActivity.class);
				startActivity(intent);
			}
		});

		IntentFilter filter = new IntentFilter();
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		registerReceiver(receiver, filter);
	}

	@Override
	public void onDestroy() {
		unregisterReceiver(receiver);
	}
}
