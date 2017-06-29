package com.rafakob.logify.repository;

import android.content.Context;

import com.rafakob.logify.dao.AppLogsDao;
import com.rafakob.logify.dao.NetworkLogsDao;

public class LogRepository {
    private static LogRepository instance = new LogRepository();

    private NetworkLogsDao networkLogsDao;
    private AppLogsDao appLogsDao;

    private LogRepository() {
    }

    public void init(Context context) {
        networkLogsDao = new NetworkLogsDao(context);
        appLogsDao = new AppLogsDao(context);
    }

    public static LogRepository getInstance() {
        return instance;
    }
}
