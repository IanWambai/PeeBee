package com.makeshift.kuhustle.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
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

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Wednesday on 9/14/2015.
 */
public class PlaceBid extends AppCompatActivity {

    private TextView tvTitle, tvDescription, tvValue, tvBids, tvTimeLeft;
    private EditText etDescription, etAmount, etDuration;
    private Spinner spDuration;
    private SharedPreferences sp;
    private int id, bids;
    private String title, description, value;
    private long time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_bid);

        setUpToolbar();
        getBundle();
        setUp();
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

    private void getBundle() {
        Bundle b = getIntent().getExtras();
        id = b.getInt("id");
        title = b.getString("title");
        description = b.getString("description");
        value = b.getString("value");
        bids = b.getInt("bids");
        time = b.getLong("time");
    }

    private void setUp() {
        sp = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

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


        String budget = null;

        if (value.contains("1")) {
            budget = "Ksh.10,000 - Ksh.50,000";
        } else if (value.contains("2")) {
            budget = "Ksh.50,000 - Ksh.100,000";
        } else if (value.contains("3")) {
            budget = "Ksh.100,000 - Ksh.200,000";
        } else if (value.contains("4")) {
            budget = "Ksh.200,000 - Ksh.500,000";
        } else if (value.contains("5")) {
            budget = "Ksh.500,000 - Ksh.1,000,000";
        } else if (value.contains("6")) {
            budget = "Above Ksh.1,000,000";
        }

        tvTitle.setText(title);
        tvDescription.setText(description);
        tvValue.setText(budget);
        tvBids.setText("Number of bids made: " + bids);

        int daysToEnd = Days.daysBetween(new DateTime(), new DateTime(time)).getDays();
        if (daysToEnd > 0) {
            tvTimeLeft.setText(String.valueOf(daysToEnd) + " days left to bid");
        } else {
            tvTimeLeft.setText("Bidding is closed");
        }
    }

    class PostBid extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String amount = params[0];
            String duration = params[1];
            String proposal = params[2];

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(getString(R.string.base_url) + "jobs/" + String.valueOf(id) + "/bids/");
            httpPost.addHeader("Authorization", "Bearer " + sp.getString("accessToken", null));

            BasicNameValuePair amountBasicNameValuePair = new BasicNameValuePair("amount", amount);
            BasicNameValuePair durationBasicNameValuePair = new BasicNameValuePair("duration", duration);
            BasicNameValuePair proposalBasicNameValuePair = new BasicNameValuePair("proposal", proposal);

            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
            nameValuePairList.add(amountBasicNameValuePair);
            nameValuePairList.add(durationBasicNameValuePair);
            nameValuePairList.add(proposalBasicNameValuePair);

            try {
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);
                httpPost.setEntity(urlEncodedFormEntity);

                try {
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    InputStream inputStream = httpResponse.getEntity().getContent();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String bufferedStrChunk = null;

                    while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                        stringBuilder.append(bufferedStrChunk);
                    }

                    return stringBuilder.toString();

                } catch (ClientProtocolException cpe) {
                    cpe.printStackTrace();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }

            } catch (UnsupportedEncodingException uee) {
                uee.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
//            try {
//                JSONObject resultObj = new JSONObject(result);
//                String accessToken = resultObj.getString("access_token");
//                String tokenType = resultObj.getString("token_type");
//                String expiresIn = resultObj.getString("expires_in");
//                String refreshToken = resultObj.getString("refresh_token");
//                String scope = resultObj.getString("scope");
//
//
//                sp.edit().putString("refreshToken", refreshToken).apply();
//                sp.edit().putString("accessToken", accessToken).apply();
//
//                Log.d("ACCESS TOKEN", accessToken);
//
//                Toast.makeText(getApplicationContext(), "Development access token: " + accessToken, Toast.LENGTH_LONG).show();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        }
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
                String description = etDescription.getText().toString().trim();
                String amount = etAmount.getText().toString().trim();
                String duration = etDuration.getText().toString().trim();

                new PostBid().execute(amount, duration, description);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
