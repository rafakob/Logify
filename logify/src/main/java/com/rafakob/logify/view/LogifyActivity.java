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
import com.rafakob.logify.repository.entity.Log;

import java.util.List;
import java.util.concurrent.Executors;

public class LogifyActivity extends AppCompatActivity {

    private LogifyAdapter adapter = new LogifyAdapter();
    private View loadingState;
    private RecyclerView recycler;

    public static void start(Context context) {
        context.startActivity(new Intent(context, LogifyActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logify);
        loadingState = findViewById(R.id.loading_state);
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
}
