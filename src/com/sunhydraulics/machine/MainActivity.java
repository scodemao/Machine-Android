package com.sunhydraulics.machine;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.ActionBar;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.sunhydraulics.machine.fragment.FeedbackFragment_;
import com.sunhydraulics.machine.fragment.IndexFragment_;
import com.sunhydraulics.machine.fragment.SearchFragment_;
import com.sunhydraulics.machine.utils.ToastUtil;
import com.sunhydraulics.machine.view.ChangeColorIconWithText;

/**
 * @author maoweiwei
 * 
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends FragmentActivity implements OnClickListener,
		OnPageChangeListener {

	@ViewById(R.id.id_viewpager)
	public ViewPager mViewPager;
	private SectionsPagerAdapter mSectionsPagerAdapter;

	private SearchFragment_ searchFragment;
	private IndexFragment_ indexFragment;
	private FeedbackFragment_ feedbackFragment;

	private int TIME = 4000;

	private List<ChangeColorIconWithText> mTabIndicators = new ArrayList<ChangeColorIconWithText>();

	@AfterViews
	void init() {

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setIcon(R.drawable.logo_nav);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setOverflowButtonAlways();

		searchFragment = new SearchFragment_();
		indexFragment = new IndexFragment_();
		feedbackFragment = new FeedbackFragment_();

		initView();

		mViewPager.setOnPageChangeListener(this);
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOffscreenPageLimit(3);

		// handler.postAtTime(runnable, TIME);

	}

	private void initView() {

		ChangeColorIconWithText one = (ChangeColorIconWithText) findViewById(R.id.id_indicator_one);
		mTabIndicators.add(one);
		ChangeColorIconWithText two = (ChangeColorIconWithText) findViewById(R.id.id_indicator_two);
		mTabIndicators.add(two);
		ChangeColorIconWithText three = (ChangeColorIconWithText) findViewById(R.id.id_indicator_three);
		mTabIndicators.add(three);

		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);

		one.setIconAlpha(1.0f);

	}

	private void setOverflowButtonAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKey = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKey.setAccessible(true);
			menuKey.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	Handler handler = new Handler();
	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			try {
				handler.postDelayed(this, TIME);
				showInfo("这是测试版，正式版本等最终打包！");
			} catch (Exception e) {
			}
		}
	};

	@UiThread
	void showInfo(String msg) {
		ToastUtil.show(this, msg);
	}

	/**
	 * 设置menu显示icon
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {

		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return super.onMenuOpened(featureId, menu);
	}

	@Override
	public void onClick(View v) {
		clickTab(v);
	}

	/**
	 * 点击Tab按钮
	 * 
	 * @param v
	 */
	private void clickTab(View v) {
		resetOtherTabs();

		switch (v.getId()) {
		case R.id.id_indicator_one:
			mTabIndicators.get(0).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(0, false);
			break;
		case R.id.id_indicator_two:
			mTabIndicators.get(1).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(1, false);
			break;
		case R.id.id_indicator_three:
			mTabIndicators.get(2).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(2, false);
			break;
		}
	}

	/**
	 * 重置其他的TabIndicator的颜色
	 */
	private void resetOtherTabs() {
		for (int i = 0; i < mTabIndicators.size(); i++) {
			mTabIndicators.get(i).setIconAlpha(0);
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		if (positionOffset > 0) {
			ChangeColorIconWithText left = mTabIndicators.get(position);
			ChangeColorIconWithText right = mTabIndicators.get(position + 1);
			left.setIconAlpha(1 - positionOffset);
			right.setIconAlpha(positionOffset);
		}

	}

	@Override
	public void onPageSelected(int position) {
	}

	@Override
	public void onPageScrollStateChanged(int state) {
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {

			Fragment fragment = null;
			switch (position) {
			case 0: {
				fragment = searchFragment;
			}
				break;
			case 1: {
				fragment = indexFragment;
			}
				break;
			case 2: {
				fragment = feedbackFragment;
			}
				break;
			default:
				break;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.tab_search);
			case 1:
				return getString(R.string.tab_index);
			case 2:
				return getString(R.string.tab_feedback);
			}
			return null;
		}
	}

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

	long lastPressBackTime;

}
