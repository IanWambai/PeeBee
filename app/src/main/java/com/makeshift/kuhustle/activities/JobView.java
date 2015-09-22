package com.makeshift.kuhustle.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.makeshift.kuhustle.R;
import com.makeshift.kuhustle.adapters.SpinnerAdapter;

import java.util.Arrays;

/**
 * Created by Wednesday on 9/14/2015.
 */
public class JobView extends AppCompatActivity {

    TextView tvTitle, tvDescription, tvValue, tvBids, tvTimeLeft;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_view);

        setUpToolbar();
        setUp();

    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    private void setUp() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvValue = (TextView) findViewById(R.id.tvValue);
        tvBids = (TextView) findViewById(R.id.tvBids);
        tvTimeLeft = (TextView) findViewById(R.id.tvTimeLeft);
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
}
