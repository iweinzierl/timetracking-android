package com.github.iweinzierl.timetracking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.iweinzierl.android.utils.UiUtils;
import com.github.iweinzierl.android.utils.widget.BaseListAdapter;
import com.github.iweinzierl.timetracking.R;
import com.github.iweinzierl.timetracking.model.CheckInOut;

import java.text.SimpleDateFormat;
import java.util.List;

public class CheckInOutListAdapter extends BaseListAdapter<CheckInOut> {

    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-mm-dd HH:MM");

    public CheckInOutListAdapter(Context context, List<CheckInOut> items) {
        super(context, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        CheckInOut object = getTypedItem(position);

        View view = inflater.inflate(R.layout.listitem_tracking_activity, parent, false);

        UiUtils.setSafeText(view, R.id.datetime, DATE_FORMAT.format(object.getDateTime()));
        UiUtils.setSafeText(view, R.id.type, object.getType().name());

        return view;
    }
}
