package com.sunhydraulics.machine;

import android.app.ActionBar;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.sunhydraulics.machine.utils.ActivityUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * @author maoweiwei
 * 
 */
public class BaseBackActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		if (ActivityUtils.hasSmartBar()) {
			getWindow().setUiOptions(
					ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW);
		}
		super.onCreate(arg0);

		ActionBar ab = getActionBar();
		if (null != ab) {
			ab.setDisplayHomeAsUpEnabled(true);
			ab.setDisplayUseLogoEnabled(true);
			if (ActivityUtils.hasSmartBar()) {
				ActivityUtils.setBackIcon(ab,
						getResources().getDrawable(R.drawable.back_meizu));
			}
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	protected void onStop() {
		super.onStop();
	}

	boolean mDestoryed = false;

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mDestoryed = true;
	}
}
