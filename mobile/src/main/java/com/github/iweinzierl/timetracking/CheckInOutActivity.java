package com.github.iweinzierl.timetracking;

import android.widget.ListView;

import com.github.iweinzierl.timetracking.adapter.CheckInOutListAdapter;
import com.github.iweinzierl.timetracking.model.CheckInOut;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity
public class CheckInOutActivity extends DrawerActivity {

    @ViewById(R.id.checkinout_list)
    protected ListView checkInOutList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_checkinout;
    }

    @Override
    protected void onStart() {
        super.onStart();

        updateUI();
    }

    @Background
    protected void updateUI() {
        updateCheckInOutList(CheckInOut.listAll(CheckInOut.class));
    }

    @UiThread
    protected void updateCheckInOutList(List<CheckInOut> checkInsOuts) {
        CheckInOutListAdapter adapter = new CheckInOutListAdapter(this, checkInsOuts);
        checkInOutList.setAdapter(adapter);
    }
}
