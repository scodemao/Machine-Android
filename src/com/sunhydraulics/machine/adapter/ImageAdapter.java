package com.sunhydraulics.machine.adapter;

import java.util.ArrayList;
import java.util.Collection;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sunhydraulics.machine.fragment.WeekSingleImageFragment_;
import com.sunhydraulics.machine.model.Photo;

/**
 * @author maoweiwei
 * 
 */
public class ImageAdapter extends FragmentStatePagerAdapter {
	boolean mOnlyDownload;
	ArrayList<Photo> mPhotos = new ArrayList<Photo>();

	public ImageAdapter(FragmentManager fm, Collection<Photo> urls,
			boolean onlyDownload) {
		super(fm);
		mPhotos.addAll(urls);
		mOnlyDownload = onlyDownload;
	}

	@Override
	public Fragment getItem(int position) {
		Photo item = mPhotos.get(position);
		Fragment fragment = null;
		fragment = WeekSingleImageFragment_.builder().photo(item)
				.onlyDownload(mOnlyDownload).build();
		return fragment;
	}

	@Override
	public int getCount() {
		return mPhotos.size();
	}

	public Photo getItemContent(int position) {
		if (null != mPhotos && position >= 0 && position < mPhotos.size()) {
			return mPhotos.get(position);
		}
		return null;
	}
}
