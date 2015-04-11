package com.sunhydraulics.machine.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductItem extends EntityBase implements Parcelable {

	private String itemName;
	private int itemId;
	private boolean hasChild;

	public String getItemName() {
		return itemName;
	}

	public static ProductItem createProductItem(String itemName, int itemId,
			boolean hasChild) {

		ProductItem item = new ProductItem();
		item.itemName = itemName;
		item.itemId = itemId;
		item.hasChild = hasChild;

		return item;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public boolean isHasChild() {
		return hasChild;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(itemName);
		dest.writeLong(itemId);
		dest.writeByte((byte) (hasChild ? 1 : 0));
	}

	public static final Parcelable.Creator<ProductItem> CREATOR = new Parcelable.Creator<ProductItem>() {

		@Override
		public ProductItem createFromParcel(Parcel source) {
			ProductItem p = new ProductItem();
			p.itemName = source.readString();
			p.itemId = source.readInt();
			p.hasChild = source.readByte() != 0;
			return p;
		}

		@Override
		public ProductItem[] newArray(int size) {
			return new ProductItem[size];
		}

	};

}
