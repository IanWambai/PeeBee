package com.makeshift.kuhustle.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeshift.kuhustle.R;
import com.makeshift.kuhustle.constructors.MessageListItem;

import java.util.ArrayList;

/**
 * Created by Wednesday on 9/14/2015.
 */
public class MessagesRecyclerViewAdapter extends RecyclerView.Adapter<MessagesRecyclerViewAdapter.ViewHolder> {

    private ArrayList<MessageListItem> mDataset;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivIcon;
        public TextView tvUsername;
        public TextView tvLastMessage;
        public TextView tvTime;
        public TextView tvCount;

        public ViewHolder(View v) {
            super(v);

            ivIcon = (ImageView) v.findViewById(R.id.ivIcon);
            tvUsername= (TextView) v.findViewById(R.id.tvUsername);
            tvLastMessage = (TextView) v.findViewById(R.id.tvMessage);
            tvTime = (TextView) v.findViewById(R.id.tvTime);
            tvCount = (TextView) v.findViewById(R.id.tvCount);
        }
    }

    public MessagesRecyclerViewAdapter(ArrayList<MessageListItem> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MessagesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ivIcon.setImageResource(mDataset.get(position).getIcon());
        holder.tvUsername.setText(mDataset.get(position).getUsername());
        holder.tvLastMessage.setText(mDataset.get(position).getLastMessage());
        holder.tvTime.setText(mDataset.get(position).getTimestamp());
        holder.tvCount.setText(mDataset.get(position).getCount()+"");
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
