package com.github.iweinzierl.timetracking.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
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

    public interface OnItemClickListener {
        void onItemClick(TrackingActivity trackingActivity);
    }

    static class TrackingActivityViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView uuidView;
        TextView uidView;
        TextView beginView;
        TextView endView;
        TextView descriptionView;
        TextView bucketView;
        View indicatorView;

        TrackingActivityViewHolder(View itemView) {
            super(itemView);

            cardView = UiUtils.getGeneric(CardView.class, itemView, R.id.card_view);
            uuidView = UiUtils.getGeneric(TextView.class, itemView, R.id.uuid);
            uidView = UiUtils.getGeneric(TextView.class, itemView, R.id.uid);
            beginView = UiUtils.getGeneric(TextView.class, itemView, R.id.begin);
            endView = UiUtils.getGeneric(TextView.class, itemView, R.id.end);
            descriptionView = UiUtils.getGeneric(TextView.class, itemView, R.id.description);
            bucketView = UiUtils.getGeneric(TextView.class, itemView, R.id.bucket);
            indicatorView = UiUtils.getGeneric(View.class, itemView, R.id.indicator);
        }
    }

    private final Context context;
    private final List<TrackingActivity> items;
    private final DateFormatter dateFormatter;

    private OnItemClickListener onItemClickListener;

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
        final TrackingActivity item = items.get(position);

        holder.uuidView.setText(item.getUuid());
        holder.uidView.setText(item.getUid());
        holder.beginView.setText(dateFormatter.formatHuman(item.getBegin()));
        holder.endView.setText(dateFormatter.formatHuman(item.getEnd()));
        holder.descriptionView.setText(item.getDescription());
        holder.bucketView.setText(item.getBucket());

        if (item.getEnd() == null || item.getDescription() == null || item.getBucket() == null) {
            holder.indicatorView.setBackgroundColor(context.getResources().getColor(
                    R.color.colorIndicatorError));
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
