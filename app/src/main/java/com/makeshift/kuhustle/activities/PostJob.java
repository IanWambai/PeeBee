package com.makeshift.kuhustle.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
public class PostJob extends AppCompatActivity {

    TextView tvTitle, tvDescription, tvValue, tvBids, tvTimeLeft;
    EditText etDescription,etDuration;
    Spinner spDuration;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_job);

        setUpToolbar();
        setUp();

    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(226, 141, 8)));
    }

    private void setUp() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvValue = (TextView) findViewById(R.id.tvValue);
        tvBids = (TextView) findViewById(R.id.tvBids);
        tvTimeLeft = (TextView) findViewById(R.id.tvTimeLeft);

        etDescription = (EditText) findViewById(R.id.etDescription);
        etDuration = (EditText) findViewById(R.id.etDuration);


        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(
                getApplicationContext(), R.layout.spinner_item,
                Arrays.asList(getResources().getStringArray(R.array.price_ranges)));
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spDuration = (Spinner) findViewById(R.id.spDuration);
        spDuration.setAdapter(spinnerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.place_bid_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.mBid:
                Toast.makeText(getApplicationContext(), "Posting job...", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
