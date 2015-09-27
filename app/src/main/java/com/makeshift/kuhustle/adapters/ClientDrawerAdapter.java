package com.makeshift.kuhustle.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeshift.kuhustle.R;
import com.makeshift.kuhustle.viewholders.ClientDrawerViewHolder;

/**
 * Created by Wednesday on 9/15/2015.
 */
public class ClientDrawerAdapter extends RecyclerView.Adapter<ClientDrawerViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private String mNavTitles[];
    private String mNavDescriptions[];
    private int mIcons[];

    private String name;
    private int profile;
    private String email;
    private Context context;


    public ClientDrawerAdapter(Context context, String titles[], String[] descriptions, int Icons[], String Name, String Email, int Profile) {
        mNavTitles = titles;
        mNavDescriptions = descriptions;
        mIcons = Icons;
        name = Name;
        email = Email;
        profile = Profile;
        this.context = context;
    }

    @Override
    public ClientDrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_row, parent, false);
            ClientDrawerViewHolder vhItem = new ClientDrawerViewHolder(v, viewType, context);
            return vhItem;

        } else if (viewType == TYPE_HEADER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_header, parent, false);
            ClientDrawerViewHolder vhHeader = new ClientDrawerViewHolder(v, viewType, context);
            return vhHeader;
        }
        return null;

    }

    @Override
    public void onBindViewHolder(ClientDrawerViewHolder holder, int position) {
        if (holder.Holderid == 1) {
            holder.title.setText(mNavTitles[position - 1]);
            holder.description.setText(mNavDescriptions[position - 1]);
            holder.imageView.setImageResource(mIcons[position - 1]);
        } else {
            holder.profile.setImageResource(profile);
            holder.Name.setText(name);
            holder.email.setText(email);
        }
    }

    @Override
    public int getItemCount() {
        return mNavTitles.length + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }
}
