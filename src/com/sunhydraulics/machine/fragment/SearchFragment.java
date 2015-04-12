package com.sunhydraulics.machine.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.sunhydraulics.machine.ProductDetailActivity_;
import com.sunhydraulics.machine.R;
import com.sunhydraulics.machine.adapter.ProductListAdapter;
import com.sunhydraulics.machine.app.AppApplication_;
import com.sunhydraulics.machine.model.ProductInfo;
import com.sunhydraulics.machine.preferences.MyPref_;
import com.sunhydraulics.machine.utils.StringUtils;
import com.sunhydraulics.machine.utils.ToastUtil;
import com.sunhydraulics.machine.utils.ZipManager;

/**
 * @author maoweiwei
 * 
 */
@SuppressLint("HandlerLeak")
@EFragment(R.layout.search_main)
public class SearchFragment extends Fragment implements OnItemClickListener {

	private static final String START_TAG = "##";// 一个部分的开始
	private static final String DETAIL_TAG = "**";// 一个部分的详细信息
	private static final String BLOCK_TAG = "$$";// 一个部分的结束

	@ViewById(R.id.key)
	public EditText editText;

	@ViewById(R.id.listView)
	public ListView listView;

	@ViewById(R.id.loadingview)
	public View loadingView;

	ProductListAdapter mAdapter;

	public String searchKey;

	private Handler mMessageHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case 1: {
				int code = msg.arg1;
				if (code == 0) {
					mPref.edit().isLoadPicDataFinished().put(false).apply();
					showInfo("图片解压失败");
				} else {
					mPref.edit().isLoadPicDataFinished().put(true).apply();
					showInfo("图片解压成功");
				}
				currentStep--;
				hiddenLoadingView();
			}
				break;
			case 2: {
				int code = msg.arg1;
				if (code == 0) {
					mPref.edit().isLoadDetailDataFinished().put(false).apply();
					showInfo("TXT加载失败");
				} else {
					mPref.edit().isLoadDetailDataFinished().put(true).apply();
					showInfo("TXT加载成功");
				}
				currentStep--;
				hiddenLoadingView();
			}
				break;
			case 3: {
				int code = msg.arg1;
				if (code == 0) {
					showInfo("未查到该产品");
				} else {
					ProductInfo productInfo = (ProductInfo) msg.obj;
					if (productInfo != null) {
						ProductDetailActivity_.intent(getActivity())
								.productInfo(productInfo).start();
					} else {
						showInfo("未查到该产品");
					}
				}
			}
				break;
			default: {
				if (mAdapter != null) {
					listView.setAdapter(mAdapter);
					listView.setOnItemClickListener(SearchFragment.this);
				}
			}

				break;
			}
		}
	};

	@Pref
	MyPref_ mPref;

	public enum NextData {
		title, detail, finish;

		public boolean isTitle() {
			return this == title;
		}

		public boolean isDetail() {
			return this == detail;
		}

		public boolean isFinish() {
			return this == finish;
		}

	}

	HandlerThread mWorkerThread;
	Handler mHanlder;

	public int currentStep;

	@AfterViews
	void init() {

		if (mPref.isLoadDetailDataFinished().getOr(false)
				&& mPref.isLoadPicDataFinished().getOr(false)) {
			loadingView.setVisibility(View.GONE);
		}

		if (mWorkerThread == null) {
			mWorkerThread = new HandlerThread("Worker Thread");
			mWorkerThread.start();
		}
		if (mHanlder == null) {
			mHanlder = new Handler(mWorkerThread.getLooper());
		}

		readDetailFile();
		loadZip();
		reloadFromDB();
	}

	private void reloadFromDB() {
		mHanlder.post(listDataRunnable);
	}

	void hiddenLoadingView() {
		if (currentStep == 0 && loadingView != null) {
			loadingView.setVisibility(View.GONE);
		}
	}

	@UiThread
	void showInfo(String msg) {
		ToastUtil.show(getActivity(), msg);
	}

	@Click(R.id.searchbtn)
	void onSearchClick() {

		searchKey = editText.getText().toString();
		if (StringUtils.isEmpty(searchKey))
			return;

		mHanlder.removeCallbacks(searchProductRunnable);
		mHanlder.post(searchProductRunnable);
	}

	void loadZip() {

		if (mPref.isLoadPicDataFinished().getOr(false)) {
			return;
		}
		currentStep++;
		mHanlder.post(zipRunnable);
	}

	public void readDetailFile() {

		if (mPref.isLoadDetailDataFinished().getOr(false)) {
			return;
		}
		currentStep++;
		mHanlder.post(detailTextRunnable);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		ProductInfo productInfo = mAdapter.getItem(position);
		if (productInfo != null) {
			ProductDetailActivity_.intent(this).productInfo(productInfo)
					.start();
		}

	}

	/**
	 * 加载pic ZIP数据
	 */
	private Runnable zipRunnable = new Runnable() {

		@Override
		public void run() {

			boolean hasError = ZipManager.getInstance().loadZip(getActivity());
			Message msg = new Message();
			msg.what = 1;
			msg.arg1 = hasError ? 0 : 1;
			mMessageHandler.sendMessage(msg);
		}
	};

	/**
	 * 产品详细信息
	 */
	private Runnable detailTextRunnable = new Runnable() {

		@Override
		public void run() {

			Context mContext = getActivity();
			if (mContext == null)
				return;

			boolean hasError = false;
			try {
				InputStream is = mContext.getAssets().open("detail.txt");
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String line = null;

				NextData nextData = null;
				StringBuilder title = new StringBuilder();
				StringBuilder detail = new StringBuilder();

				while ((line = br.readLine()) != null) {
					if (line.trim().equalsIgnoreCase(START_TAG)) {
						if (nextData == null) {
							nextData = NextData.title;
						} else {
							nextData = NextData.title;
						}
					} else if (line.trim().equalsIgnoreCase(DETAIL_TAG)) {
						nextData = NextData.detail;
					} else if (line.trim().equalsIgnoreCase(BLOCK_TAG)) {
						nextData = NextData.finish;
						// 存储上一个Model
						ProductInfo info = new ProductInfo();
						info.setName(title.toString());
						info.setDesc(detail.toString());
						AppApplication_.getInstance().getMyDbUtils().save(info);
						title.setLength(0);
						detail.setLength(0);
					} else {
						if (nextData.isTitle()) {
							title.append(line);
						} else {
							detail.append(line).append("\n");
						}
					}
				}
			} catch (IOException e) {
				hasError = true;
			} catch (DbException e) {
				hasError = true;
			}

			Message msg = new Message();
			msg.what = 2;
			msg.arg1 = hasError ? 0 : 1;
			mMessageHandler.sendMessage(msg);
		}
	};

	/**
	 * 搜索产品
	 */
	private Runnable searchProductRunnable = new Runnable() {

		@Override
		public void run() {

			Context mContext = getActivity();
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

	/**
	 * 从数据库中获取所有数据
	 */
	private Runnable listDataRunnable = new Runnable() {

		@Override
		public void run() {
			Context mContext = getActivity();
			if (mContext == null)
				return;
			try {
				mAdapter = new ProductListAdapter(mContext, AppApplication_
						.getInstance().getMyDbUtils()
						.findAll(ProductInfo.class));
			} catch (DbException e) {
			}

			Message msg = new Message();
			msg.what = 4;
			mMessageHandler.sendMessage(msg);
		}
	};

}
