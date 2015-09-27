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
import com.makeshift.kuhustle.constructors.ProductListItem;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CardItemAdapter extends ArrayAdapter<ProductListItem> {
    private ArrayList<ProductListItem> objects;
    Context context;

    public CardItemAdapter(Context context, int textViewResourceId,
                           ArrayList<ProductListItem> objects) {
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
            v = inflater.inflate(R.layout.card_layout, null);
        }

        ProductListItem i = objects.get(position);
        if (i != null) {

            ImageView ivProductImage = (ImageView) v
                    .findViewById(R.id.ivProductImage);

            TextView tvQuantity = (TextView) v.findViewById(R.id.tvQuantity);
            TextView tvProductName = (TextView) v
                    .findViewById(R.id.tvProductName);
            TextView tvDescription = (TextView) v
                    .findViewById(R.id.tvDescription);
            TextView tvPrice = (TextView) v.findViewById(R.id.tvPrice);

            if (ivProductImage != null) {
                Picasso.with(context)
                        .load(i.getImageUrl()).placeholder(R.drawable.profile_picture)
                        .into(ivProductImage);
            }

            if (tvProductName != null) {
                tvProductName.setText(i.getName());
            }
            if (tvDescription != null) {
                tvDescription.setText(i.getDescription());
            }
            if (tvPrice != null) {
                DecimalFormat df = new DecimalFormat("###,###,###.##");
                tvPrice.setText(df.format(Float.parseFloat(i.getPrice()))
                        + ".00");
            }

        }
        return v;
    }
}