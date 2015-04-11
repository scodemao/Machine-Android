package com.sunhydraulics.machine;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.sunhydraulics.machine.adapter.ProductChildListAdapter;
import com.sunhydraulics.machine.app.AppApplication_;
import com.sunhydraulics.machine.model.CategoryChildItem;
import com.sunhydraulics.machine.model.CategoryItem;
import com.sunhydraulics.machine.model.ProductInfo;
import com.sunhydraulics.machine.utils.ToastUtil;

/**
 * @author maoweiwei
 * 
 */
@SuppressLint("HandlerLeak")
@EActivity(R.layout.layout_product_list)
public class ProductListViewActivity extends BaseBackActivity implements
		OnItemClickListener {

	@Extra("categoryChildItem")
	public CategoryChildItem categoryChildItem;

	@Extra("categoryItem")
	public CategoryItem categoryItem;

	@ViewById(R.id.listView)
	public ListView listView;

	ProductChildListAdapter mAdapter;

	HandlerThread mWorkerThread;
	Handler mHanlder;

	protected String searchKey;

	@AfterViews
	void init() {
		if (categoryItem != null) {
			getActionBar().setTitle(categoryItem.getName());
			mAdapter = new ProductChildListAdapter(this,
					categoryItem.getChildList());
			listView.setAdapter(mAdapter);
		}

		if (categoryChildItem != null) {
			getActionBar().setTitle(categoryChildItem.getName());
			mAdapter = new ProductChildListAdapter(this,
					categoryChildItem.getChildList());
			listView.setAdapter(mAdapter);
		}
		listView.setOnItemClickListener(this);

		if (mWorkerThread == null) {
			mWorkerThread = new HandlerThread("ProductList Thread");
			mWorkerThread.start();
		}
		if (mHanlder == null) {
			mHanlder = new Handler(mWorkerThread.getLooper());
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		CategoryChildItem item = mAdapter.getItem(position);
		if (item != null) {
			if (item.getChildList() != null && item.getChildList().size() > 0) {
				ProductListViewActivity_.intent(this).categoryChildItem(item)
						.start();
			} else {
				searchKey = item.getName().trim();
				mHanlder.post(searchProductRunnable);
			}
		}
	}

	/**
	 * 搜索产品
	 */
	private Runnable searchProductRunnable = new Runnable() {

		@Override
		public void run() {

			Context mContext = ProductListViewActivity.this;
			if (mContext == null)
				return;

			boolean hasError = false;
			ProductInfo productInfo = null;

			try {
				productInfo = AppApplication_
						.getInstance()
						.getMyDbUtils()
						.findFirst(
								Selector.from(ProductInfo.class).where("name",
										"=", searchKey));
			} catch (DbException e) {
				hasError = true;
			}

			Message msg = new Message();
			msg.what = 3;
			msg.arg1 = hasError ? 0 : 1;
			msg.obj = productInfo;
			mMessageHandler.sendMessage(msg);
		}
	};

	@UiThread
	void showInfo(String msg) {
		ToastUtil.show(this, msg);
	}

	private Handler mMessageHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case 1: {
			}
				break;
			case 2: {
			}
				break;
			case 3: {
				int code = msg.arg1;
				if (code == 0) {
					showInfo("未查到该产品");
				} else {
					ProductInfo productInfo = (ProductInfo) msg.obj;
					if (productInfo != null) {
						ProductDetailActivity_
								.intent(ProductListViewActivity.this)
								.productInfo(productInfo).start();
					} else {
						showInfo("未查到该产品");
					}
				}
			}
				break;
			}
		}
	};

}
