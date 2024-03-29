/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package daily.app.alarm;

// Need the following import to get access to the app resources, since this
// class is in a sub-package.

import android.media.AudioManager;
import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.MediaStore.Audio;
import android.widget.Toast;

/**
 * This is an example of implement an {@link BroadcastReceiver} for an alarm that
 * should occur once.
 */
public class RepeatingAlarm extends BroadcastReceiver
{
	public MediaPlayer mMediaPlayer;
	@Override
    public void onReceive(Context context, Intent intent)
    {
		mMediaPlayer = MediaPlayer.create(context,R.raw.test_cbr);
		
		AudioManager a = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		a.setStreamVolume(AudioManager.STREAM_MUSIC, a.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
		
		//mMediaPlayer.setVolume((float)1.0,(float)1.0);
		mMediaPlayer.start();
		Toast.makeText(context, "alarm", Toast.LENGTH_SHORT).show();
    }
}

