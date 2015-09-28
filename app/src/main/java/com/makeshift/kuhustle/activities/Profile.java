package com.makeshift.kuhustle.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.makeshift.kuhustle.R;
import com.makeshift.kuhustle.adapters.GridItemAdapter;
import com.makeshift.kuhustle.classes.ExpandableGridView;
import com.makeshift.kuhustle.constructors.SkillListItem;

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

/**
 * Created by Wednesday on 9/14/2015.
 */
public class Profile extends AppCompatActivity {

    private CollapsingToolbarLayout ctb;
    private int mutedColor;
    private Toolbar toolbar;
    private SharedPreferences sp;
    private Intent i;
    private String username;
    private TextView tvBio, tvExperiences, tvVerified;
    private GridItemAdapter skillsAdapter;
    private ArrayList<SkillListItem> skillsList;
    private ExpandableGridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        username = "medhurst_lew";

        setUpToolBar();
        setUp();

        new GetProfile().execute(username);
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

        ctb = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ctb.setTitle(username);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

            @Override
            public void onGenerated(Palette palette) {
                mutedColor = palette.getMutedColor(R.attr.colorPrimary);
                ctb.setContentScrimColor(mutedColor);
            }
        });

        tvBio = (TextView) findViewById(R.id.tvBio);
        tvExperiences = (TextView) findViewById(R.id.tvExperiences);
        tvVerified = (TextView) findViewById(R.id.tvVerified);
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

                if (status == 200) {
                    response = EntityUtils.toString(entity);
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
            try {
                JSONObject resultObj = new JSONObject(result);
                String username = resultObj.getString("username");
                String url = resultObj.getString("url");
                String avatar = resultObj.getString("avatar");
                String bio = resultObj.getString("bio");
                String verified = resultObj.getString("verified");
                String skills = resultObj.getString("skills");
                String experiences = resultObj.getString("experiences");

                populateCards(username, url, avatar, bio, verified, skills, experiences);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void populateCards(String username, String url, String avatar, String bio, String verified, String skills, String experiences) {
        skillsList = new ArrayList<>();

        try {
            JSONArray skillsArray = new JSONArray(skills);

            for (int i = 0; i < skillsArray.length(); i++) {
                String id = ((JSONObject) skillsArray.get(i)).getString("id");
                String category = ((JSONObject) skillsArray.get(i)).getString("category");
                String title = ((JSONObject) skillsArray.get(i)).getString("title");
                String categoryUrl = ((JSONObject) skillsArray.get(i)).getString("url");

                skillsList
                        .add(new SkillListItem(title, R.drawable.profile_picture));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        gridView = (ExpandableGridView) findViewById(R.id.gvSkills);
        gridView.setExpanded(true);
        skillsAdapter = new GridItemAdapter(getApplicationContext(),
                R.layout.grid_layout, skillsList);
        gridView.setAdapter(skillsAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "fsf", Toast.LENGTH_SHORT).show();
            }
        });


        tvBio.setText(bio);
        tvExperiences.setText(experiences);

        if (verified == "false") {
            tvVerified.setText("Your Kuhustle account has not been verified");
        } else {
            tvVerified.setText("Your account has been verified");
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

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
