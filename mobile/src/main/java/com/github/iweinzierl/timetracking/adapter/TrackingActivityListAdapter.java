package com.github.iweinzierl.timetracking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.iweinzierl.android.utils.UiUtils;
import com.github.iweinzierl.android.utils.widget.BaseListAdapter;
import com.github.iweinzierl.timetracking.R;
import com.github.iweinzierl.timetracking.model.TrackingActivity;

import java.text.SimpleDateFormat;
import java.util.List;

public class TrackingActivityListAdapter extends BaseListAdapter<TrackingActivity> {

    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-mm-dd HH:MM");

    public TrackingActivityListAdapter(Context context, List<TrackingActivity> items) {
        super(context, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TrackingActivity item = (TrackingActivity) getItem(position);

        View view = inflater.inflate(R.layout.listitem_tracking_activity, parent, false);

        UiUtils.setSafeText(view, R.id.begin, DATE_FORMAT.format(item.getBegin()));
        UiUtils.setSafeText(view, R.id.end, DATE_FORMAT.format(item.getEnd()));
        UiUtils.setSafeText(view, R.id.description, item.getDescription());
        UiUtils.setSafeText(view, R.id.bucket, item.getBucket());
        UiUtils.setSafeText(view, R.id.synched, item.getUid() != null ? "true" : "false");

        return view;
    }
}
