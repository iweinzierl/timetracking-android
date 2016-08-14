package com.github.iweinzierl.timetracking;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ActivityTrackingActivity extends WearableActivity implements MessageApi.MessageListener {

    private static final String TAG = "[Tracking-Wear]";

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    private BoxInsetLayout mContainerView;
    private ImageView mActivityIndicatorView;
    private TextView mTextView;
    private TextView mRuntimeView;
    private ImageButton mStartButton;

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_tracking);
        setAmbientEnabled();

        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mActivityIndicatorView = (ImageView) findViewById(R.id.activity_indicator);
        mTextView = (TextView) findViewById(R.id.text);
        mRuntimeView = (TextView) findViewById(R.id.runtime);
        mStartButton = (ImageButton) findViewById(R.id.btn_start);

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick();
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                        Log.d(TAG, "connected to google play services");

                        Wearable.MessageApi.addListener(mGoogleApiClient, ActivityTrackingActivity.this);
                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                        Log.d(TAG, "onConnectionSuspended: " + cause);
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        Log.d(TAG, "onConnectionFailed: " + result);
                    }
                })
                .addApi(Wearable.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

    }

    private void handleButtonClick() {
        mActivityIndicatorView.setBackground(
                getResources().getDrawable(R.drawable.time_relaxing, getTheme()));

        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/test/path");
        putDataMapRequest.getDataMap().putString("msg", "Time Tracking Test Message");

        PutDataRequest putDataRequest = PutDataRequest.create("/test/path/");
        putDataRequest.setData("Test Message Time Tracking".getBytes());

        Log.d(TAG, "Start sending Time Tracking Message");

        /*
        PendingResult<DataApi.DataItemResult> dataItemResultPendingResult = Wearable.DataApi.putDataItem(mGoogleApiClient, putDataRequest);
        dataItemResultPendingResult.setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
            @Override
            public void onResult(@NonNull DataApi.DataItemResult dataItemResult) {
                Log.d(TAG, "Time Tracking Message delivered");
            }
        });
        */

        final String path = "/test/message";
        final String message = "test message";

        new Thread() {
            @Override
            public void run() {

                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
                for (Node node : nodes.getNodes()) {
                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                            mGoogleApiClient,
                            node.getId(),
                            path,
                            message.getBytes()).await();

                    if (result.getStatus().isSuccess()) {
                        Log.v(TAG, "Message: {" + message + "} sent to: " + node.getDisplayName());
                    } else {
                        Log.v(TAG, "ERROR: failed to send Message");
                    }
                }
            }
        }.start();
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d(TAG, "Received message: " + messageEvent.getPath());
    }

    private void updateDisplay() {
        if (isAmbient()) {
            mContainerView.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            mTextView.setTextColor(getResources().getColor(android.R.color.white));
            mRuntimeView.setVisibility(View.VISIBLE);

            mRuntimeView.setText(AMBIENT_DATE_FORMAT.format(new Date()));
        } else {
            mContainerView.setBackground(null);
            mTextView.setTextColor(getResources().getColor(android.R.color.black));
            mRuntimeView.setVisibility(View.GONE);
        }
    }
}
