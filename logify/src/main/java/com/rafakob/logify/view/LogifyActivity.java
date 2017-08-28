package com.rafakob.logify.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rafakob.logify.R;
import com.rafakob.logify.repository.LogsRepository;
import com.rafakob.logify.repository.entity.AppLog;
import com.rafakob.logify.repository.entity.Log;
import com.rafakob.logify.repository.entity.NetworkLog;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;

public class LogifyActivity extends AppCompatActivity implements LogifyAdapter.ItemListener {

    private LogifyAdapter adapter = new LogifyAdapter();
    private RecyclerView recycler;
    private View loadingState;

    public static void start(Context context) {
        context.startActivity(new Intent(context, LogifyActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logify);
        loadingState = findViewById(R.id.loading_state);

        adapter.setItemListener(this);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(adapter);
        loadLogs();
    }

    private void loadLogs() {
        showLoadingState();
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                showLogs(LogsRepository.getInstance().getLogs());
            }
        });
    }

    private void showLogs(final List<Log> logs) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Collections.sort(logs, new Comparator<Log>() {
                    @Override
                    public int compare(Log t1, Log t2) {
                        return t2.getId().compareTo(t1.getId());
                    }
                });
                adapter.setItems(logs);
                showContentState();
            }
        });
    }

    private void showLoadingState() {
        loadingState.setVisibility(View.VISIBLE);
        recycler.setVisibility(View.GONE);
    }

    private void showContentState() {
        loadingState.setVisibility(View.GONE);
        recycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAppLogClick(AppLog appLog) {

    }

    @Override
    public void onNetworkLogClick(NetworkLog networkLog) {
        NetworkLogDialog dialog = NetworkLogDialog.newInstance(networkLog);
        dialog.show(getFragmentManager(), "NetworkLogDialog");
    }
}
