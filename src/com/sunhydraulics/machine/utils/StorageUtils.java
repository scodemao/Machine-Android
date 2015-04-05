package com.sunhydraulics.machine.utils;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;

public class StorageUtils {

	public static final File getDefaultCacheDir(Context context) {
		File dataDir = Environment.getExternalStorageDirectory();
		File appCacheDir = new File(dataDir, context.getPackageName());
		if (!appCacheDir.exists()) {
			if (!appCacheDir.mkdirs()) {
				return null;
			}
			try {
				new File(appCacheDir, ".nomedia").createNewFile();
			} catch (IOException e) {
			}
		}

		return appCacheDir;
	}
}
