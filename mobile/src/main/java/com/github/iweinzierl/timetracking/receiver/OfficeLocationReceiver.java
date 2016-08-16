package com.github.iweinzierl.timetracking.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.github.iweinzierl.timetracking.model.CheckInOut;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OfficeLocationReceiver extends BroadcastReceiver {

    private static final String TAG = "[Tracking-Phone]";

    private static final String EXTRA_DATETIME = "datetime";
    private static final String EXTRA_TYPE = "type";

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-mm-dd HH:MM");

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "Received Broadcast Intent");
        Log.d(TAG, "Extra DateTime: " + intent.getStringExtra(EXTRA_DATETIME));
        Log.d(TAG, "Extra Type    : " + intent.getStringExtra(EXTRA_TYPE));

        try {
            Date dateTime = DATE_FORMAT.parse(intent.getStringExtra(EXTRA_DATETIME));
            CheckInOut.Type type = CheckInOut.Type.valueOf(intent.getStringExtra(EXTRA_TYPE));

            CheckInOut checkInOut = new CheckInOut();
            checkInOut.setDateTime(dateTime);
            checkInOut.setType(type);

            long id = checkInOut.save();

            if (id > 0 && type == CheckInOut.Type.CHECKIN) {
                Log.i(TAG, "Successfully checked in");
            } else if (id > 0 && type == CheckInOut.Type.CHECKOUT) {
                Log.i(TAG, "Successfully checked out");
            } else if (id <= 0 && type == CheckInOut.Type.CHECKIN) {
                Log.w(TAG, "Failed to checkin");
            } else if (id <= 0 && type == CheckInOut.Type.CHECKOUT) {
                Log.w(TAG, "Failed to checkout");
            }
        } catch (ParseException e) {
            Log.e(TAG, "Unable to parseTechnical dateTime from extras: " + intent.getStringExtra(EXTRA_DATETIME));
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Illegal type argument from extras: " + intent.getStringExtra(EXTRA_TYPE));
        }
    }
}
