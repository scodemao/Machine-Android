package com.sunhydraulics.machine.view;

import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.imageaware.ImageAware;

/**
 * @author maoweiwei
 *
 */
public interface ImageProgress extends ImageAware {

	public ImageView getImageView();

	public View getProgressBar();
}
