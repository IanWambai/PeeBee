package com.makeshift.kuhustle.activities;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.makeshift.kuhustle.R;

import java.text.DecimalFormat;

/**
 * Created by Wednesday on 9/14/2015.
 */
public class FreelancerDashboard extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextView tvJobsAvailable, tvJobsMatching, tvJobsBiddedFor, tvJobsAwarded, tvAmountDue;
    Intent i;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.freelancer_dashboard);

        setUpToolBar();
        setUp();
    }

    private void setUpToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActivityInfo activityInfo = null;
        try {
            activityInfo = getPackageManager().getActivityInfo(
                    getComponentName(), PackageManager.GET_META_DATA);
            TextView tvToolBarText = (TextView) toolbar.findViewById(R.id.tvToolbarText);
            tvToolBarText.setText(activityInfo.loadLabel(getPackageManager())
                    .toString());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setUp() {
        tvJobsAvailable = (TextView) findViewById(R.id.tvJobsAvailable);
        tvJobsMatching = (TextView) findViewById(R.id.tvJobsMatching);
        tvJobsBiddedFor = (TextView) findViewById(R.id.tvJobsBiddedFor);
        tvJobsAwarded = (TextView) findViewById(R.id.tvJobsAwarded);
        tvAmountDue = (TextView) findViewById(R.id.tvAmountDue);

        tvJobsAvailable.setOnClickListener(this);
        tvJobsMatching.setOnClickListener(this);
        tvJobsBiddedFor.setOnClickListener(this);
        tvJobsAwarded.setOnClickListener(this);
        tvAmountDue.setOnClickListener(this);

        countUp(tvJobsAvailable, 34);
        countUp(tvJobsMatching, 13);
        countUp(tvJobsBiddedFor, 23);
        countUp(tvJobsAwarded, 10);
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
        Bundle b = new Bundle();

        switch (v.getId()) {
            case R.id.tvJobsAvailable:
                i = new Intent(getApplicationContext(), JobsList.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                b.putInt("flag", getResources().getInteger(R.integer.jobs_available));
                i.putExtras(b);
                startActivity(i);
                break;
            case R.id.tvJobsMatching:
                i = new Intent(getApplicationContext(), JobsList.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                b.putInt("flag", getResources().getInteger(R.integer.jobs_matching));
                i.putExtras(b);
                startActivity(i);
                break;
            case R.id.tvJobsBiddedFor:
                i = new Intent(getApplicationContext(), JobsList.class);
                b.putInt("flag", getResources().getInteger(R.integer.jobs_bidded));
                i.putExtras(b);
                startActivity(i);
                break;
            case R.id.tvJobsAwarded:
                i = new Intent(getApplicationContext(), JobsList.class);
                b.putInt("flag", getResources().getInteger(R.integer.jobs_won));
                i.putExtras(b);
                startActivity(i);
                break;
            case R.id.tvAmountDue:

                break;
        }
    }
}
