package com.makeshift.kuhustle.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeshift.kuhustle.R;
import com.makeshift.kuhustle.constructors.JobListItem;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.ArrayList;

/**
 * Created by Wednesday on 9/14/2015.
 */
public class JobsRecyclerViewAdapter extends RecyclerView.Adapter<JobsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<JobListItem> mDataset;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivIcon;
        public TextView tvTitle, tvDescription, tvValue, tvEndsInTitle, tvTimeLeft, tvDaysTitle, tvBids, tvBidsTitle;

        public ViewHolder(View v) {
            super(v);

            ivIcon = (ImageView) v.findViewById(R.id.ivIcon);
            tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            tvDescription = (TextView) v.findViewById(R.id.tvDescription);
            tvValue = (TextView) v.findViewById(R.id.tvValue);
            tvEndsInTitle = (TextView) v.findViewById(R.id.tvEndsInTitle);
            tvTimeLeft = (TextView) v.findViewById(R.id.tvTimeLeft);
            tvDaysTitle = (TextView) v.findViewById(R.id.tvDaysTitle);
            tvBids = (TextView) v.findViewById(R.id.tvBids);
            tvBidsTitle = (TextView) v.findViewById(R.id.tvBidsTitle);
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
        holder.tvDescription.setText(mDataset.get(position).getJobDescription().substring(0, 100).trim() + "...");

        String budget = null;

        if (mDataset.get(position).getValueRange().contains("1")) {
            budget = "Ksh.10,000 - Ksh.50,000";
        } else if (mDataset.get(position).getValueRange().contains("2")) {
            budget = "Ksh.50,000 - Ksh.100,000";
        } else if (mDataset.get(position).getValueRange().contains("3")) {
            budget = "Ksh.100,000 - Ksh.200,000";
        } else if (mDataset.get(position).getValueRange().contains("4")) {
            budget = "Ksh.200,000 - Ksh.500,000";
        } else if (mDataset.get(position).getValueRange().contains("5")) {
            budget = "Above Ksh.500,000";
        } else if (mDataset.get(position).getValueRange().contains("6")) {

        }

        holder.tvValue.setText(budget);

        int daysToEnd = Days.daysBetween(new DateTime(), new DateTime(mDataset.get(position).getTimeLeft().getTime().getTime())).getDays();
        if (daysToEnd > 0) {
            holder.tvTimeLeft.setText(String.valueOf(daysToEnd));
        } else {
            holder.tvEndsInTitle.setText("Bidding is closed");
            holder.tvDaysTitle.setVisibility(View.INVISIBLE);
            holder.tvDaysTitle.setWidth(0);
            holder.tvDaysTitle.setHeight(0);
            holder.tvTimeLeft.setVisibility(View.INVISIBLE);
            holder.tvTimeLeft.setWidth(0);
            holder.tvTimeLeft.setHeight(0);
        }

        holder.tvBids.setText(String.valueOf(mDataset.get(position).getNumberOfBids()));
        if (mDataset.get(position).getNumberOfBids() == 1) {
            holder.tvBidsTitle.setText("day");
        } else {
            holder.tvBidsTitle.setText("days");
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
