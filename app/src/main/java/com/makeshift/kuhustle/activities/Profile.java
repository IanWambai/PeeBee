package com.makeshift.kuhustle.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.makeshift.kuhustle.R;
import com.makeshift.kuhustle.adapters.CardViewAdapter;
import com.makeshift.kuhustle.constructors.CardItem;

import java.util.ArrayList;

/**
 * Created by Wednesday on 9/14/2015.
 */
public class Profile extends AppCompatActivity {

    private CollapsingToolbarLayout ctb;
    private int mutedColor;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ArrayList<CardItem> cards;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        setUpToolBar();
        setUp();
        populateCards();
    }

    private void setUpToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    private void setUp() {
        recyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ctb = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ctb.setTitle("Username");

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

            @Override
            public void onGenerated(Palette palette) {
                mutedColor = palette.getMutedColor(R.attr.colorPrimary);
                ctb.setContentScrimColor(mutedColor);
            }
        });
    }

    private void populateCards() {
        cards = new ArrayList<>();
        cards.add(new CardItem("Bio", "Lorem ipsuum dolor sit amet Lorem ipsuum dolor sit amet Lorem ipsuum dolor sit ametLorem ipsuum dolor sit ametLorem ipsuum dolor sit ametLorem ipsuum dolor sit ametLorem ipsuum dolor sit amet"));
        cards.add(new CardItem("Skills", "Lorem ipsuum dolor sit ametLorem ipsuum dolor sit ametLorem ipsuum dolor sit ametLorem ipsuum dolor sit amet"));
        cards.add(new CardItem("Blah", "Lorem ipsuum dolor sit ametLorem ipsuum dolor sit amet"));
        cards.add(new CardItem("Fleek", "Lorem ipsuum dolor sit ametLorem ipsuum dolor sit ametLorem ipsuum dolor sit ametLorem ipsuum dolor sit amet"));

        CardViewAdapter adapter = new CardViewAdapter(cards);
        recyclerView.setAdapter(adapter);
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
