package com.udacity.stockhawk;

import android.app.Application;
import android.content.Context;

import timber.log.Timber;

public class StockHawkApp extends Application {
public static Context global_context;
    @Override
    public void onCreate() {
        super.onCreate();
        global_context=getApplicationContext();

        if (BuildConfig.DEBUG) {
            Timber.uprootAll();
            Timber.plant(new Timber.DebugTree());
        }
    }
}
