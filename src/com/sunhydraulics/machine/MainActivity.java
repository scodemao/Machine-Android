package com.sunhydraulics.machine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.content.Context;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.sunhydraulics.machine.app.AppApplication_;
import com.sunhydraulics.machine.model.ProductInfo;
import com.sunhydraulics.machine.preferences.MyPref_;
import com.sunhydraulics.machine.utils.StringUtils;
import com.sunhydraulics.machine.utils.ToastUtil;

/**
 * @author maoweiwei
 * 
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends FragmentActivity implements
		OnItemClickListener {

	private static final String TAG = "MaoMao";

	private static final String START_TAG = "##";// 一个部分的开始
	private static final String DETAIL_TAG = "**";// 一个部分的详细信息
	private static final String BLOCK_TAG = "$$";// 一个部分的结束

	@ViewById(R.id.key)
	public EditText editText;

	@ViewById(R.id.listView)
	public ListView listView;

	ProductListAdapter mAdapter;

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

	@AfterViews
	void init() {
		readDetailFile(this);

		try {
			mAdapter = new ProductListAdapter(this, AppApplication_
					.getInstance().getMyDbUtils().findAll(ProductInfo.class));
		} catch (DbException e) {
		}

		if (mAdapter != null) {
			listView.setAdapter(mAdapter);
			listView.setOnItemClickListener(this);
		}
	}

	@Click(R.id.searchbtn)
	void onSearchClick() {

		String key = null;
		key = editText.getText().toString();
		if (StringUtils.isEmpty(key))
			return;

		try {
			ProductInfo productInfo = AppApplication_
					.getInstance()
					.getMyDbUtils()
					.findFirst(
							Selector.from(ProductInfo.class).where("name", "=",
									key));

			if (productInfo == null) {

				ToastUtil.show(this, "没查到当前产品");

				return;
			}

			Log.d(TAG, productInfo.toString());

			ProductDetailActivity_.intent(this).productInfo(productInfo)
					.start();
		} catch (DbException e) {
		}

	}

	@Background
	public void readDetailFile(Context context) {

		if (mPref.isLoadDetailDataFinished().getOr(false)) {
			return;
		}

		InputStream is = context.getResources().openRawResource(R.raw.detail);
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		String line = null;

		boolean hasError = false;

		try {

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

		if (hasError)
			mPref.edit().isLoadDetailDataFinished().put(true).apply();

	}

	public static boolean isExternalStorageReadOnly() {
		String extStorageState = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
			return true;
		}
		return false;
	}

	public static boolean isExternalStorageAvailable() {
		String extStorageState = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
			return true;
		}
		return false;
	}

	@Override
	public void onBackPressed() {
		long time = System.currentTimeMillis();
		if (time - lastPressBackTime < 3000l) {
			super.onBackPressed();
		} else {
			Toast.makeText(this, R.string.msg_press_back_to_exit,
					Toast.LENGTH_SHORT).show();
		}
		lastPressBackTime = time;
	}

	long lastPressBackTime;

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		ProductInfo productInfo = mAdapter.getItem(position);
		if (productInfo != null) {
			ProductDetailActivity_.intent(this).productInfo(productInfo)
					.start();
		}

	}
}
