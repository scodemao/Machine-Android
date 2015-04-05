package com.sunhydraulics.machine.app;

import org.androidannotations.annotations.EApplication;

import com.lidroid.xutils.DbUtils;
import com.sunhydraulics.machine.utils.StorageUtils;

import android.app.Application;

/**
 * @author maoweiwei
 * 
 */

@EApplication
public class AppApplication extends Application {

	private DbUtils myDbUtils;

	@Override
	public void onCreate() {
		super.onCreate();

		StorageUtils.getDefaultCacheDir(this);

		myDbUtils = DbUtils.create(this);

	}

	public DbUtils getMyDbUtils() {
		return myDbUtils;
	}

}