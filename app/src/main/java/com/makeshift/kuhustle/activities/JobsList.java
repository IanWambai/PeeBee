package com.makeshift.kuhustle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.makeshift.kuhustle.R;
import com.makeshift.kuhustle.adapters.JobsRecyclerViewAdapter;
import com.makeshift.kuhustle.classes.RecyclerItemClickListener;
import com.makeshift.kuhustle.constructors.JobListItem;

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
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_list);

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
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        
        final ArrayList<JobListItem> jobs = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            jobs.add(new JobListItem("Jon Snow", "You know nothing lorem ipsum dolor sit amet winter is coming", "Ksh. 25,000 - Ksh. 50,000", "6", "8", R.mipmap.ic_launcher));
        }

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorScheme(R.color.blue, R.color.purple, R.color.green, R.color.orange);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mAdapter = new JobsRecyclerViewAdapter(jobs);
        mRecyclerView.setAdapter(new SlideInBottomAnimationAdapter(mAdapter));
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getApplicationContext(), jobs.get(position).getJobTitle() + " " + jobs.get(position).getJobDescription(), Toast.LENGTH_SHORT).show();
                        i = new Intent(getApplicationContext(), JobView.class);
                        startActivity(i);
                    }
                })
        );
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
