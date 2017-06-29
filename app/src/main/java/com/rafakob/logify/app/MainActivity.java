package com.rafakob.logify.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rafakob.logify.Logify;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Logify.init(this);

        Logify.i("Test message 1");
        Logify.i("Test message 2");

//        LogifyActivity.start(this);
    }
}
