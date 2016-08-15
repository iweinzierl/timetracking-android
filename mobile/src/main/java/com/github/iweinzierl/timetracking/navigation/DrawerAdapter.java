package com.github.iweinzierl.timetracking.navigation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.github.iweinzierl.android.utils.UiUtils;
import com.github.iweinzierl.android.utils.widget.BaseListAdapter;
import com.github.iweinzierl.timetracking.CheckInOutActivity_;
import com.github.iweinzierl.timetracking.R;
import com.google.common.collect.Lists;

public class DrawerAdapter extends BaseListAdapter implements AdapterView.OnItemClickListener {

    private static class DrawerItem {

        private int titleRes;
        private int iconRes;
        private Class startActivity;

        DrawerItem(Class startActivity, int titleRes, int iconRes) {
            this.titleRes = titleRes;
            this.iconRes = iconRes;
            this.startActivity = startActivity;
        }

    }

    public DrawerAdapter(Context context) {
        super(context, Lists.newArrayList(
                new DrawerItem(CheckInOutActivity_.class, R.string.drawer_checkinout, 0),
                new DrawerItem(CheckInOutActivity_.class, R.string.drawer_unsynchronized_activities, 0)
        ));
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        DrawerItem item = (DrawerItem) getItem(i);

        View itemView = inflater.inflate(R.layout.listitem_drawer, viewGroup, false);
        UiUtils.setSafeText(itemView, R.id.title, getContext().getString(item.titleRes));

        return itemView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        DrawerItem item = (DrawerItem) getItem(i);
        getContext().startActivity(new Intent(getContext(), item.startActivity));
    }
}
