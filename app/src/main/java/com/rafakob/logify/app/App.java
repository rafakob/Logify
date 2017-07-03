package com.rafakob.logify.app;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.rafakob.logify.Logify;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        Logify.init(this);
    }
}
