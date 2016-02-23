package com.toe.firstcustoma.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;
import com.parse.SendCallback;
import com.toe.firstcustoma.R;

import org.json.JSONObject;

/**
 * Created by Wednesday on 1/17/2016.
 */
public class ParseUtils {

    private static String TAG = "PLAINPARSE";

    public static void registerParse(Context context) {
        Parse.initialize(context, context.getString(R.string.parse_app_id), context.getString(R.string.parse_client_key));
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParsePush.subscribeInBackground("Plain", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d(TAG, "Successfully subscribed to the broadcast channel.");
                } else {
                    Log.d(TAG, "Failed to subscribe for push", e);
                }
            }
        });
    }

    public static void subscribeToChannel(final String channel) {
        ParsePush.subscribeInBackground(channel.replaceAll("[^A-Za-z0-9 ]", ""), new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d(TAG, "Successfully subscribed to the broadcast channel " + channel);
                } else {
                    Log.d(TAG, "Failed to subscribe to the channel " + channel, e);
                }
            }
        });
    }

    public static void unsubscribeFromChannel(final String channel) {
        ParsePush.unsubscribeInBackground(channel.replaceAll("[^A-Za-z0-9 ]", ""), new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d(TAG, "Successfully unsubscribed to the broadcast channel " + channel);
                } else {
                    Log.d(TAG, "Failed to unsubscribe to the channel " + channel, e);
                }
            }
        });
    }

    public static void sendPushNotification(final String channel, JSONObject json) {
        ParsePush push = new ParsePush();
        push.setChannel(channel.replaceAll("[^A-Za-z0-9 ]", ""));
        push.setData(json);
        push.sendInBackground(new SendCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d(TAG, "Successfully sent push notification to " + channel);
                } else {
                    Log.d(TAG, "Failed to send push notification to " + channel, e);
                }
            }
        });
    }
}
