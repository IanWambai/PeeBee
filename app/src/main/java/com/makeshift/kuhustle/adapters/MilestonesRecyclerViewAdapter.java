package com.makeshift.kuhustle.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeshift.kuhustle.R;
import com.makeshift.kuhustle.constructors.MilestoneListItem;
import com.makeshift.kuhustle.constructors.NotificationListItem;

import java.util.ArrayList;

/**
 * Created by Wednesday on 9/14/2015.
 */
public class MilestonesRecyclerViewAdapter extends RecyclerView.Adapter<MilestonesRecyclerViewAdapter.ViewHolder> {

    private ArrayList<MilestoneListItem> mDataset;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public TextView tvMessage;
        public TextView tvTime;

        public ViewHolder(View v) {
            super(v);

            tvTitle= (TextView) v.findViewById(R.id.tvTitle);
            tvMessage = (TextView) v.findViewById(R.id.tvMessage);
            tvTime = (TextView) v.findViewById(R.id.tvTime);
        }
    }

    public MilestonesRecyclerViewAdapter(ArrayList<MilestoneListItem> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MilestonesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.milestone_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvTitle.setText(mDataset.get(position).getTitle());
        holder.tvMessage.setText(mDataset.get(position).getMessage());
        holder.tvTime.setText(mDataset.get(position).getTimestamp());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
