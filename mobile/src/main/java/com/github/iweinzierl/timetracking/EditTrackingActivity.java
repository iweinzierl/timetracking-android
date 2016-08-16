package com.github.iweinzierl.timetracking;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.github.iweinzierl.timetracking.model.TrackingActivity;
import com.github.iweinzierl.timetracking.util.DateFormatter;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.text.ParseException;
import java.util.Date;

@EActivity(R.layout.activity_edit_tracking)
public class EditTrackingActivity extends Activity {

    public static final String EXTRA_TRACKING_ID = "com.github.iweinzierl.timetracking.EXTRA_TRACKING_ID";

    private static final String TAG = "[Tracking]";

    private DateFormatter dateFormatter;

    @ViewById(R.id.begin)
    protected EditText beginView;

    @ViewById(R.id.end)
    protected EditText endView;

    @ViewById(R.id.description)
    protected EditText descriptionView;

    @ViewById(R.id.bucket)
    protected EditText bucketView;

    @Override
    protected void onStart() {
        super.onStart();

        dateFormatter = new DateFormatter(getResources().getConfiguration().locale);

        long trackingId = getIntent().getLongExtra(EXTRA_TRACKING_ID, -1L);
        if (trackingId > 0) {
            updateUI(trackingId);
        } else {
            showActivityNotFoundError(trackingId);
        }
    }

    @Background
    protected void updateUI(long trackingId) {
        TrackingActivity activity = TrackingActivity.findById(TrackingActivity.class, trackingId);

        if (activity == null) {
            showActivityNotFoundError(trackingId);
        } else {
            updateUI(activity);
        }
    }

    @UiThread
    protected void updateUI(@NonNull TrackingActivity activity) {
        if (activity.getBegin() != null) {
            beginView.setText(dateFormatter.formatHuman(activity.getBegin()));
        }

        if (activity.getEnd() != null) {
            endView.setText(dateFormatter.formatHuman(activity.getEnd()));
        }

        descriptionView.setText(activity.getDescription());
        bucketView.setText(activity.getBucket());
    }

    @UiThread
    protected void showActivityNotFoundError(long trackingId) {
        Toast.makeText(this, "No Tracking Activity found for id: " + trackingId,
                Toast.LENGTH_SHORT).show();

        finish();
    }

    @UiThread
    protected void showActivityNotSavedError() {
        Toast.makeText(this, "Unable to save Tracking Activity", Toast.LENGTH_SHORT).show();
    }

    @Click(R.id.save)
    protected void saveTrackingActivity() {
        boolean error = false;

        Date begin = null;
        Date end = null;

        try {
            begin = extractBegin();
        } catch (ParseException e) {
            error = true;
            beginView.setError("Unable to parse date time");
        }

        try {
            end = extractEnd();
        } catch (ParseException e) {
            error = true;
            endView.setError("Unable to parse date time");
        }

        String description = descriptionView.getText().toString();
        String bucket = bucketView.getText().toString();

        if (!error) {
            updateTrackingActivity(begin, end, description, bucket);
        }
    }

    @Background
    protected void updateTrackingActivity(Date begin, Date end, String description, String bucket) {
        long trackingId = getIntent().getLongExtra(EXTRA_TRACKING_ID, -1L);

        TrackingActivity activity = TrackingActivity.findById(TrackingActivity.class, trackingId);
        activity.setBegin(begin);
        activity.setEnd(end);
        activity.setDescription(description);
        activity.setBucket(bucket);

        Log.d(TAG, "Save tracking activity: " + activity);

        if (activity.save() > 0) {
            finish();
        } else {
            showActivityNotSavedError();
        }
    }

    private Date extractBegin() throws ParseException {
        String begin = beginView.getText().toString();
        return dateFormatter.parseHuman(begin);
    }

    private Date extractEnd() throws ParseException {
        String end = endView.getText().toString();
        return dateFormatter.parseHuman(end);
    }
}
