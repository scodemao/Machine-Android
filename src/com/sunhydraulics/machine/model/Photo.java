package com.sunhydraulics.machine.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author maoweiwei
 *
 */
public class Photo implements Parcelable {

	protected String imageUrl;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(imageUrl);
	}

	public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {

		@Override
		public Photo createFromParcel(Parcel source) {
			Photo p = new Photo();

			p.imageUrl = source.readString();
			return p;
		}

		@Override
		public Photo[] newArray(int size) {
			return new Photo[size];
		}

	};

}
