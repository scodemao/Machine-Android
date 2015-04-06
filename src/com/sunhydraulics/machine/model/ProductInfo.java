package com.sunhydraulics.machine.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.lidroid.xutils.db.annotation.Column;

public class ProductInfo extends EntityBase implements Parcelable {

	@Override
	public String toString() {
		return "ProductInfo [name=" + name + ", desc=" + desc + "]";
	}

	@Column(column = "name")
	private String name;

	@Column(column = "descinfo")
	private String desc;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static final Parcelable.Creator<ProductInfo> CREATOR = new Parcelable.Creator<ProductInfo>() {

		@Override
		public ProductInfo createFromParcel(Parcel source) {
			ProductInfo a = new ProductInfo();
			a.name = source.readString();
			a.desc = source.readString();

			return a;
		}

		@Override
		public ProductInfo[] newArray(int size) {
			return new ProductInfo[size];
		}

	};

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(desc);
	}
}
