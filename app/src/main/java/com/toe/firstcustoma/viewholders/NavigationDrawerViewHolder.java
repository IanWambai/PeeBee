package com.toe.firstcustoma.viewholders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.toe.firstcustoma.R;

/**
 * Created by Wednesday on 9/15/2015.
 */
public class NavigationDrawerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public int Holderid;

    public TextView title;
    public TextView description;
    public ImageView ivIcon, ivProfilePicture, ivHeaderBackground;
    public TextView tvName;
    public TextView tvEmail;
    public Context contxt;
    public Intent i;

    private static final int TYPE_ITEM = 1;

    public NavigationDrawerViewHolder(View itemView, int ViewType, Context c) {
        super(itemView);
        contxt = c;
        itemView.setClickable(true);
        itemView.setOnClickListener(this);

        if (ViewType == TYPE_ITEM) {
            title = (TextView) itemView.findViewById(R.id.tvTitle);
            description = (TextView) itemView.findViewById(R.id.tvDescription);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
            Holderid = 1;
        } else {
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
            ivProfilePicture = (ImageView) itemView.findViewById(R.id.ivProfilePicture);
            ivHeaderBackground = (ImageView) itemView.findViewById(R.id.ivHeaderBackground);
            Holderid = 0;
        }
    }

    @Override
    public void onClick(View v) {
        switch (getPosition()) {
//            case 1:
//                i = new Intent(contxt, Profile.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                contxt.startActivity(i);
//                break;
//            case 2:
//                i = new Intent(contxt, FreelancerDashboard.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                contxt.startActivity(i);
//                break;
//            case 3:
//                i = new Intent(contxt, ClientDashboard.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                contxt.startActivity(i);
//                break;
//            case 4:
//                i = new Intent(contxt, JobsList.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                Bundle b = new Bundle();
//                b.putInt("flag", contxt.getResources().getInteger(R.integer.jobs_won));
//                i.putExtras(b);
//                contxt.startActivity(i);
//                break;
//            case 5:
//                i = new Intent(contxt, MessagesList.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                contxt.startActivity(i);
//                break;
//            case 6:
//                i = new Intent(contxt, NotificationsList.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                contxt.startActivity(i);
//                break;
        }
    }
}
