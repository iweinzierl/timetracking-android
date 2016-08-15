package com.github.iweinzierl.timetracking;

import android.widget.TextView;

import com.github.iweinzierl.timetracking.model.CheckInOut;
import com.github.iweinzierl.timetracking.model.TrackingActivity;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

@EActivity
public class MainActivity extends DrawerActivity {

    @ViewById(R.id.number_of_checkins)
    protected TextView numberOfCheckinsView;

    @ViewById(R.id.number_of_checkouts)
    protected TextView numberOfCheckoutsView;

    @ViewById(R.id.number_of_unsynched_activities)
    protected TextView numberOfUnsynchedActivitiesView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI();
    }

    @Background
    protected void updateUI() {
        long numOfCheckins = CheckInOut.getNumberOfCheckins();
        long numOfCheckouts = CheckInOut.getNumberOfCheckouts();
        long numOfUnsynchedActivities = TrackingActivity.getNumberOfUnsynchedActivities();

        setNumberOfCheckins(numOfCheckins);
        setNumberOfCheckouts(numOfCheckouts);
        setNumberOfUnsynchedActivities(numOfUnsynchedActivities);
    }

    @UiThread
    protected void setNumberOfCheckins(long checkins) {
        numberOfCheckinsView.setText(String.valueOf(checkins));
    }

    @UiThread
    protected void setNumberOfCheckouts(long checkouts) {
        numberOfCheckoutsView.setText(String.valueOf(checkouts));
    }

    @UiThread
    protected void setNumberOfUnsynchedActivities(long unsynchedActivities) {
        numberOfUnsynchedActivitiesView.setText(String.valueOf(unsynchedActivities));
    }
}
