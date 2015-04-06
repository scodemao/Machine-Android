package com.sunhydraulics.machine.app;

import org.androidannotations.annotations.EApplication;

import android.app.Application;
import android.content.Context;

import com.lidroid.xutils.DbUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.sunhydraulics.machine.utils.ImageLoaderUtils;
import com.sunhydraulics.machine.utils.StorageUtils;

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

		initImageLoader(this);
	}

	public DbUtils getMyDbUtils() {
		return myDbUtils;
	}

	public void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.denyCacheImageMultipleSizesInMemory()
				.threadPoolSize(3)
				.memoryCache(new WeakMemoryCache())
				.diskCache(
						new UnlimitedDiscCache(StorageUtils
								.getDefaultCacheDir(this), StorageUtils
								.getDefaultCacheDir(this), ImageLoaderUtils
								.getDefaultFileNameGenerator()))
				.diskCacheFileNameGenerator(
						ImageLoaderUtils.getDefaultFileNameGenerator())
				.writeDebugLogs()
				.tasksProcessingOrder(QueueProcessingType.FIFO).build();
		ImageLoader.getInstance().init(config);
	}

}