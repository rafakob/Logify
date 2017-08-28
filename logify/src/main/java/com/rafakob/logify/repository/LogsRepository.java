package com.rafakob.logify.repository;

import android.content.Context;

import com.rafakob.logify.repository.entity.AppLog;
import com.rafakob.logify.repository.entity.Log;
import com.rafakob.logify.repository.entity.NetworkLog;

import java.util.Calendar;
import java.util.List;


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

    public void insertAppLog(String tag, String level, String message) {
        AppLog appLog = new AppLog();
        appLog.setLevel(level);
        appLog.setTag(tag);
        appLog.setMessage(message);
        appLog.setTimestamp(Calendar.getInstance().getTimeInMillis());

        logsDao.open();
        logsDao.insert(appLog);
        logsDao.close();
    }

    public void insertAppLog(AppLog appLog) {
        logsDao.open();
        logsDao.insert(appLog);
        logsDao.close();
    }

    public void insertNetworkLog(NetworkLog networkLog) {
        logsDao.open();
        logsDao.insert(networkLog);
        logsDao.close();
    }

    public List<Log> getLogs() {
        logsDao.open();
        List<Log> logs = logsDao.select();
        logsDao.close();
        return logs;
    }
}
