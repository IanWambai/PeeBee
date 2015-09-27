package com.makeshift.kuhustle.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.makeshift.kuhustle.R;
import com.makeshift.kuhustle.classes.FastBlur;
import com.makeshift.kuhustle.viewholders.FreelancerDrawerViewHolder;

/**
 * Created by Wednesday on 9/15/2015.
 */
public class FreelancerDrawerAdapter extends RecyclerView.Adapter<FreelancerDrawerViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private String mNavTitles[];
    private String mNavDescriptions[];
    private int mIcons[];

    private String name;
    private int profile;
    private String email;
    private Context context;


    public FreelancerDrawerAdapter(Context context, String titles[], String[] descriptions, int Icons[], String Name, String Email, int Profile) {
        mNavTitles = titles;
        mNavDescriptions = descriptions;
        mIcons = Icons;
        name = Name;
        email = Email;
        profile = Profile;
        this.context = context;
    }

    @Override
    public FreelancerDrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_row, parent, false);
            FreelancerDrawerViewHolder vhItem = new FreelancerDrawerViewHolder(v, viewType, context);
            return vhItem;

        } else if (viewType == TYPE_HEADER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_header, parent, false);
            FreelancerDrawerViewHolder vhHeader = new FreelancerDrawerViewHolder(v, viewType, context);
            return vhHeader;
        }
        return null;

    }

    @Override
    public void onBindViewHolder(FreelancerDrawerViewHolder holder, int position) {
        if (holder.Holderid == 1) {
            holder.title.setText(mNavTitles[position - 1]);
            holder.description.setText(mNavDescriptions[position - 1]);
            holder.ivIcon.setImageResource(mIcons[position - 1]);
        } else {
            blur(holder.contxt, profile, 25, holder.ivHeaderBackground);
            holder.ivProfilePicture.setImageResource(profile);
            holder.tvName.setText(name);
            holder.tvEmail.setText(email);
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

    public static void blur(Context context, int resourceId, int radius, ImageView view) {

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        float scaleFactor = 1;

        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        if (measuredWidth == 0 || measuredHeight == 0) {
            measuredWidth = 350;
            measuredHeight = 350;
        }

        Bitmap overlay = Bitmap.createBitmap((int) (measuredWidth / scaleFactor),
                (int) (measuredHeight / scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop() / scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        overlay = FastBlur.doBlur(overlay, (int) radius, true);
        view.setImageBitmap(overlay);
    }
}
