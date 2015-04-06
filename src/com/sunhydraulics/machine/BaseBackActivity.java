package com.sunhydraulics.machine;

import android.app.ActionBar;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.MenuItem;

import com.sunhydraulics.machine.utils.ActivityUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * @author maoweiwei
 * 
 */
public class BaseBackActivity extends FragmentActivity {
	public Drawable mActionBarBackgroundDrawable;
	public ActionBar mActionBar;
	public int mActionBarHeight;
	protected TypedValue mTypedValue = new TypedValue();

	@Override
	protected void onCreate(Bundle arg0) {

		if (ActivityUtils.hasSmartBar()) {
			getWindow().setUiOptions(
					ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW);
		}
		super.onCreate(arg0);

		mActionBar = getActionBar();
		if (null != mActionBar) {

			mActionBar.setIcon(R.drawable.ic_launcher);
			mActionBar.setDisplayHomeAsUpEnabled(true);
			mActionBar.setDisplayUseLogoEnabled(true);
			if (ActivityUtils.hasSmartBar()) {
				ActivityUtils.setBackIcon(mActionBar, getResources()
						.getDrawable(R.drawable.back_default));
			}
		}
	}


	/**
	 * 
	 * 
	 * @return
	 */
	public int getActionBarHeight() {
		if (mActionBarHeight != 0) {
			return mActionBarHeight;
		}
		getTheme().resolveAttribute(android.R.attr.actionBarSize, mTypedValue,
				true);
		mActionBarHeight = TypedValue.complexToDimensionPixelSize(
				mTypedValue.data, getResources().getDisplayMetrics());
		return mActionBarHeight;
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
