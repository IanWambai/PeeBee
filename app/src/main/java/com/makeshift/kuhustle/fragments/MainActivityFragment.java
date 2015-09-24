package com.makeshift.kuhustle.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.makeshift.kuhustle.R;
import com.makeshift.kuhustle.activities.PlaceBid;
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
public class MainActivityFragment extends Fragment {

    public static final String ARG_OBJECT = "object";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Intent i;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SharedPreferences sp;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        setUp();

        return populatePages(inflater, container);
    }

    private void setUp() {
        sp = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
    }

    private View populatePages(LayoutInflater inflater, ViewGroup container) {

        Bundle args = getArguments();

        rootView = inflater.inflate(
                R.layout.recycler_view, container, false);

        String jobState = null;

        switch (args.getInt(ARG_OBJECT)) {
            case 1:
                jobState = "jobs/";
                break;
            case 2:
                jobState = "jobs/?status=open";
                break;
            case 3:
                jobState = "jobs/?status=won";
                break;
            case 4:
                jobState = "jobs/?status=closed";
                break;
        }

        FetchJobs fetch = new FetchJobs();
        fetch.position = args.getInt(ARG_OBJECT);
        fetch.jobState = jobState;
        fetch.execute();

        return rootView;

    }

    class FetchJobs extends AsyncTask<String, Void, String> {

        ArrayList<JobListItem> jobs = new ArrayList<>();
        public int position;
        public String jobState;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            mSwipeRefreshLayout.setColorScheme(R.color.blue, R.color.purple, R.color.green, R.color.orange);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                @Override
                public void onRefresh() {
                    switch (position) {
                        case 1:
                            jobState = "jobs/";
                            break;
                        case 2:
                            jobState = "jobs/?status=open";
                            break;
                        case 3:
                            jobState = "jobs/?status=won";
                            break;
                        case 4:
                            jobState = "jobs/?status=closed";
                            break;
                    }

                    FetchJobs fetch = new FetchJobs();
                    fetch.position = position;
                    fetch.execute(jobState);
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

                    jobs.add(new JobListItem(id, title, description, budget, deadline, String.valueOf(status), R.mipmap.ic_launcher));
                }

                mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

                mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setHasFixedSize(true);

                mAdapter = new JobsRecyclerViewAdapter(jobs);
                mRecyclerView.setAdapter(new SlideInBottomAnimationAdapter(mAdapter));
                mRecyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(getActivity().getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                i = new Intent(getActivity().getApplicationContext(), PlaceBid.class);
                                Bundle b = new Bundle();
                                b.putInt("id", jobs.get(position).getId());
                                i.putExtras(b);
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
}
