package com.makeshift.kuhustle.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {

	// Typeface font;

	public SpinnerAdapter(Context context, int resource, List<String> items) {
		super(context, resource, items);
		// font = Typeface.createFromAsset(context.getAssets(), context
		// .getResources().getString(R.string.font));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view = (TextView) super.getView(position, convertView, parent);
		// view.setTypeface(font);
		return view;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		TextView view = (TextView) super.getDropDownView(position, convertView,
				parent);
		// view.setTypeface(font);
		return view;
	}
}