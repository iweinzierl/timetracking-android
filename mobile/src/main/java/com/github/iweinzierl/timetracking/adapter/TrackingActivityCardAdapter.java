package com.github.iweinzierl.timetracking.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.iweinzierl.android.utils.UiUtils;
import com.github.iweinzierl.timetracking.R;
import com.github.iweinzierl.timetracking.model.TrackingActivity;
import com.github.iweinzierl.timetracking.util.DateFormatter;

import java.util.List;

public class TrackingActivityCardAdapter extends RecyclerView.Adapter<TrackingActivityCardAdapter.TrackingActivityViewHolder> {

    static class TrackingActivityViewHolder extends RecyclerView.ViewHolder {
        TextView uuidView;
        TextView uidView;
        TextView beginView;
        TextView endView;
        TextView descriptionView;
        TextView bucketView;

        TrackingActivityViewHolder(View itemView) {
            super(itemView);

            uuidView = UiUtils.getGeneric(TextView.class, itemView, R.id.uuid);
            uidView = UiUtils.getGeneric(TextView.class, itemView, R.id.uid);
            beginView = UiUtils.getGeneric(TextView.class, itemView, R.id.begin);
            endView = UiUtils.getGeneric(TextView.class, itemView, R.id.end);
            descriptionView = UiUtils.getGeneric(TextView.class, itemView, R.id.description);
            bucketView = UiUtils.getGeneric(TextView.class, itemView, R.id.bucket);
        }
    }

    private final Context context;
    private final List<TrackingActivity> items;
    private final DateFormatter dateFormatter;

    public TrackingActivityCardAdapter(Context context, List<TrackingActivity> items) {
        this.context = context;
        this.items = items;
        this.dateFormatter = new DateFormatter(context.getResources().getConfiguration().locale);
    }

    @Override
    public TrackingActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_tracking_activity, parent, false);

        return new TrackingActivityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TrackingActivityViewHolder holder, int position) {
        TrackingActivity item = items.get(position);

        holder.uuidView.setText(item.getUuid());
        holder.uidView.setText(item.getUid());
        holder.beginView.setText(dateFormatter.formatHuman(item.getBegin()));
        holder.endView.setText(dateFormatter.formatHuman(item.getEnd()));
        holder.descriptionView.setText(item.getDescription());
        holder.bucketView.setText(item.getBucket());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
