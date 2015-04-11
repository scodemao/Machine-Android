package com.sunhydraulics.machine.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.content.Context;

/**
 * @author maoweiwei
 * 
 */
public class ZipManager {

	private static ZipManager _instance;
	private Context mContext;

	public static ZipManager getInstance() {
		if (_instance == null) {
			_instance = new ZipManager();
		}
		return _instance;
	}

	public synchronized boolean loadZip(Context context) {
		if (null == context) {
			return true;
		}
		mContext = context;

		boolean hasError = true;

		try {
			unZip(mContext, "pics.zip",
					StorageUtils.getDefaultCacheDir(mContext).toString(), false);
			hasError = false;
		} catch (IOException e) {
			hasError = true;
		}
		return hasError;
	}

	public static void unZip(Context context, String assetName,
			String outputDirectory, boolean isReWrite) throws IOException {
		// 创建解压目标目录
		File file = new File(outputDirectory);
		// 如果目标目录不存在，则创建
		if (!file.exists()) {
			file.mkdirs();
		}
		// 打开压缩文件
		InputStream inputStream = context.getAssets().open(assetName);
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);
		// 读取一个进入点
		ZipEntry zipEntry = zipInputStream.getNextEntry();
		// 使用1Mbuffer
		byte[] buffer = new byte[1024 * 1024];
		// 解压时字节计数
		int count = 0;
		// 如果进入点为空说明已经遍历完所有压缩包中文件和目录
		while (zipEntry != null) {
			// 如果是文件
			file = new File(outputDirectory + File.separator
					+ zipEntry.getName());
			// 文件需要覆盖或者文件不存在，则解压文件
			if (isReWrite || !file.exists()) {
				file.createNewFile();
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				while ((count = zipInputStream.read(buffer)) > 0) {
					fileOutputStream.write(buffer, 0, count);
				}
				fileOutputStream.close();
			}

			// 定位到下一个文件入口
			zipEntry = zipInputStream.getNextEntry();
		}
		zipInputStream.close();
	}

	// 将source拷贝到dest
	public boolean moveDataBase(InputStream srcDataBase, String dest)
			throws IOException {
		if (null == srcDataBase || null == dest) {
			return false;
		}

		File dir = StorageUtils.getDefaultCacheDir(mContext);
		File db = new File(dir, dest);

		if (db.exists()) {
			return false;
		}

		FileOutputStream os = null;
		try {
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[4096];
			int count = 0;

			while ((count = srcDataBase.read(buffer)) > 0) {
				os.write(buffer, 0, count);
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} finally {
			if (os != null)
				os.close();
		}

		return true;
	}
}
