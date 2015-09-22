package com.makeshift.kuhustle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.makeshift.kuhustle.R;
import com.makeshift.kuhustle.adapters.FreelancerDrawerAdapter;
import com.makeshift.kuhustle.adapters.ViewPagerAdapter;
import com.makeshift.kuhustle.classes.DepthPageTransformer;

public class MainActivity extends AppCompatActivity {

    private ViewPagerAdapter mViewPagerAdapter;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private DrawerLayout drawerLayout;
    Intent i;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    int icons[] = {R.drawable.blur_black, R.drawable.blur_black, R.drawable.blur_black, R.drawable.blur_black, R.drawable.blur_black, R.drawable.blur_black};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);

        setUp();
    }

    private void setUp() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.pager);

        setSupportActionBar(toolbar);

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        tabLayout.setupWithViewPager(mViewPager);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new FreelancerDrawerAdapter(getApplicationContext(), getResources().getStringArray(R.array.freelancer_navigation), getResources().getStringArray(R.array.freelancer_navigation_descriptions), icons, getString(R.string.default_username), getString(R.string.default_email), R.drawable.profile_picture);
        mRecyclerView.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mHelp:
                Toast.makeText(getApplicationContext(), "Help", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mBrowser:
                Toast.makeText(getApplicationContext(), "Browser", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mAbout:
                Toast.makeText(getApplicationContext(), "About", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}