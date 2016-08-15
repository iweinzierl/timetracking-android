package com.github.iweinzierl.timetracking.navigation;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListAdapter;
import android.widget.ListView;

public class DrawerListView extends ListView {

    public DrawerListView(Context context) {
        super(context);
    }

    public DrawerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawerListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DrawerListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);

        if (adapter instanceof DrawerAdapter) {
            setOnItemClickListener((OnItemClickListener) adapter);
        }
    }
}
