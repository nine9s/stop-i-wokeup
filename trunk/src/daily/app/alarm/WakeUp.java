package daily.app.alarm;

import java.util.Date;
import java.util.Random;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.TimeUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class WakeUp extends Activity {
	
	public TextView question;
	public EditText answer;
	Toast mToast;
	int questionVar1,questionVar2;
	
	int hourToWakeUp=5;
	int minuteTowakeUp=0;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	  
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button button = (Button)findViewById(R.id.button_normal);
        button.setOnClickListener(mStartRepeatingListener);
        Button ansButton = (Button)findViewById(R.id.answerButton);
        ansButton.setOnClickListener(validateAnswer);
        question = (TextView) findViewById(R.id.question);
        answer = (EditText) findViewById(R.id.answer);
        getQuestion();
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setCurrentHour(5);
        timePicker.setCurrentMinute(0);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
        	
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                updateDisplay(hourOfDay, minute);
            }
        });
    }
    
public void updateDisplay(int hourOfDay, int minute){
	hourToWakeUp=hourOfDay;
	minuteTowakeUp=minute;
}
public void getQuestion()
{
	Random r = new Random(System.currentTimeMillis());
	String fnl="";
	questionVar1=Math.abs(r.nextInt(100));
	questionVar2=Math.abs(r.nextInt(100));
	fnl=questionVar1+" X "+questionVar2;
	question.setText(fnl);
}

private OnClickListener validateAnswer = new OnClickListener() {
    public void onClick(View v) {
    	if((answer.getText().toString()).equalsIgnoreCase((questionVar1*questionVar2)+""))
    		stopAlarm();
    		else
    		{	mToast = Toast.makeText(WakeUp.this, "try again",
                        Toast.LENGTH_LONG);
                mToast.show();
    		}
    }
};
private OnClickListener mStartRepeatingListener = new OnClickListener() {
        public void onClick(View v) {
            // When the alarm goes off, we want to broadcast an Intent to our
            // BroadcastReceiver.  Here we make an Intent with an explicit class
            // name to have our own receiver (which has been published in
            // AndroidManifest.xml) instantiated and called, and then create an
            // IntentSender to have the intent executed as a broadcast.
            // Note that unlike above, this IntentSender is configured to
            // allow itself to be sent multiple times.
            Intent intent = new Intent(WakeUp.this, RepeatingAlarm.class);
            PendingIntent sender = PendingIntent.getBroadcast(WakeUp.this,
                    0, intent, 0);
            
            // We want the alarm to go off 30 seconds from now.
            long firstTime = SystemClock.elapsedRealtime();
            firstTime += hourToWakeUp*60*60*1000+minuteTowakeUp*60*1000;
            //firstTime+=15*1000;         	 

            // Schedule the alarm!
            AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
            am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            firstTime, 15*60*1000, sender);

            // Tell the user about what we did.
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(WakeUp.this, "alarm in "+hourToWakeUp+" hrs"+minuteTowakeUp+" mins",
                    Toast.LENGTH_LONG);
                mToast.show();
        }
    };
public void stopAlarm()
{
	 Intent intent = new Intent(WakeUp.this, RepeatingAlarm.class);
     PendingIntent sender = PendingIntent.getBroadcast(WakeUp.this,
             0, intent, 0);
     
     // And cancel the alarm.
     AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
     am.cancel(sender);

     // Tell the user about what we did.
     if (mToast != null) {
         mToast.cancel();
     }
     mToast = Toast.makeText(WakeUp.this, "stopping alarm",
             Toast.LENGTH_LONG);
     mToast.show();

}

}