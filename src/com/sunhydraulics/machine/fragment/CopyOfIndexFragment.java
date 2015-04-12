package com.sunhydraulics.machine.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.sunhydraulics.machine.ProductListViewActivity_;
import com.sunhydraulics.machine.R;
import com.sunhydraulics.machine.adapter.AbstractListAdapter;
import com.sunhydraulics.machine.model.CategoryChildItem;
import com.sunhydraulics.machine.model.CategoryItem;
import com.sunhydraulics.machine.utils.MyArrayList;
import com.sunhydraulics.machine.utils.StringUtils;
import com.sunhydraulics.machine.utils.ToastUtil;

@SuppressLint("HandlerLeak")
@EFragment(R.layout.layout_gridview)
public class CopyOfIndexFragment extends Fragment implements OnItemClickListener {
	@ViewById(R.id.gv_gridview)
	public GridView gv_gridview;
	CategoryListAdapter mAdapter;

	private static final String BLOCK_ONE = "*";// 一级目录
	private static final String BLOCK_TWO = "**";// 二级目录
	private static final String BLOCK_THREE = "***";// 三级目录
	private static final String BLOCK_FOUR = "****";// 四级目录

	private static final String BLOCK_TAG = "$$$$";// 一个部分的结束

	@UiThread
	void showInfo(String msg) {
		ToastUtil.show(getActivity(), msg);
	}

	private Handler mMessageHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case 1: {
				int code = msg.arg1;
				if (code == 0) {
					showInfo("数据解析失败");
				} else {
					showInfo("数据解析成功");
					insertData((ArrayList<CategoryItem>) msg.obj);
				}
			}
				break;
			default: {
			}
				break;
			}
		}
	};

	HandlerThread mWorkerThread;
	Handler mHanlder;

	@AfterViews
	void init() {
		// ArrayList<ProductItem> list = new ArrayList<ProductItem>();
		// list.add(ProductItem.createProductItem("压力控制阀", 1, true));
		// list.add(ProductItem.createProductItem("方向阀", 2, true));
		// list.add(ProductItem.createProductItem("电磁换向阀", 3, true));
		// list.add(ProductItem.createProductItem("流量阀", 4, true));
		// list.add(ProductItem.createProductItem("逻辑单元", 5, true));
		// list.add(ProductItem.createProductItem("特殊功能阀", 6, true));
		// list.add(ProductItem.createProductItem("负载保持阀", 7, true));
		// list.add(ProductItem.createProductItem("电比例阀", 8, true));
		// list.add(ProductItem.createProductItem("线圈", 9, true));

		if (mWorkerThread == null) {
			mWorkerThread = new HandlerThread("Index Thread");
			mWorkerThread.start();
		}
		if (mHanlder == null) {
			mHanlder = new Handler(mWorkerThread.getLooper());
		}

		mHanlder.post(loadCategoryRunnable);

	}

	void insertData(ArrayList<CategoryItem> list) {
		mAdapter = new CategoryListAdapter(getActivity(), list);
		gv_gridview.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
		gv_gridview.setOnItemClickListener(this);
	}

	private Runnable loadCategoryRunnable = new Runnable() {

		@Override
		public void run() {
			Context mContext = getActivity();
			if (mContext == null)
				return;

			boolean hasError = false;
			/****/
			String level1Name = null;
			String level2Name = null;
			String level3Name = null;

			/****/
			MyArrayList<CategoryItem> level1Items = new MyArrayList<CategoryItem>();// 一级目录
			MyArrayList<CategoryChildItem> level2Items = new MyArrayList<CategoryChildItem>();// 二级目录
			MyArrayList<CategoryChildItem> level3Items = new MyArrayList<CategoryChildItem>();// 二级目录

			try {
				InputStream inputStream = mContext.getAssets().open(
						"template.txt");
				InputStreamReader isr = new InputStreamReader(inputStream);
				BufferedReader br = new BufferedReader(isr);
				String line = null;

				while ((line = br.readLine()) != null) {
					if (line.trim().equalsIgnoreCase(BLOCK_TAG)) {
						// 上部分结束
						// 下部分开始
						if (!StringUtils.isEmpty(level1Name)) {
							CategoryItem item = new CategoryItem();
							item.init();
							item.setName(level1Name);
							level1Items.add(item);
							item.getChildList().addAll(level2Items);
						}

						level1Name = null;
						level2Name = null;
						level3Name = null;

						level2Items.clear();

					} else if (line.startsWith(BLOCK_FOUR)) {
						line = line.replace(BLOCK_FOUR, "");
						level3Name = line;
						CategoryChildItem childItem = new CategoryChildItem();
						childItem.init();

						String[] itemArr = level3Name.split(";;");
						if (itemArr.length > 0) {
							childItem.setName(itemArr[0]);
						}

						if (itemArr.length == 2) {
							String strChild = itemArr[1];
							String[] childs = strChild.split(",");
							int length = childs.length;
							for (int i = 0; i < length; i++) {
								String inSt = childs[i];
								if (!StringUtils.isEmpty(inSt)) {
									CategoryChildItem itn = new CategoryChildItem();
									itn.setName(inSt);
									childItem.getChildList().add(itn);
								}
							}
						}

						/* 合并数据 和 清理数据 */
						CategoryChildItem categoryChildItem = level2Items
								.getlastOnject();
						if (categoryChildItem != null) {
							categoryChildItem.getChildList().add(childItem);
						}
						/* 合并数据 */

					} else if (line.startsWith(BLOCK_THREE)) {
						line = line.replace(BLOCK_THREE, "");
						level3Name = line;
						CategoryChildItem childItem = new CategoryChildItem();
						childItem.init();

						String[] itemArr = level3Name.split(";;");
						if (itemArr.length > 0) {
							childItem.setName(itemArr[0]);
						}

						if (itemArr.length == 2) {
							String strChild = itemArr[1];
							String[] childs = strChild.split(",");
							int length = childs.length;
							for (int i = 0; i < length; i++) {
								String inSt = childs[i];
								if (!StringUtils.isEmpty(inSt)) {
									CategoryChildItem itn = new CategoryChildItem();
									itn.setName(inSt);
									childItem.getChildList().add(itn);
								}
							}
						}

						/* 合并数据 和 清理数据 */
						CategoryChildItem categoryChildItem = level2Items
								.getlastOnject();
						if (categoryChildItem != null) {
							categoryChildItem.getChildList().add(childItem);
						}
						/* 合并数据 */

					} else if (line.startsWith(BLOCK_TWO)) {
						line = line.replace(BLOCK_TWO, "");
						level2Name = line;

						CategoryChildItem childItem = new CategoryChildItem();
						childItem.init();
						childItem.setName(level2Name);
						level2Items.add(childItem);

					} else if (line.startsWith(BLOCK_ONE)) {
						line = line.replace(BLOCK_ONE, "").trim();
						level1Name = line;
					}
				}
			} catch (IOException e) {
				hasError = true;
			}

			Message msg = new Message();
			msg.what = 1;
			msg.arg1 = hasError ? 0 : 1;
			msg.obj = level1Items;
			mMessageHandler.sendMessage(msg);
		}
	};

	class CategoryListAdapter extends AbstractListAdapter<CategoryItem> {

		public CategoryListAdapter(Context context, List<CategoryItem> content) {
			super(context, content);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (null == convertView) {
				convertView = mInflater.inflate(R.layout.layout_product_cell,
						null);
				holder = new ViewHolder();
				holder.itemView = (TextView) convertView
						.findViewById(R.id.itemview);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			CategoryItem itemObject = getItem(position);
			holder.itemView.setText(itemObject.getName());

			return convertView;
		}

	}

	static class ViewHolder {
		TextView itemView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		CategoryItem item = mAdapter.getItem(position);
		if (item != null) {
			ProductListViewActivity_.intent(getActivity()).categoryItem(item)
					.start();
		}
	}
}
