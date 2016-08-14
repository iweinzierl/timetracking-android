package com.github.iweinzierl.timetracking.service;

import android.util.Log;

import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.WearableListenerService;

public class DataListenerService extends WearableListenerService {

    private static final String TAG = "[Tracking-Phone]";

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        super.onDataChanged(dataEventBuffer);
        Log.d(TAG, "Received data changed event: ");
    }
}
