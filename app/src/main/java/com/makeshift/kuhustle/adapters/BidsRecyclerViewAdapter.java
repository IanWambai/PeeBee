package com.makeshift.kuhustle.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeshift.kuhustle.R;
import com.makeshift.kuhustle.constructors.BidListItem;

import java.util.ArrayList;

/**
 * Created by Wednesday on 9/14/2015.
 */
public class BidsRecyclerViewAdapter extends RecyclerView.Adapter<BidsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<BidListItem> mDataset;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivIcon;
        public TextView tvUsername, tvMessage, tvValue, tvTime;

        public ViewHolder(View v) {
            super(v);

            ivIcon = (ImageView) v.findViewById(R.id.ivIcon);
            tvUsername = (TextView) v.findViewById(R.id.tvUsername);
            tvMessage = (TextView) v.findViewById(R.id.tvMessage);
            tvValue = (TextView) v.findViewById(R.id.tvValue);
            tvTime = (TextView) v.findViewById(R.id.tvTime);
        }
    }

    public BidsRecyclerViewAdapter(ArrayList<BidListItem> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public BidsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bids_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.ivIcon.setImageResource(mDataset.get(position).getIcon());
        holder.tvUsername.setText(mDataset.get(position).getUsername());
        holder.tvMessage.setText(mDataset.get(position).getMessage());
        holder.tvValue.setText(mDataset.get(position).getValue());
        holder.tvTime.setText(mDataset.get(position).getTimestamp());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
