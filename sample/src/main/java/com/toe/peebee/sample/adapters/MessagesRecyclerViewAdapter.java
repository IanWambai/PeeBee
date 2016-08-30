package com.toe.peebee.sample.adapters;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toe.peebee.constructors.MessageItem;
import com.toe.peebee.sample.R;

import java.util.ArrayList;

/**
 * Created by Wednesday on 9/14/2015.
 */
public class MessagesRecyclerViewAdapter extends RecyclerView.Adapter<MessagesRecyclerViewAdapter.ViewHolder> {

    private ArrayList<MessageItem> chats;
    private Activity activity;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvMessage, tvFrom, tvTo, tvTimestamp;

        public ViewHolder(View v) {
            super(v);
            tvMessage = (TextView) v.findViewById(R.id.tvMessage);
            tvFrom = (TextView) v.findViewById(R.id.tvFrom);
            tvTo = (TextView) v.findViewById(R.id.tvTo);
            tvTimestamp = (TextView) v.findViewById(R.id.tvTimestamp);
        }
    }

    public MessagesRecyclerViewAdapter(Activity activity, ArrayList<MessageItem> chats) {
        this.activity = activity;
        this.chats = chats;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvMessage.setText("Message: " + chats.get(position).getMessage());
        holder.tvFrom.setText("From: " + chats.get(position).getFrom());
        holder.tvTo.setText("To: " + chats.get(position).getTo());
        holder.tvTimestamp.setText("Timestamp: " + chats.get(position).getTimestamp());
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }
}
