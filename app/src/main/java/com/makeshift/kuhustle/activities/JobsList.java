package com.makeshift.kuhustle.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.makeshift.kuhustle.R;
import com.makeshift.kuhustle.adapters.JobsRecyclerViewAdapter;
import com.makeshift.kuhustle.classes.RecyclerItemClickListener;
import com.makeshift.kuhustle.constructors.JobListItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import jp.wasabeef.recyclerview.animators.adapters.SlideInBottomAnimationAdapter;

import static com.makeshift.kuhustle.R.color;
import static com.makeshift.kuhustle.R.id;
import static com.makeshift.kuhustle.R.layout;
import static com.makeshift.kuhustle.R.mipmap;
import static com.makeshift.kuhustle.R.string;

/**
 * Created by Wednesday on 9/14/2015.
 */
public class JobsList extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Intent i;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SharedPreferences sp;
    private int FLAG;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.recycler_view_list);

        setUpToolBar();
        setUp();
        getBundle();
        fetchJobs();
    }

    private void setUpToolBar() {
        toolbar = (Toolbar) findViewById(id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    private void setUp() {
        sp = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
    }

    private void getBundle() {
        Bundle b = getIntent().getExtras();
        FLAG = b.getInt("flag");

        Toast.makeText(getApplicationContext(), String.valueOf(FLAG), Toast.LENGTH_SHORT).show();
    }

    private void fetchJobs() {
        FetchJobs fetch = new FetchJobs();
        String url = null;

        switch (FLAG) {
            case 0:
                //Jobs won
                Toast.makeText(getApplicationContext(), "Coming soon :-)", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                //Jobs posted

                break;
            case 2:
                //Jobs available

                break;
            case 3:
                //Jobs matching
                JSONArray skillsArray = new JSONArray();
                try {
                    JSONObject skill1 = new JSONObject().put("id", getString(R.string.base_url) + "skills/12/");
                    JSONObject skill2 = new JSONObject().put("id", getString(R.string.base_url) + "skills/30/");
                    JSONObject skill3 = new JSONObject().put("id", getString(R.string.base_url) + "skills/15/");

                    skillsArray.put(skill1);
                    skillsArray.put(skill2);
                    skillsArray.put(skill3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String skills = skillsArray.toString().replace("\\", "");

                url = "jobs/?skills_required=" + skills;
                break;
            case 4:
                //Jobs in progress

                break;
        }

        fetch.url = url;
        fetch.execute();
    }

    class FetchJobs extends AsyncTask<String, Void, String> {

        ArrayList<JobListItem> jobs = new ArrayList<>();
        public String url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();

            mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(id.swipeRefreshLayout);
            mSwipeRefreshLayout.setColorScheme(color.blue, color.purple, color.green, color.orange);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                @Override
                public void onRefresh() {
                    FetchJobs fetch = new FetchJobs();
                    fetch.url = url;
                    fetch.execute();
                }
            });
        }

        @Override
        protected String doInBackground(String... params) {
            String response = null;

            try {
                HttpGet httpGet = new HttpGet(getString(string.base_url) + url);

                Log.d("GET JOBS REQUEST", "URL: " + getString(string.base_url) + url + " Header: " + "Bearer " + sp.getString("accessToken", null));

                httpGet.addHeader("Authorization", "Bearer " + sp.getString("accessToken", null));
                HttpClient httpClient = new DefaultHttpClient();
                HttpResponse httpResponse = httpClient.execute(httpGet);

                int status = httpResponse.getStatusLine().getStatusCode();

                Log.d("GET JOBS STATUS", String.valueOf(status));

                HttpEntity entity = httpResponse.getEntity();

                if (status == 200) {
                    response = EntityUtils.toString(entity);
                } else if (status == 401) {
                    response = "Sorry that access token is unauthorized. (Status 401)";
                } else {
                    response = "Status: " + status + " Response: " + EntityUtils.toString(entity);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JSONObject resultObj = null;
            JSONObject jobObj = null;
            JSONArray results, skillsRequired;

            try {
                resultObj = new JSONObject(result);
                int count = resultObj.getInt("count");
                String next = resultObj.getString("next");
                String previous = resultObj.getString("previous");
                results = resultObj.getJSONArray("results");

                for (int i = 0; i < results.length(); i++) {
                    jobObj = new JSONObject(results.get(i).toString());
                    int id = jobObj.getInt("id");
                    String url = jobObj.getString("url");
                    String title = jobObj.getString("title");
                    String description = jobObj.getString("description");
                    boolean isOpen = jobObj.getBoolean("is_open");
                    int status = jobObj.getInt("status");
                    String bidEndDate = jobObj.getString("bid_end_date");
                    String deadline = jobObj.getString("deadline");
                    String selectedBid = jobObj.getString("selected_bid");
                    String client = jobObj.getString("client");
                    String category = jobObj.getString("category");
                    String budget = jobObj.getString("budget");
                    String statusDisplay = jobObj.getString("status_display");
                    boolean userHasBid = jobObj.getBoolean("user_has_bid");
                    boolean userIsClient = jobObj.getBoolean("user_is_client");
                    boolean isAcceptingBids = jobObj.getBoolean("is_accepting_bids");
                    skillsRequired = jobObj.getJSONArray("skills_required");

                    jobs.add(new JobListItem(id, title, description, budget, formatTime(bidEndDate), status, mipmap.ic_launcher));
                }

                mLayoutManager = new LinearLayoutManager(getApplicationContext());

                mRecyclerView = (RecyclerView) findViewById(id.recyclerView);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setHasFixedSize(true);

                mAdapter = new JobsRecyclerViewAdapter(jobs);
                mRecyclerView.setAdapter(new SlideInBottomAnimationAdapter(mAdapter));
                mRecyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(getApplicationContext(), jobs.get(position).getJobTitle() + " " + jobs.get(position).getJobDescription(), Toast.LENGTH_SHORT).show();
                                i = new Intent(getApplicationContext(), PlaceBid.class);
                                startActivity(i);
                            }
                        })
                );

                mSwipeRefreshLayout.setRefreshing(false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private Calendar formatTime(String rawTime) {
        // TODO Auto-generated method stub
        String spitDate[] = rawTime.split("T");
        String sDate = spitDate[0];
        String dateSplit[] = sDate.split("-");
        String sYear = dateSplit[0];
        String sMonth = dateSplit[1];
        String sDay = dateSplit[2];

        int month = Integer.parseInt(sMonth);
        int day = Integer.parseInt(sDay);

        String splitTime[] = rawTime.split(":");
        String rawHour = splitTime[0];
        String hour = rawHour.substring(rawHour.length() - 2, rawHour.length());
        String minute = splitTime[1];

        int hourInt = Integer.parseInt(hour);


        Date dateObj = new Date(Integer.parseInt(sYear.substring(2)) + 100,
                month - 1, day, hourInt, Integer.parseInt(minute));

        Calendar calender = Calendar.getInstance();
        calender.setTime(dateObj);

        return calender;
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
