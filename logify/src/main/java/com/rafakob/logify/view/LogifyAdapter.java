package com.rafakob.logify.view;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rafakob.logify.R;
import com.rafakob.logify.repository.entity.AppLog;
import com.rafakob.logify.repository.entity.Log;
import com.rafakob.logify.repository.entity.NetworkLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LogifyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_APP = 0;
    public static final int TYPE_NETWORK = 1;

    private List<Log> items = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_APP:
                return new AppLogViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app_log, parent, false));
            case TYPE_NETWORK:
                return new NetworkLogViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_network_log, parent, false));
            default:
                return new AppLogViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app_log, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NetworkLogViewHolder && items.get(position) instanceof NetworkLog) {
            bindNetworkLogViewHolder((NetworkLogViewHolder) holder, (NetworkLog) items.get(position));
        }

        if (holder instanceof AppLogViewHolder && items.get(position) instanceof AppLog) {
            bindAppLogViewHolder((AppLogViewHolder) holder, (AppLog) items.get(position));
        }
    }

    private void bindNetworkLogViewHolder(NetworkLogViewHolder vh, NetworkLog model) {
        vh.method.setText(model.getRequestMethod());
        vh.path.setText(model.getPath());
        vh.duration.setText(String.format(Locale.getDefault(), "%dms", model.getDuration()));
        vh.host.setText(model.getHost());
        vh.timestamp.setText(getDateFromTimestamp(model.getTimestamp()));
        vh.status.setText(String.format("%s %s", model.getResponseCode(), model.getResponseMessage()).trim());

        if (model.getResponseCode() >= 200 && model.getResponseCode() < 400) {
            vh.status.setBackgroundResource(R.drawable.logify_round_rect_success);
        }

        if (model.getResponseCode() >= 400 && model.getResponseCode() < 500) {
            vh.status.setBackgroundResource(R.drawable.logify_round_rect_error);
        }

        if (model.getResponseCode() >= 500) {
            vh.status.setBackgroundResource(R.drawable.logify_round_rect_undefined);
        }
    }

    private void bindAppLogViewHolder(AppLogViewHolder vh, AppLog model) {
        vh.tag.setText(model.getTag());
        vh.level.setText(model.getLevel().toUpperCase());
        vh.message.setText(model.getMessage());
        vh.timestamp.setText(getDateFromTimestamp(model.getTimestamp()));

        if (model.getLevel().equals(AppLog.LEVEL_DEBUG)) {
            vh.level.setBackgroundResource(R.drawable.logify_round_rect_debug);
        }

        if (model.getLevel().equals(AppLog.LEVEL_ERROR)) {
            vh.level.setBackgroundResource(R.drawable.logify_round_rect_error);
        }

        if (model.getLevel().equals(AppLog.LEVEL_INFO)) {
            vh.level.setBackgroundResource(R.drawable.logify_round_rect_info);
        }

        if (model.getLevel().equals(AppLog.LEVEL_VERBOSE)) {
            vh.level.setBackgroundResource(R.drawable.logify_round_rect_verbose);
        }

        if (model.getLevel().equals(AppLog.LEVEL_WARNING)) {
            vh.level.setBackgroundResource(R.drawable.logify_round_rect_warning);
        }
    }

    private String getDateFromTimestamp(long timestamp) {
        return DateFormat.format("yyyy-MM-dd, hh:mm:ss", timestamp).toString();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof AppLog) {
            return TYPE_APP;
        }
        if (items.get(position) instanceof NetworkLog) {
            return TYPE_NETWORK;
        }
        return TYPE_APP;
    }


    public class AppLogViewHolder extends RecyclerView.ViewHolder {
        TextView tag, level, message, timestamp;

        public AppLogViewHolder(View itemView) {
            super(itemView);
            tag = itemView.findViewById(R.id.tag);
            level = itemView.findViewById(R.id.level);
            message = itemView.findViewById(R.id.message);
            timestamp = itemView.findViewById(R.id.timestamp);
        }
    }

    public class NetworkLogViewHolder extends RecyclerView.ViewHolder {
        TextView method, path, duration, timestamp, host, status;

        public NetworkLogViewHolder(View itemView) {
            super(itemView);
            method = itemView.findViewById(R.id.method);
            path = itemView.findViewById(R.id.path);
            duration = itemView.findViewById(R.id.duration);
            timestamp = itemView.findViewById(R.id.timestamp);
            host = itemView.findViewById(R.id.host);
            status = itemView.findViewById(R.id.status);
        }
    }

    public void setItems(List<Log> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
