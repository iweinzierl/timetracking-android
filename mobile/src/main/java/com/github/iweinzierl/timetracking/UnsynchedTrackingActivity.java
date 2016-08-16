package com.github.iweinzierl.timetracking;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.iweinzierl.timetracking.adapter.TrackingActivityCardAdapter;
import com.github.iweinzierl.timetracking.model.TrackingActivity;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity
public class UnsynchedTrackingActivity extends DrawerActivity {

    @ViewById(R.id.unsynched_tracking_list)
    protected RecyclerView unsynchedTrackingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        unsynchedTrackingList.setHasFixedSize(true);
        unsynchedTrackingList.setLayoutManager(new LinearLayoutManager(this));
    }

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
        TrackingActivityCardAdapter adapter = new TrackingActivityCardAdapter(this, activities);
        unsynchedTrackingList.setAdapter(adapter);
    }
}
