package com.rafakob.logify.view;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
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
        vh.timestamp.setText(model.getTimestamp().toString());
        vh.status.setText(model.getResponseCode().toString());

        if (model.getResponseCode() >= 200 && model.getResponseCode() < 400) {
            vh.label.setBackgroundColor(Color.GREEN);
        }

        if (model.getResponseCode() >= 400 && model.getResponseCode() < 500) {
            vh.label.setBackgroundColor(Color.RED);
        }

        if (model.getResponseCode() >= 500) {
            vh.label.setBackgroundColor(Color.BLACK);
        }
    }

    private void bindAppLogViewHolder(AppLogViewHolder vh, AppLog model) {

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
        public AppLogViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class NetworkLogViewHolder extends RecyclerView.ViewHolder {
        TextView method, path, timestamp, host, status;
        View label;

        public NetworkLogViewHolder(View itemView) {
            super(itemView);
            method = itemView.findViewById(R.id.method);
            path = itemView.findViewById(R.id.path);
            timestamp = itemView.findViewById(R.id.timestamp);
            host = itemView.findViewById(R.id.host);
            status = itemView.findViewById(R.id.status);
            label = itemView.findViewById(R.id.label);
        }
    }

    public void setItems(List<Log> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
