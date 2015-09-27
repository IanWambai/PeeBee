package com.makeshift.kuhustle.constructors;

import android.os.Parcel;

import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;

public class SkillListItem implements AsymmetricItem {
	private String title;
	private int icon;

	public SkillListItem(String title, int icon) {
		this.title = title;
		this.icon = icon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	@Override
	public int getColumnSpan() {
		return 0;
	}

	@Override
	public int getRowSpan() {
		return 0;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}
}