package com.makeshift.kuhustle.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.makeshift.kuhustle.R;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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

import io.fabric.sdk.android.Fabric;

/**
 * Created by Wednesday on 9/14/2015.
 */
public class SignIn extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 0;

    private SignInButton bGoogleLogin;
    private LoginButton bFacebookLogin;
    private TwitterLoginButton bTwitterLogin;

    private GoogleApiClient mGoogleApiClient;
    private CallbackManager callbackManager;
    private ConnectionResult mConnectionResult;

    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private boolean isSignedInToGPlus;
    private boolean hasLoggedIn;

    private String googleToken, facebookToken, twitterToken;

    private ProgressDialog progressDialog;
    private SharedPreferences sp;
    private Intent i;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(getString(R.string.twitter_key), getString(R.string.twitter_secret));
        Fabric.with(this, new Twitter(authConfig));
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.sign_in);

        setUpToolbar();
        setUp();
        checkSignIn();
    }

    private void checkSignIn() {
        // TODO Auto-generated method stub
        hasLoggedIn = sp.getBoolean("hasLoggedIn", false);
        if (false) {
            i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        } else {
            new GetDevelopmentToken().execute();
            setUpGooglePlus();
            setUpFacebook();
            setUpTwitter();
        }
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    private void setUp() {
        sp = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(this);
    }

    private void setUpGooglePlus() {
        // TODO Auto-generated method stub
        bGoogleLogin = (SignInButton) findViewById(R.id.bGoogleLogin);
        bGoogleLogin.setOnClickListener(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
    }

    private void setUpFacebook() {
        callbackManager = CallbackManager.Factory.create();
        bFacebookLogin = (LoginButton) findViewById(R.id.bFacebookLogin);
        bFacebookLogin.setReadPermissions(Arrays.asList("public_profile", "email"));
        bFacebookLogin.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        facebookToken = loginResult.getAccessToken().getToken();

                        progressDialog.setMessage("Getting your Facebook information");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        requestFacebookData();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(SignIn.this, "Login cancelled by user!", Toast.LENGTH_LONG).show();
                        System.out.println("Login cancelled by user!");

                    }

                    @Override
                    public void onError(FacebookException e) {
                        Toast.makeText(SignIn.this, "Login unsuccessful!", Toast.LENGTH_LONG).show();
                        System.out.println("Login unsuccessful!");
                    }
                });
    }

    private void setUpTwitter() {
        bTwitterLogin = (TwitterLoginButton) findViewById(R.id.bTwitterLogin);
        bTwitterLogin.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                progressDialog.setMessage("Getting your Twitter information");
                progressDialog.setCancelable(false);
                progressDialog.show();

                final TwitterSession session = Twitter.getSessionManager().getActiveSession();
                Twitter.getApiClient(session).getAccountService()
                        .verifyCredentials(true, false, new Callback<User>() {
                            @Override
                            public void success(final Result<User> userResult) {
                                Log.d("User", "profile : " + userResult.data.url + "Name: " + userResult.data.name + "profile pic: " + userResult.data.profileImageUrl + "Screen Name: " + userResult.data.screenName);
                                twitterToken = session.getAuthToken().token;
                                authenticateUser("twitter", twitterToken, userResult.data.name, String.valueOf(userResult.data.id), userResult.data.profileImageUrl, userResult.data.url);
                            }

                            @Override
                            public void failure(final TwitterException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Getting Twitter credentials failed with error: " + e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        });
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(getApplicationContext(), "Twitter login failed with error: " + exception.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void requestFacebookData() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                String name = null, uniqueId = null, pictureUri = null, profileLink = null;
                try {
                    JSONObject json = response.getJSONObject();
                    json.getString("name");
                    if (json != null) {
                        Log.d("JSON DATA", json.toString());
                        name = json.getString("name");
                        uniqueId = json.getString("id");
                        profileLink = json.getString("link");
                        pictureUri = ((JSONObject) ((JSONObject) json.get("picture")).get("data")).getString("url");
                    }

                    authenticateUser("facebook", facebookToken, name, uniqueId, pictureUri, profileLink);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }


    protected void onStart() {
        super.onStart();
        if (!hasLoggedIn) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(SignIn.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(SignIn.this);
    }

    protected void onStop() {
        super.onStop();
        if (!hasLoggedIn) {
            if (mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
            }
        }
    }

    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            mConnectionResult = result;

            if (mSignInClicked) {
                resolveSignInError();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }

        callbackManager.onActivityResult(requestCode, resultCode, data);
        bTwitterLogin.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConnected(Bundle arg0) {
        mSignInClicked = false;
        updateUI(true);
    }

    private void updateUI(boolean isSignedIn) {
        isSignedInToGPlus = isSignedIn;

        if (isSignedIn) {
            setGooglePlusButtonText(bGoogleLogin, "Sign out");
            new GetGoogleProfileInformation().execute();
        } else {
            setGooglePlusButtonText(bGoogleLogin, "Sign in");
        }
    }

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
        }
    }

    private class GetGoogleProfileInformation extends AsyncTask<Void, Void, String> {

        private String personName;
        private String personPhotoUrl;
        private String personGooglePlusProfile;
        private String email;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Getting your Google+ information");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                    Person currentPerson = Plus.PeopleApi
                            .getCurrentPerson(mGoogleApiClient);
                    personName = currentPerson.getDisplayName();
                    personPhotoUrl = currentPerson.getImage().getUrl();
                    personGooglePlusProfile = currentPerson.getUrl();
                    email = Plus.AccountApi.getAccountName(mGoogleApiClient);


                    try {
                        googleToken = GoogleAuthUtil.getToken(getApplicationContext(), email, "oauth2:" + getString(R.string.gplus_scope));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (GoogleAuthException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Person information is null", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),
                        "Error: " + e.toString(), Toast.LENGTH_LONG).show();
            }
            return googleToken;
        }

        @Override
        protected void onPostExecute(String token) {
            authenticateUser("google-oauth2", googleToken, personName, email, personPhotoUrl, personGooglePlusProfile);
        }
    }


    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
        updateUI(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bGoogleLogin:
                if (!isSignedInToGPlus) {
                    signInWithGplus();
                } else {
                    signOutFromGplus();
                }
                break;
        }
    }

    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            if (mConnectionResult != null) {
                resolveSignInError();
            } else {
                signOutFromGplus();
            }
        }
    }

    private void signOutFromGplus() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
            updateUI(false);
        }
    }

    private void authenticateUser(String socialBackend, String token, String personName, String id, String personPhotoUrl, String profileLink) {
        progressDialog.dismiss();
        sp.edit().putBoolean("hasLoggedIn", true).apply();

        new ExchangeToken().execute(socialBackend, token);
    }

    class GetDevelopmentToken extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(getString(R.string.base_url) + "auth/token/");
            BasicNameValuePair clientIdBasicNameValuePair = new BasicNameValuePair("client_id", getString(R.string.client_id));
            BasicNameValuePair clientSecretBasicNameValuePair = new BasicNameValuePair("client_secret", getString(R.string.client_secret));
            BasicNameValuePair grantTypeBasicNameValuePair = new BasicNameValuePair("grant_type", "password");
            BasicNameValuePair usernameBasicNameValuePair = new BasicNameValuePair("username", getString(R.string.admin_username));
            BasicNameValuePair passwordBasicNameValuePair = new BasicNameValuePair("password", getString(R.string.admin_password));

            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
            nameValuePairList.add(clientIdBasicNameValuePair);
            nameValuePairList.add(clientSecretBasicNameValuePair);
            nameValuePairList.add(grantTypeBasicNameValuePair);
            nameValuePairList.add(usernameBasicNameValuePair);
            nameValuePairList.add(passwordBasicNameValuePair);

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
            try {
                JSONObject resultObj = new JSONObject(result);
                String accessToken = resultObj.getString("access_token");
                String tokenType = resultObj.getString("token_type");
                String expiresIn = resultObj.getString("expires_in");
                String refreshToken = resultObj.getString("refresh_token");
                String scope = resultObj.getString("scope");


                sp.edit().putString("refreshToken", refreshToken).apply();
                sp.edit().putString("accessToken", accessToken).apply();

                Log.d("ACCESS TOKEN", accessToken);

                Toast.makeText(getApplicationContext(), "Development access token: " + accessToken, Toast.LENGTH_LONG).show();

                i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    class ExchangeToken extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String socialBackend = params[0];
            String token = params[1];
            String response = null;

            Log.d("SOCIAL HEADER", socialBackend + ": " + token);

            try {
                HttpGet httpGet = new HttpGet(getString(R.string.base_url) + "auth/convert-token/");

                httpGet.addHeader("Authorization", "Bearer " + socialBackend + " " + token);
                HttpClient httpClient = new DefaultHttpClient();
                HttpResponse httpResponse = httpClient.execute(httpGet);

                int status = httpResponse.getStatusLine().getStatusCode();

                Log.d("REFRESH TOKEN STATUS", String.valueOf(status));

                HttpEntity entity = httpResponse.getEntity();

                if (status == 200) {
                    response = EntityUtils.toString(entity);
                } else if (status == 401) {
                    response = "Sorry your " + socialBackend + " credentials couldn't be used. Please try a different login option. (Status 401)";
                } else if (status == 501) {
                    response = "This is weird, the server does not recognize the request method :-( (Status 501)";
                } else {
                    response = EntityUtils.toString(entity);
                }
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
}
