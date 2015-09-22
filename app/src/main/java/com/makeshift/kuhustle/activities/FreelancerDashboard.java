package com.makeshift.kuhustle.activities;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
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
    private TextView tvJobsAvailable, tvJobsMatching, tvJobsInProgress, tvJobsAwarded, tvAmountDue;
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
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    private void setUp() {
        tvJobsAvailable = (TextView) findViewById(R.id.tvJobsAvailable);
        tvJobsMatching = (TextView) findViewById(R.id.tvJobsMatching);
        tvJobsInProgress = (TextView) findViewById(R.id.tvJobsInProgress);
        tvJobsAwarded = (TextView) findViewById(R.id.tvJobsAwarded);
        tvAmountDue = (TextView) findViewById(R.id.tvAmountDue);

        tvJobsAvailable.setOnClickListener(this);
        tvJobsMatching.setOnClickListener(this);
        tvJobsInProgress.setOnClickListener(this);
        tvJobsAwarded.setOnClickListener(this);
        tvAmountDue.setOnClickListener(this);

        countUp(tvJobsAvailable, 34);
        countUp(tvJobsMatching, 13);
        countUp(tvJobsInProgress, 23);
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
        switch (v.getId()) {
            case R.id.tvJobsAvailable:
                i = new Intent(getApplicationContext(), JobsList.class);
                startActivity(i);
                break;
            case R.id.tvJobsMatching:
                i = new Intent(getApplicationContext(), JobsList.class);
                startActivity(i);
                break;
            case R.id.tvJobsInProgress:
                i = new Intent(getApplicationContext(), JobsList.class);
                startActivity(i);
                break;
            case R.id.tvJobsAwarded:
                i = new Intent(getApplicationContext(), JobsList.class);
                startActivity(i);
                break;
            case R.id.tvAmountDue:

                break;
        }
    }
}
