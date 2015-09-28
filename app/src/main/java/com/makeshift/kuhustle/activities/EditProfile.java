package com.makeshift.kuhustle.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.makeshift.kuhustle.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wednesday on 9/14/2015.
 */
public class EditProfile extends AppCompatActivity {

    private EditText etFirstName, etSecondName, etBio, etPhoneNumber, etEmail, etSkills, etExperiences, etAvatar, etVerified, etEmailNotifications, etDateJoined;
    private SharedPreferences sp;
    private String firstName, secondName, bio, phoneNumber, email, skills, experiences, avatar, verified, emailNotifications, dateJoined;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        setUpToolbar();
//        getBundle();
        setUp();
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActivityInfo activityInfo = null;
        try {
            activityInfo = getPackageManager().getActivityInfo(
                    getComponentName(), PackageManager.GET_META_DATA);
            TextView tvToolBarText = (TextView) toolbar.findViewById(R.id.tvToolbarText);
            tvToolBarText.setText(activityInfo.loadLabel(getPackageManager())
                    .toString());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getBundle() {
        Bundle b = getIntent().getExtras();
//        id = b.getInt("id");
//        title = b.getString("title");
//        description = b.getString("description");
//        value = b.getString("value");
//        bids = b.getInt("bids");
//        time = b.getLong("time");
    }

    private void setUp() {
        sp = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etSecondName = (EditText) findViewById(R.id.etSecondName);
        etBio = (EditText) findViewById(R.id.etBio);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etSkills = (EditText) findViewById(R.id.etSkills);
        etExperiences = (EditText) findViewById(R.id.etExperiences);
        etAvatar = (EditText) findViewById(R.id.etAvatar);
        etVerified = (EditText) findViewById(R.id.etVerified);
        etEmailNotifications = (EditText) findViewById(R.id.etEmailNotifications);
        etDateJoined = (EditText) findViewById(R.id.etDateJoined);

    }

    class EditUserProfile extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
//            firstName, secondName, bio, phoneNumber, email, skills, experiences, avatar, verified, emailNotifications
            String firstName = params[0];
            String secondName = params[1];
            String bio = params[2];
            String phoneNumber = params[3];
            String email = params[4];
            String skills = params[5];
            String experiences = params[6];
            String avatar = params[7];
            String verified = params[8];
            String emailNotifications = params[9];


            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(getString(R.string.base_url) + "users/" + getString(R.string.test_username));
            httpPost.addHeader("Authorization", "Bearer " + sp.getString("accessToken", null));

            BasicNameValuePair firstNameBasicNameValuePair = new BasicNameValuePair("first_name", firstName);
            BasicNameValuePair secondNameBasicNameValuePair = new BasicNameValuePair("second_name", secondName);
            BasicNameValuePair bioBasicNameValuePair = new BasicNameValuePair("bio", bio);
            BasicNameValuePair phoneNumberBasicNameValuePair = new BasicNameValuePair("phone_number", phoneNumber);
            BasicNameValuePair emailBasicNameValuePair = new BasicNameValuePair("email", email);
            BasicNameValuePair skillsBasicNameValuePair = new BasicNameValuePair("skills", skills);
            BasicNameValuePair experiencesBasicNameValuePair = new BasicNameValuePair("experiences", experiences);
            BasicNameValuePair avatarBasicNameValuePair = new BasicNameValuePair("avatar", avatar);
            BasicNameValuePair verifiedBasicNameValuePair = new BasicNameValuePair("verified", verified);
            BasicNameValuePair emailNotificationsBasicNameValuePair = new BasicNameValuePair("email_notifications", emailNotifications);

            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
            nameValuePairList.add(firstNameBasicNameValuePair);
            nameValuePairList.add(secondNameBasicNameValuePair);
            nameValuePairList.add(bioBasicNameValuePair);
            nameValuePairList.add(phoneNumberBasicNameValuePair);
            nameValuePairList.add(emailBasicNameValuePair);
            nameValuePairList.add(skillsBasicNameValuePair);
            nameValuePairList.add(experiencesBasicNameValuePair);
            nameValuePairList.add(avatarBasicNameValuePair);
            nameValuePairList.add(verifiedBasicNameValuePair);
            nameValuePairList.add(emailNotificationsBasicNameValuePair);

            try {
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);
                httpPost.setEntity(urlEncodedFormEntity);

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

            } catch (UnsupportedEncodingException uee) {
                uee.printStackTrace();
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
                firstName = etFirstName.getText().toString().trim();
                secondName = etSecondName.getText().toString().trim();
                bio = etBio.getText().toString().trim();
                phoneNumber = etPhoneNumber.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                skills = etSkills.getText().toString().trim();
                experiences = etExperiences.getText().toString().trim();
                avatar = etAvatar.getText().toString().trim();
                verified = etVerified.getText().toString().trim();
                emailNotifications = etEmailNotifications.getText().toString().trim();
                dateJoined = etDateJoined.getText().toString().trim();

                new EditUserProfile().execute(firstName, secondName, bio, phoneNumber, email, skills, experiences, avatar, verified, emailNotifications);
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
