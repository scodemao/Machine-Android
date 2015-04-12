package com.sunhydraulics.machine.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.lidroid.xutils.db.annotation.Column;
import com.sunhydraulics.machine.utils.MyArrayList;

/**
 * @author maoweiwei
 * 
 */
public class CategoryChildItem extends EntityBase implements Parcelable {

	@Override
	public String toString() {
		return "ProductInfo [name=" + name + ", childinfo=" + childinfo + "]";
	}

	@Column(column = "name")
	private String name;

	@Column(column = "childinfo")
	private String childinfo;

	@Column(column = "childList")
	private MyArrayList<CategoryChildItem> childList;

	public void init() {
		childList = new MyArrayList<CategoryChildItem>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return childinfo;
	}

	public void setDesc(String desc) {
		this.childinfo = desc;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Parcelable.Creator<CategoryChildItem> CREATOR = new Parcelable.Creator<CategoryChildItem>() {

		@Override
		public CategoryChildItem createFromParcel(Parcel source) {
			CategoryChildItem a = new CategoryChildItem();
			a.name = source.readString();
			a.childinfo = source.readString();

			MyArrayList<CategoryChildItem> l = new MyArrayList<CategoryChildItem>();
			source.readTypedList(l, CategoryChildItem.CREATOR);
			a.childList = l;

			return a;
		}

		@Override
		public CategoryChildItem[] newArray(int size) {
			return new CategoryChildItem[size];
		}

	};

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(childinfo);
		dest.writeTypedList(childList);
	}

	public MyArrayList<CategoryChildItem> getChildList() {
		return childList;
	}

	public void setChildList(MyArrayList<CategoryChildItem> childList) {
		this.childList = childList;
	}
}
