package com.makeshift.kuhustle.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
public class PlaceJob extends AppCompatActivity {

    private EditText etJobTitle, etDescription;
    private Spinner spDuration;

    private SharedPreferences sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_job);

        setUpToolbar();
        setUp();

    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(226, 141, 8)));
    }

    private void setUp() {
        sp = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        etJobTitle = (EditText) findViewById(R.id.etJobTitle);
        etDescription = (EditText) findViewById(R.id.etDescription);


        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(
                getApplicationContext(), R.layout.spinner_item,
                Arrays.asList(getResources().getStringArray(R.array.price_ranges)));
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spDuration = (Spinner) findViewById(R.id.spDuration);
        spDuration.setAdapter(spinnerAdapter);

    }

    class PostJob extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String title = params[0];
            String description = params[1];
            String status = params[2];
            String categoryId = params[3];
            String budgetId = params[4];
            String skills = params[5];

            HttpClient httpClient = new DefaultHttpClient();
//            HttpPost httpPost = new HttpPost(getString(R.string.base_url) + "jobs/");

            String url = "http://neptune.kuhustle.com/api/v1/jobs?\"title\"=\"title\"&\"description\"=\"description\"&\"status\"=\"1\"&\"category\"=\"http://neptune.kuhustle.com/api/v1/categories/1\"&\"budget\"=\"http://neptune.kuhustle.com/api/v1/categories/1\"&\"skills_required\"=[{\"id\":\"http://neptune.kuhustle.com/api/v1/skills/12/\"},{\"id\":\"http://neptune.kuhustle.com/api/v1/skills/12/\"},{\"id\":\"http://neptune.kuhustle.com/api/v1/skills/12/\"}]";

            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Authorization", "Bearer " + sp.getString("accessToken", null));

//            BasicNameValuePair titleBasicNameValuePair = new BasicNameValuePair("title", title);
//            BasicNameValuePair descriptionBasicNameValuePair = new BasicNameValuePair("description", description);
//            BasicNameValuePair statusBasicNameValuePair = new BasicNameValuePair("status", status);
//            BasicNameValuePair categoryIdBasicNameValuePair = new BasicNameValuePair("category", categoryId);
//            BasicNameValuePair budgetIdBasicNameValuePair = new BasicNameValuePair("budget", budgetId);
//            BasicNameValuePair skillsBasicNameValuePair = new BasicNameValuePair("skills_required", skills);
//
//            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
//            nameValuePairList.add(titleBasicNameValuePair);
//            nameValuePairList.add(descriptionBasicNameValuePair);
//            nameValuePairList.add(statusBasicNameValuePair);
//            nameValuePairList.add(categoryIdBasicNameValuePair);
//            nameValuePairList.add(budgetIdBasicNameValuePair);
//            nameValuePairList.add(skillsBasicNameValuePair);

            //                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);
//                httpPost.setEntity(urlEncodedFormEntity);

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
                String title = etJobTitle.getText().toString().trim();
                String description = etDescription.getText().toString().trim();
                String status = "1";
                String categoryId = getString(R.string.base_url) + "categories/1/";
                String budgetId = getString(R.string.base_url) + "budgets/1/";
                JSONArray skills = new JSONArray();

                try {
                    JSONObject skill1 = new JSONObject().put("id", getString(R.string.base_url) + "skills/12/");
                    JSONObject skill2 = new JSONObject().put("id", getString(R.string.base_url) + "skills/30/");
                    JSONObject skill3 = new JSONObject().put("id", getString(R.string.base_url) + "skills/15/");

                    skills.put(skill1);
                    skills.put(skill2);
                    skills.put(skill3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String skillsString = skills.toString().replace("\\", "");

                Log.d("SKILLS JSON", skillsString);

                new PostJob().execute(title, description, status, categoryId, budgetId, skillsString);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
