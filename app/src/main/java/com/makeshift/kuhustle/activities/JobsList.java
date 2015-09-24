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

import jp.wasabeef.recyclerview.animators.adapters.SlideInBottomAnimationAdapter;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_list);

        setUpToolBar();
        setUp();

        FetchJobs fetch = new FetchJobs();
        fetch.jobState = "jobs/";
        fetch.execute();
    }

    private void setUpToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    private void setUp() {
        sp = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
    }

    class FetchJobs extends AsyncTask<String, Void, String> {

        ArrayList<JobListItem> jobs = new ArrayList<>();
        public String jobState;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
            mSwipeRefreshLayout.setColorScheme(R.color.blue, R.color.purple, R.color.green, R.color.orange);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                @Override
                public void onRefresh() {
                    FetchJobs fetch = new FetchJobs();
                    fetch.jobState = jobState;
                    fetch.execute();
                }
            });
        }

        @Override
        protected String doInBackground(String... params) {

            String data = null;

            try {
                HttpGet httpGet = new HttpGet(getString(R.string.base_url) + jobState);

                Log.d("GET REQUEST", "URL: " + getString(R.string.base_url) + jobState + " Header: " + "Bearer " + sp.getString("accessToken", null));

                httpGet.addHeader("Authorization", "Bearer " + sp.getString("accessToken", null));
                HttpClient httpClient = new DefaultHttpClient();
                HttpResponse response = httpClient.execute(httpGet);

                int status = response.getStatusLine().getStatusCode();

                Log.d("GET JOBS STATUS", String.valueOf(status));

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    data = EntityUtils.toString(entity);
                } else if (status == 401) {
                    data = "Sorry that access token is unauthorized. (Status 401)";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
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

                    jobs.add(new JobListItem(title, description, budget, deadline, String.valueOf(status), R.mipmap.ic_launcher));
                }

                mLayoutManager = new LinearLayoutManager(getApplicationContext());

                mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
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
