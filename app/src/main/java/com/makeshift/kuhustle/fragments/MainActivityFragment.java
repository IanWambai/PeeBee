package com.makeshift.kuhustle.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.makeshift.kuhustle.R;
import com.makeshift.kuhustle.activities.PlaceBid;
import com.makeshift.kuhustle.adapters.JobsRecyclerViewAdapter;
import com.makeshift.kuhustle.classes.RecyclerItemClickListener;
import com.makeshift.kuhustle.constructors.JobListItem;

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
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.recycler_view, container, false);
        Bundle args = getArguments();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);


        switch (args.getInt(ARG_OBJECT)) {
            case 1:
                final ArrayList<JobListItem> allJobs = new ArrayList<>();

                for (int i = 0; i < 100; i++) {
                    allJobs.add(new JobListItem("Jon Snow", "You know nothing lorem ipsum dolor sit amet winter is coming", "Ksh. 25,000 - Ksh. 50,000", "6", "8", R.mipmap.ic_launcher));
                }


                mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
                mSwipeRefreshLayout.setColorScheme(R.color.blue, R.color.purple, R.color.green, R.color.orange);
                mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // Refresh items
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });

                mAdapter = new JobsRecyclerViewAdapter(allJobs);
                mRecyclerView.setAdapter(new SlideInBottomAnimationAdapter(mAdapter));
                mRecyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(getActivity().getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(getActivity().getApplicationContext(), allJobs.get(position).getJobTitle() + " " + allJobs.get(position).getJobDescription(), Toast.LENGTH_SHORT).show();

                                i = new Intent(getActivity().getApplicationContext(), PlaceBid.class);
                                startActivity(i);
                            }
                        })
                );
                break;
            case 2:
                final ArrayList<JobListItem> openJobs = new ArrayList<>();

                for (int i = 0; i < 100; i++) {
                    openJobs.add(new JobListItem("Jon Snow", "You know nothing lorem ipsum dolor sit amet winter is coming", "Ksh. 25,000 - Ksh. 50,000", "6", "8", R.mipmap.ic_launcher));
                }

                mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
                mSwipeRefreshLayout.setColorScheme(R.color.blue, R.color.purple, R.color.green, R.color.orange);
                mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // Refresh items
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });

                mAdapter = new JobsRecyclerViewAdapter(openJobs);
                mRecyclerView.setAdapter(new SlideInBottomAnimationAdapter(mAdapter));
                mRecyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(getActivity().getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(getActivity().getApplicationContext(), openJobs.get(position).getJobTitle() + " " + openJobs.get(position).getJobDescription(), Toast.LENGTH_SHORT).show();

                                i = new Intent(getActivity().getApplicationContext(), PlaceBid.class);
                                startActivity(i);
                            }
                        })
                );
                break;
            case 3:
                final ArrayList<JobListItem> wonJobs = new ArrayList<>();

                for (int i = 0; i < 100; i++) {
                    wonJobs.add(new JobListItem("Jon Snow", "You know nothing lorem ipsum dolor sit amet winter is coming", "Ksh. 25,000 - Ksh. 50,000", "6", "8", R.mipmap.ic_launcher));
                }

                mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
                mSwipeRefreshLayout.setColorScheme(R.color.blue, R.color.purple, R.color.green, R.color.orange);
                mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // Refresh items
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });

                mAdapter = new JobsRecyclerViewAdapter(wonJobs);
                mRecyclerView.setAdapter(new SlideInBottomAnimationAdapter(mAdapter));
                mRecyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(getActivity().getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(getActivity().getApplicationContext(), wonJobs.get(position).getJobTitle() + " " + wonJobs.get(position).getJobDescription(), Toast.LENGTH_SHORT).show();

                                i = new Intent(getActivity().getApplicationContext(), PlaceBid.class);
                                startActivity(i);
                            }
                        })
                );
                break;
            case 4:
                final ArrayList<JobListItem> closedJobs = new ArrayList<>();

                for (int i = 0; i < 100; i++) {
                    closedJobs.add(new JobListItem("Jon Snow", "You know nothing lorem ipsum dolor sit amet winter is coming", "Ksh. 25,000 - Ksh. 50,000", "6", "8", R.mipmap.ic_launcher));
                }

                mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
                mSwipeRefreshLayout.setColorScheme(R.color.blue, R.color.purple, R.color.green, R.color.orange);
                mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // Refresh items
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });

                mAdapter = new JobsRecyclerViewAdapter(closedJobs);
                mRecyclerView.setAdapter(new SlideInBottomAnimationAdapter(mAdapter));
                mRecyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(getActivity().getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(getActivity().getApplicationContext(), closedJobs.get(position).getJobTitle() + " " + closedJobs.get(position).getJobDescription(), Toast.LENGTH_SHORT).show();

                                i = new Intent(getActivity().getApplicationContext(), PlaceBid.class);
                                startActivity(i);
                            }
                        })
                );
                break;
        }

        return rootView;
    }
}
