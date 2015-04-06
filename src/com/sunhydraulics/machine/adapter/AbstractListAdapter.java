package com.sunhydraulics.machine.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

/**
 * @author maoweiwei
 * 
 * @param <T>
 */
public abstract class AbstractListAdapter<T> extends BaseAdapter {
	protected List<T> mContent;
	protected Context mContext;
	protected LayoutInflater mInflater;

	private int selectIndex = 0;

	private long userMatchID;

	public AbstractListAdapter(Context context, List<T> content) {
		mContext = context;
		mContent = content;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return null == mContent ? 0 : mContent.size();
	}

	@Override
	public T getItem(int position) {
		return null == mContent || position < 0 || position >= mContent.size() ? null
				: mContent.get(position);

	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setContents(List<T> content) {
		mContent = content;
	}

	public List<T> getContents() {
		return mContent;
	}

	public void addItem(int position, T obj) {
		if (null == mContent) {
			mContent = new ArrayList<T>();
		}
		if (null != obj) {
			mContent.remove(obj);
		}
		mContent.add(position, obj);
	}

	public boolean addItem(T obj) {
		if (null == mContent) {
			mContent = new ArrayList<T>();
		}
		if (!mContent.contains(obj)) {
			return mContent.add(obj);
		}
		return false;
	}

	public T removeItemAt(int position) {
		if (null == mContent) {
			return null;
		}
		return mContent.remove(position);
	}

	public boolean removeItem(T obj) {
		if (null == mContent) {
			return false;
		}
		return mContent.remove(obj);
	}

	public boolean addAll(List<T> objs) {
		if (null == mContent) {
			mContent = new ArrayList<T>();
		}
		if (null != objs) {
			for (int i = 0; i < objs.size(); i++) {
				T item = objs.get(i);
				if (!mContent.contains(item)) {
					mContent.add(item);
				}
			}
			return true;
		}
		return false;
	}

	public boolean removeAll(List<T> objs) {
		if (null == mContent) {
			return false;
		}
		return mContent.removeAll(objs);
	}

	public int getSelectIndex() {
		return selectIndex;
	}

	public void setSelectIndex(int selectIndex) {
		this.selectIndex = selectIndex;
	}

	public long getUserMatchID() {
		return userMatchID;
	}

	public void setUserMatchID(long id) {
		this.userMatchID = id;
	}
}
