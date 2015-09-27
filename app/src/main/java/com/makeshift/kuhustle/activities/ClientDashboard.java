package com.makeshift.kuhustle.activities;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.makeshift.kuhustle.R;
import com.makeshift.kuhustle.adapters.ClientDrawerAdapter;

import java.text.DecimalFormat;

/**
 * Created by Wednesday on 9/14/2015.
 */
public class ClientDashboard extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextView tvBids, tvMilestonesOngoing, tvMilestonesCompleted, tvJobs, tvAmountDue;
    FloatingActionButton fab;
    private DrawerLayout drawerLayout;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    Intent i;

    int icons[] = {R.mipmap.ic_switch, R.mipmap.ic_work, R.mipmap.ic_chat ,R.mipmap.ic_notification};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_dashboard);

        setUpToolBar();
        setUp();
    }

    private void setUpToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(226, 141, 8)));
    }

    private void setUp() {
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new ClientDrawerAdapter(getApplicationContext(), getResources().getStringArray(R.array.client_navigation), getResources().getStringArray(R.array.client_navigation_descriptions), icons, getString(R.string.default_username), getString(R.string.default_email), R.drawable.profile_picture);
        mRecyclerView.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(getApplicationContext(), PlaceJob.class);
                startActivity(i);
            }
        });

        tvBids = (TextView) findViewById(R.id.tvBids);
        tvMilestonesOngoing = (TextView) findViewById(R.id.tvMilestonesOngoing);
        tvMilestonesCompleted = (TextView) findViewById(R.id.tvMilestonesCompleted);
        tvJobs = (TextView) findViewById(R.id.tvJobs);
        tvAmountDue = (TextView) findViewById(R.id.tvAmountDue);

        tvBids.setOnClickListener(this);
        tvMilestonesOngoing.setOnClickListener(this);
        tvMilestonesCompleted.setOnClickListener(this);
        tvJobs.setOnClickListener(this);
        tvAmountDue.setOnClickListener(this);

        countUp(tvBids, 34);
        countUp(tvMilestonesOngoing, 13);
        countUp(tvMilestonesCompleted, 23);
        countUp(tvJobs, 10);
        countUp(tvAmountDue, 234000);
    }


    private void countUp(final TextView view, int number) {
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, number);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                DecimalFormat df = new DecimalFormat("###,###,###.##");
                if (view.getId() == R.id.tvAmountDue) {
                    view.setText("Ksh." + df.format(animation.getAnimatedValue()));
                } else {
                    view.setText(df.format(animation.getAnimatedValue()));
                }
            }
        });
        animator.setEvaluator(new TypeEvaluator<Integer>() {

            @Override
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                return Math.round(startValue + (endValue - startValue) * fraction);
            }
        });
        animator.setDuration(2000);
        animator.start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvBids:
                i = new Intent(getApplicationContext(), BidsList.class);
                startActivity(i);
                break;
            case R.id.tvMilestonesOngoing:
                i = new Intent(getApplicationContext(), MilestonesList.class);
                startActivity(i);
                break;
            case R.id.tvMilestonesCompleted:
                i = new Intent(getApplicationContext(), MilestonesList.class);
                startActivity(i);
                break;
            case R.id.tvJobs:
                i = new Intent(getApplicationContext(), JobsList.class);
                startActivity(i);
                break;
            case R.id.tvAmountDue:

                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }
}
