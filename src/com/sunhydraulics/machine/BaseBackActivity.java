package com.sunhydraulics.machine;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

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

	private Drawable.Callback mDrawableCallback = new Drawable.Callback() {
		@Override
		public void invalidateDrawable(Drawable who) {
			mActionBar.setBackgroundDrawable(who);
		}

		@Override
		public void scheduleDrawable(Drawable who, Runnable what, long when) {
		}

		@Override
		public void unscheduleDrawable(Drawable who, Runnable what) {
		}
	};

	@Override
	protected void onCreate(Bundle arg0) {

		if (ActivityUtils.hasSmartBar()) {
			getWindow().setUiOptions(
					ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW);
		}
		super.onCreate(arg0);

		changeStatusBarColor();
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

	public void initActionBar() {
		mActionBarBackgroundDrawable = getResources().getDrawable(
				R.drawable.ab_background);

		mActionBar.setBackgroundDrawable(mActionBarBackgroundDrawable);
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
			mActionBarBackgroundDrawable.setCallback(mDrawableCallback);
		}
		mActionBarBackgroundDrawable.setAlpha(0);
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

	@SuppressLint("NewApi")
	private void changeStatusBarColor() {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(getResources().getColor(
					R.color.actionbar_bg_color));
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
