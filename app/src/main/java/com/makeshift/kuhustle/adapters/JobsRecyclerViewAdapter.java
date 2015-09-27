package com.makeshift.kuhustle.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeshift.kuhustle.R;
import com.makeshift.kuhustle.constructors.JobListItem;

import java.util.ArrayList;

/**
 * Created by Wednesday on 9/14/2015.
 */
public class JobsRecyclerViewAdapter extends RecyclerView.Adapter<JobsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<JobListItem> mDataset;
    private int lastPosition = -1;


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivIcon;
        public TextView tvTitle, tvDescription, tvValue,tvBids;

        RelativeTimeTextView tvTimeLeft;

        public ViewHolder(View v) {
            super(v);

            ivIcon = (ImageView) v.findViewById(R.id.ivIcon);
            tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            tvDescription = (TextView) v.findViewById(R.id.tvDescription);
            tvValue = (TextView) v.findViewById(R.id.tvValue);
            tvTimeLeft = (RelativeTextView) v.findViewById(R.id.tvTimeLeft);
            tvBids = (TextView) v.findViewById(R.id.tvBids);
        }
    }

    public JobsRecyclerViewAdapter(ArrayList<JobListItem> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public JobsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.ivIcon.setImageResource(mDataset.get(position).getIcon());
        holder.tvTitle.setText(mDataset.get(position).getJobTitle());
        holder.tvDescription.setText(mDataset.get(position).getJobDescription().substring(0, 100).trim()+"...");
        holder.tvValue.setText(mDataset.get(position).getValueRange());
        holder.tvTimeLeft.setReferenceTime(mDataset.get(position).getTimeLeft().getTime());
        holder.tvBids.setText(mDataset.get(position).getNumberOfBids());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
