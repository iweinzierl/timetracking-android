package com.github.iweinzierl.timetracking;

import android.widget.ListView;

import com.github.iweinzierl.timetracking.adapter.TrackingActivityListAdapter;
import com.github.iweinzierl.timetracking.model.TrackingActivity;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity
public class UnsynchedTrackingActivity extends DrawerActivity {

    @ViewById(R.id.unsynched_tracking_list)
    protected ListView unsynchedTrackingList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_unsynched_tracking;
    }

    @Override
    protected void onStart() {
        super.onStart();

        updateUI();
    }

    @Background
    protected void updateUI() {
        updateUnsynchedActivities(TrackingActivity.listUnsynchedActivities());
    }

    @UiThread
    protected void updateUnsynchedActivities(List<TrackingActivity> activities) {
        TrackingActivityListAdapter adapter = new TrackingActivityListAdapter(this, activities);
        unsynchedTrackingList.setAdapter(adapter);
    }
}
