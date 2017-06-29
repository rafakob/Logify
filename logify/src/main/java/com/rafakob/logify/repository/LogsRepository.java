package com.rafakob.logify.repository;

import android.content.Context;

import com.rafakob.logify.repository.entity.AppLog;
import com.rafakob.logify.repository.entity.NetworkLog;


public class LogsRepository {
    private static LogsRepository instance = new LogsRepository();

    private LogsDao logsDao;

    private LogsRepository() {
    }

    public void init(Context context) {
        logsDao = new LogsDao(context);
    }

    public static LogsRepository getInstance() {
        return instance;
    }

    public void insertAppLog(String type, String message) {
        AppLog appLog = new AppLog();
        appLog.setType(type);
        appLog.setMessage(message);

        logsDao.open();
        logsDao.insert(appLog);
        logsDao.close();
    }

    public void insertNetworkLog(NetworkLog networkLog) {
        logsDao.open();
        logsDao.insert(networkLog);
        logsDao.close();
    }
}
