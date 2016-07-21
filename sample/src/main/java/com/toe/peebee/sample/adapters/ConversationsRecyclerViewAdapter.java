package com.toe.peebee.sample.adapters;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toe.peebee.constructors.ConversationItem;
import com.toe.peebee.sample.R;

import java.util.ArrayList;

/**
 * Created by Wednesday on 9/14/2015.
 */
public class ConversationsRecyclerViewAdapter extends RecyclerView.Adapter<ConversationsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<ConversationItem> chats;
    private Activity activity;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvFrom, tvTo, tvTimestamp;

        public ViewHolder(View v) {
            super(v);
            tvFrom = (TextView) v.findViewById(R.id.tvFrom);
            tvTo = (TextView) v.findViewById(R.id.tvTo);
            tvTimestamp = (TextView) v.findViewById(R.id.tvTimestamp);
        }
    }

    public ConversationsRecyclerViewAdapter(Activity activity, ArrayList<ConversationItem> chats) {
        this.activity = activity;
        this.chats = chats;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvFrom.setText("From: " + chats.get(position).getFrom());
        holder.tvTo.setText("To: " + chats.get(position).getTo());
        holder.tvTimestamp.setText("Timestamp: " + chats.get(position).getTimestamp());
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }
}
