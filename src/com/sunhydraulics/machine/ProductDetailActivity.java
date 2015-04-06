package com.sunhydraulics.machine;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import com.sunhydraulics.machine.model.ProductInfo;

import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

@EActivity(R.layout.layout_product_detail_view)
public class ProductDetailActivity extends FragmentActivity {

	@ViewById(R.id.titleView)
	public TextView titleView;

	@ViewById(R.id.productimage)
	public ImageView productimage;

	@ViewById(R.id.detailView)
	public TextView detailView;

	@Extra("ProductInfo")
	public ProductInfo productInfo;

	@AfterViews
	void init() {
		if (productInfo != null) {
			titleView.setText(productInfo.getName());
			detailView.setText(productInfo.getDesc());
		}
	}
}
