package com.rafakob.logify.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rafakob.logify.R;

public class LogifyActivity extends AppCompatActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, LogifyActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logify);
    }
}
