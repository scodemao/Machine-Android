package com.sunhydraulics.machine;

import org.androidannotations.annotations.EActivity;

import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * @author maoweiwei
 * 
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends FragmentActivity {

	long lastPressBackTime;

	@Override
	public void onBackPressed() {
		long time = System.currentTimeMillis();
		if (time - lastPressBackTime < 3000l) {
			super.onBackPressed();
		} else {
			Toast.makeText(this, R.string.msg_press_back_to_exit,
					Toast.LENGTH_SHORT).show();
		}
		lastPressBackTime = time;
	}
}
