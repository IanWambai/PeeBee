package com.makeshift.kuhustle.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeshift.kuhustle.R;
import com.makeshift.kuhustle.constructors.CardItem;

import java.util.ArrayList;

/**
 * Created by Wednesday on 9/15/2015.
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    private ArrayList<CardItem> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView title;
        public TextView description;

        public ViewHolder(View v) {
            super(v);
            view = v;
            title = (TextView) v.findViewById(R.id.tvTitle);
            description = (TextView) v.findViewById(R.id.tvDescription);
        }
    }

    public CardViewAdapter(ArrayList<CardItem> cards) {
        mDataset = cards;
    }

    @Override
    public CardViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        return new ViewHolder(cardview);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(mDataset.get(position).getTitle());
        holder.description.setText(mDataset.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}