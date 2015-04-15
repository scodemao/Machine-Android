package com.sunhydraulics.machine.view;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import uk.co.senab.photoview.PhotoView;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.sunhydraulics.machine.R;

/**
 * @author maoweiwei
 * 
 */
@EViewGroup(R.layout.layout_progressable_photoview)
public class ProgressablePhotoView extends FrameLayout implements ImageProgress {

	@ViewById(R.id.progressablePhotoView_photo)
	protected PhotoView imageView;
	@ViewById(R.id.progressablePhotoView_progress)
	protected ProgressBar progressBar;

	public ProgressablePhotoView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public ProgressablePhotoView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ProgressablePhotoView(Context context) {
		this(context, null);
	}

	public PhotoView getImageView() {
		return imageView;
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

	@Override
	public ViewScaleType getScaleType() {
		return ViewScaleType.CROP;
	}

	@Override
	public View getWrappedView() {
		return this;
	}

	@Override
	public boolean isCollected() {
		return false;
	}

	@Override
	public boolean setImageDrawable(Drawable drawable) {
		imageView.setImageDrawable(drawable);
		return true;
	}

	@Override
	public boolean setImageBitmap(Bitmap bitmap) {
		Drawable drawable = imageView.getDrawable();
		if (null != drawable && drawable instanceof BitmapDrawable) {

		}
		imageView.setImageBitmap(bitmap);
		return true;
	}

}
