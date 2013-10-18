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

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;

public class TalkCommand implements OnInitListener, Command {

	/**
	 * Example: cmd:say:
	 * "Your name has been sent to the police, you will rot in jail"
	 */

	private Context context_;
	private TextToSpeech textToSpeech_;
	private String message_;
	private final static String TAG = "TalkCommand";

	public TalkCommand() {
	}

	public TalkCommand(Context ctx, String msg) {
		context_ = ctx;
		message_ = msg;
		textToSpeech_ = new TextToSpeech(ctx, this);
	}

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			talk();
		} else {
			Log.e(TAG, "TTS Initilization Failed!");
		}
	}

	public void talk() {
		HashMap<String, String> myHashAlarm = new HashMap<String, String>();
		myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_STREAM,
				String.valueOf(AudioManager.STREAM_ALARM));
		textToSpeech_.speak(message_, TextToSpeech.QUEUE_ADD, myHashAlarm);

		try {
			Thread.currentThread().sleep(3000);
		} catch (Exception e) {
			Log.e(TAG, e.toString() + ": " + e.getMessage());
		}
	}

	@Override
	public void execute(Context cxt, Object... objs) {
		context_ = cxt;
		String cmd = (String) objs[0];
		message_ = Util.getMSG(cmd);
		textToSpeech_ = new TextToSpeech(context_, this);
		talk();
	}

}
