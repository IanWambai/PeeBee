package com.makeshift.kuhustle.viewholders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.makeshift.kuhustle.R;
import com.makeshift.kuhustle.activities.ClientMainActivity;
import com.makeshift.kuhustle.activities.ClientDashboard;
import com.makeshift.kuhustle.activities.FreelancerDashboard;
import com.makeshift.kuhustle.activities.JobsList;
import com.makeshift.kuhustle.activities.MainActivity;
import com.makeshift.kuhustle.activities.MessagesList;
import com.makeshift.kuhustle.activities.NotificationsList;
import com.makeshift.kuhustle.activities.Profile;

/**
 * Created by Wednesday on 9/15/2015.
 */
public class ClientDrawerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public int Holderid;

    public TextView title;
    public TextView description;
    public ImageView imageView;
    public ImageView profile;
    public TextView Name;
    public TextView email;
    Context contxt;
    Intent i;

    private static final int TYPE_ITEM = 1;


    public ClientDrawerViewHolder(View itemView, int ViewType, Context c) {
        super(itemView);
        contxt = c;
        itemView.setClickable(true);
        itemView.setOnClickListener(this);

        if (ViewType == TYPE_ITEM) {
            title = (TextView) itemView.findViewById(R.id.tvTitle);
            description = (TextView) itemView.findViewById(R.id.tvDescription);
            imageView = (ImageView) itemView.findViewById(R.id.ivIcon);
            Holderid = 1;
        } else {
            Name = (TextView) itemView.findViewById(R.id.name);
            email = (TextView) itemView.findViewById(R.id.email);
            profile = (ImageView) itemView.findViewById(R.id.circleView);
            Holderid = 0;
        }
    }

    @Override
    public void onClick(View v) {
        switch (getPosition()) {
            case 1:
                i = new Intent(contxt, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                contxt.startActivity(i);
                break;
            case 2:
                i = new Intent(contxt, JobsList.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle b = new Bundle();
                b.putInt("flag", contxt.getResources().getInteger(R.integer.jobs_posted));
                i.putExtras(b);
                contxt.startActivity(i);
                break;
            case 3:
                i = new Intent(contxt, MessagesList.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                contxt.startActivity(i);
                break;
            case 4:
                i = new Intent(contxt,NotificationsList.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                contxt.startActivity(i);
                break;
        }
    }
}
