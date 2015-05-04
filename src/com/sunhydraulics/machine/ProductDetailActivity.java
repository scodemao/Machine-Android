package com.sunhydraulics.machine;

import java.io.File;
import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.sunhydraulics.machine.model.Photo;
import com.sunhydraulics.machine.model.ProductInfo;
import com.sunhydraulics.machine.utils.ImageLoaderUtils;
import com.sunhydraulics.machine.utils.StorageUtils;
import com.sunhydraulics.machine.view.RatioImageView;

/**
 * @author maoweiwei
 * 
 */
@EActivity(R.layout.layout_product_detail_view)
public class ProductDetailActivity extends BaseBackActivity implements
		OnClickListener {

	@ViewById(R.id.titleView)
	public TextView titleView;

	@ViewById(R.id.productimage)
	public RatioImageView productimage;

	@ViewById(R.id.detailView)
	public TextView detailView;

	@Extra("ProductInfo")
	public ProductInfo productInfo;

	@AfterViews
	void init() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);
		getActionBar().setDisplayUseLogoEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
		if (productInfo != null) {
			getActionBar().setTitle(productInfo.getName());

			File file = new File(StorageUtils.getDefaultCacheDir(this),
					productInfo.getName() + ".png");

			productimage.setTag(file.toString());
			productimage.setOnClickListener(this);

			ImageLoaderUtils.displayPic(file.toString(), productimage,
					new ImageLoadingListener() {

						@Override
						public void onLoadingStarted(String arg0, View arg1) {
						}

						@Override
						public void onLoadingFailed(String arg0, View arg1,
								FailReason arg2) {
						}

						@Override
						public void onLoadingComplete(String arg0, View arg1,
								Bitmap bitmap) {

							if (bitmap != null && !bitmap.isRecycled()) {
								productimage.setWHRatio((float) bitmap
										.getWidth()
										/ (float) bitmap.getHeight());
								productimage.requestLayout();
							}

						}

						@Override
						public void onLoadingCancelled(String arg0, View arg1) {
						}
					});

			titleView.setText(productInfo.getName());
			detailView.setText(productInfo.getDesc());
		}
	}

	@Override
	public void onClick(View v) {

		String imgurl = (String) v.getTag();

		ArrayList<Photo> list = new ArrayList<Photo>();
		Photo p = new Photo();
		p.setImageUrl(imgurl);
		list.add(p);
		SingleImageActivity_.intent(this).photos(list).start();
	}
}
