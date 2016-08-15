package com.github.iweinzierl.timetracking;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.github.iweinzierl.timetracking.model.CheckInOut;
import com.github.iweinzierl.timetracking.model.TrackingActivity;
import com.github.iweinzierl.timetracking.util.DateFormatter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.Wearable;
import com.orm.SugarApp;

import org.androidannotations.annotations.EApplication;

import java.text.ParseException;
import java.util.UUID;

@EApplication
public class TrackingApp extends SugarApp implements DataApi.DataListener {

    private static final String TAG = "[Tracking]";

    private DateFormatter dateFormatter;

    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate() {
        super.onCreate();

        dateFormatter = new DateFormatter(getResources().getConfiguration().locale);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                        Log.d(TAG, "onConnected: " + connectionHint);
                        Wearable.DataApi.addListener(mGoogleApiClient, TrackingApp.this);
                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                        Log.d(TAG, "onConnectionSuspended: " + cause);
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult result) {
                        Log.d(TAG, "onConnectionFailed: " + result);
                    }
                })
                .addApi(Wearable.API)
                .build();

        mGoogleApiClient.connect();

        if (BuildConfig.DEBUG && CheckInOut.count(CheckInOut.class) == 0) {
            insertTestCheckInsOuts();
        }

        if (BuildConfig.DEBUG && TrackingActivity.count(TrackingActivity.class) == 0) {
            insertTestTrackingActivities();
        }
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        Log.d(TAG, "Received data changed event: ");
    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    private void insertTestCheckInsOuts() {
        try {
            new CheckInOut(dateFormatter.parse("2016-08-01T08:00:00+0200"), CheckInOut.Type.CHECKIN).save();
            new CheckInOut(dateFormatter.parse("2016-08-01T12:00:00+0200"), CheckInOut.Type.CHECKOUT).save();
            new CheckInOut(dateFormatter.parse("2016-08-01T13:00:00+0200"), CheckInOut.Type.CHECKIN).save();
            new CheckInOut(dateFormatter.parse("2016-08-01T18:30:00+0200"), CheckInOut.Type.CHECKOUT).save();
            new CheckInOut(dateFormatter.parse("2016-08-02T07:45:00+0200"), CheckInOut.Type.CHECKIN).save();
            new CheckInOut(dateFormatter.parse("2016-08-02T12:10:00+0200"), CheckInOut.Type.CHECKOUT).save();
            new CheckInOut(dateFormatter.parse("2016-08-02T13:00:00+0200"), CheckInOut.Type.CHECKIN).save();
            new CheckInOut(dateFormatter.parse("2016-08-02T17:45:00+0200"), CheckInOut.Type.CHECKOUT).save();
        } catch (ParseException e) {
            Log.w(TAG, "Unable to parse date string", e);
        }
    }

    private void insertTestTrackingActivities() {
        UUID uid = UUID.randomUUID();

        try {
            new TrackingActivity(
                    uid.toString(),
                    dateFormatter.parse("2016-08-01T08:00:00+0200"),
                    dateFormatter.parse("2016-08-01T08:15:00+0200"),
                    "Diverse Mails",
                    "Mail"
            ).save();

            new TrackingActivity(
                    uid.toString(),
                    dateFormatter.parse("2016-08-01T08:15:00+0200"),
                    dateFormatter.parse("2016-08-01T08:35:00+0200"),
                    "Daily Standup",
                    "Team"
            ).save();

            new TrackingActivity(
                    uid.toString(),
                    dateFormatter.parse("2016-08-01T08:35:00+0200"),
                    dateFormatter.parse("2016-08-01T08:45:00+0200"),
                    "1on1 with Mr. X",
                    "People"
            ).save();

            new TrackingActivity(
                    uid.toString(),
                    dateFormatter.parse("2016-08-01T08:45:00+0200"),
                    dateFormatter.parse("2016-08-01T09:15:00+0200"),
                    "Planning Meeting",
                    "Management"
            ).save();

            new TrackingActivity(
                    uid.toString(),
                    dateFormatter.parse("2016-08-01T09:15:00+0200"),
                    dateFormatter.parse("2016-08-01T10:00:00+0200"),
                    "Discussion about further topics",
                    "Management"
            ).save();

            new TrackingActivity(
                    uid.toString(),
                    dateFormatter.parse("2016-08-01T10:00:00+0200"),
                    dateFormatter.parse("2016-08-01T11:00:00+0200"),
                    "API Review",
                    "TECH"
            ).save();
        } catch (ParseException e) {
            Log.w(TAG, "Unable to parse date string", e);
        }
    }
}
