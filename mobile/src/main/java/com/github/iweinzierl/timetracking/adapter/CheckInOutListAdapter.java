package com.github.iweinzierl.timetracking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.iweinzierl.android.utils.UiUtils;
import com.github.iweinzierl.android.utils.widget.BaseListAdapter;
import com.github.iweinzierl.timetracking.R;
import com.github.iweinzierl.timetracking.model.CheckInOut;
import com.github.iweinzierl.timetracking.util.DateFormatter;

import java.util.List;

public class CheckInOutListAdapter extends BaseListAdapter<CheckInOut> {

    private final DateFormatter dateFormatter;

    public CheckInOutListAdapter(Context context, List<CheckInOut> items) {
        super(context, items);
        dateFormatter = new DateFormatter(context.getResources().getConfiguration().locale);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        CheckInOut object = getTypedItem(position);

        View view = inflater.inflate(R.layout.listitem_checkinout, parent, false);

        UiUtils.setSafeText(view, R.id.datetime, dateFormatter.format(object.getDateTime()));
        UiUtils.setSafeText(view, R.id.type, object.getType().name());

        return view;
    }
}
