package com.makeshift.kuhustle.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeshift.kuhustle.R;
import com.makeshift.kuhustle.constructors.SkillListItem;

import java.util.ArrayList;

public class GridItemAdapter extends ArrayAdapter<SkillListItem> {
    private ArrayList<SkillListItem> objects;
    Context context;

    public GridItemAdapter(Context context, int textViewResourceId,
                           ArrayList<SkillListItem> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
        this.context = context;
    }

    @SuppressLint("InflateParams")
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.grid_layout, null);
        }

        SkillListItem i = objects.get(position);
        if (i != null) {

            ImageView ivProductImage = (ImageView) v
                    .findViewById(R.id.ivIcon);

            TextView tvTitle = (TextView) v
                    .findViewById(R.id.tvTitle);

            if (ivProductImage != null) {
                ivProductImage.setImageResource(i.getIcon());
            }

            if (tvTitle != null) {
                tvTitle.setText(i.getTitle());
            }

        }
        return v;
    }
}