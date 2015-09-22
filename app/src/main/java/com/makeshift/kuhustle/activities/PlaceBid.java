package com.makeshift.kuhustle.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
public class PlaceBid extends AppCompatActivity {

    TextView tvTitle, tvDescription, tvValue, tvBids, tvTimeLeft;
    EditText etDescription, etAmount, etDuration;
    Spinner spDuration;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_bid);

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

        etDescription = (EditText) findViewById(R.id.etDescription);
        etAmount = (EditText) findViewById(R.id.etAmount);
        etDuration = (EditText) findViewById(R.id.etDuration);


        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(
                getApplicationContext(), R.layout.spinner_item,
                Arrays.asList(getResources().getStringArray(R.array.durations)));
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
                Toast.makeText(getApplicationContext(), "Placing bid..", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
