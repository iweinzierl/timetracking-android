package com.github.iweinzierl.timetracking;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;

import com.github.iweinzierl.timetracking.navigation.DrawerAdapter;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public abstract class DrawerActivity extends Activity {

    @ViewById(R.id.drawer_layout)
    protected DrawerLayout leftDrawerLayout;

    @ViewById(R.id.left_drawer)
    protected ListView leftDrawerList;

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
    }

    @Override
    protected void onStart() {
        super.onStart();
        leftDrawerList.setAdapter(new DrawerAdapter(this));
    }
}
