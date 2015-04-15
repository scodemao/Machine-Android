package com.sunhydraulics.machine;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.sunhydraulics.machine.adapter.ImageAdapter;
import com.sunhydraulics.machine.model.Photo;

@EActivity(R.layout.act_single_image)
public class SingleImageActivity extends FragmentActivity {

	@ViewById(R.id.single_image_viewpager)
	ViewPager viewpager;

	ImageAdapter mAdapter;

	@Extra("photos")
	ArrayList<Photo> photos;

	@Extra("current_position")
	int currentPosition;

	@Extra("only_download")
	boolean onlyDownload;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@AfterViews
	void init() {
		mAdapter = new ImageAdapter(getSupportFragmentManager(), photos,
				onlyDownload);
		viewpager.setAdapter(mAdapter);
		viewpager.setCurrentItem(currentPosition);
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}
}
