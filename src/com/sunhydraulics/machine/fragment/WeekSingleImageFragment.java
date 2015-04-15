package com.sunhydraulics.machine.fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;
import uk.co.senab.photoview.PhotoViewAttacher.OnViewTapListener;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.sunhydraulics.machine.R;
import com.sunhydraulics.machine.model.Photo;
import com.sunhydraulics.machine.utils.ImageLoaderUtils;
import com.sunhydraulics.machine.view.ProgressablePhotoView;

/**
 * 
 * @author maoweiwei
 * 
 */
@EFragment(R.layout.fragment_single_image)
public class WeekSingleImageFragment extends Fragment {

	@ViewById(R.id.single_image)
	ProgressablePhotoView photoView;

	@ViewById(R.id.single_net_error)
	View netError;

	@FragmentArg("only_download")
	boolean onlyDownload = false;

	@FragmentArg("photo")
	Photo photo;

	@AfterViews
	void init() {
		photoView.getImageView().setMaximumScale(10.f);

		photoView.getImageView().setOnViewTapListener(new OnViewTapListener() {

			@Override
			public void onViewTap(View arg0, float arg1, float arg2) {
				getActivity().finish();
			}
		});
		photoView.getImageView().setOnPhotoTapListener(
				new OnPhotoTapListener() {

					@Override
					public void onPhotoTap(View arg0, float arg1, float arg2) {
						getActivity().finish();
					}
				});

		ImageLoaderUtils.displayPic(photo.getImageUrl(), photoView,
				new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String arg0, View arg1) {
						photoView.getProgressBar().setVisibility(View.VISIBLE);
						netError.setVisibility(View.GONE);
					}

					@Override
					public void onLoadingFailed(String arg0, View arg1,
							FailReason arg2) {
						netError.setVisibility(View.VISIBLE);
						photoView.getProgressBar().setVisibility(View.GONE);
					}

					@Override
					public void onLoadingComplete(String arg0, View arg1,
							Bitmap arg2) {
						if (null != arg2) {
							// download.setVisibility(View.VISIBLE);
							netError.setVisibility(View.GONE);
						} else {
							netError.setVisibility(View.VISIBLE);
							photoView.getProgressBar().setVisibility(View.GONE);
						}
					}

					@Override
					public void onLoadingCancelled(String arg0, View arg1) {
					}
				});

		// registerForContextMenu(download);
	}

	// @Override
	// public void onCreateContextMenu(ContextMenu menu, View v,
	// ContextMenuInfo menuInfo) {
	// super.onCreateContextMenu(menu, v, menuInfo);
	// menu.add(0, R.id.save_image_to_local, 0, R.string.saveimage);
	//
	// }
	//
	// @Override
	// public boolean onContextItemSelected(MenuItem item) {
	// if (getUserVisibleHint()) {
	// setWallpaperOrDownload(item);
	// return true;
	// }
	// return false;
	// }

	@Background
	void setWallpaperOrDownload(MenuItem item) {
	}

	@UiThread
	void toast(int resId) {
		Toast.makeText(getActivity().getApplicationContext(), resId,
				Toast.LENGTH_SHORT).show();
	}

	@Click(R.id.single_content)
	void onPageClicked() {
		getActivity().finish();
	}

}
