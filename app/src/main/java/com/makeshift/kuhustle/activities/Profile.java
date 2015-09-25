package com.makeshift.kuhustle.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.makeshift.kuhustle.R;
import com.makeshift.kuhustle.adapters.CardViewAdapter;
import com.makeshift.kuhustle.constructors.CardItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
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

    private SharedPreferences sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        setUpToolBar();
        setUp();

        new GetProfile().execute("batman");
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

    class GetProfile extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String username = params[0];
            String response = null;

            try {
                HttpGet httpGet = new HttpGet(getString(R.string.base_url) + "users/" + username);

                httpGet.addHeader("Authorization", "Bearer " + sp.getString("accessToken", null));
                HttpClient httpClient = new DefaultHttpClient();
                HttpResponse httpResponse = httpClient.execute(httpGet);

                int status = httpResponse.getStatusLine().getStatusCode();
                
                HttpEntity entity = httpResponse.getEntity();
                response = EntityUtils.toString(entity);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //If the result is OK then:
//            i = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(i);
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
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
