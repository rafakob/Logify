package com.rafakob.logify.repository;

import com.google.gson.Gson;
import com.rafakob.logify.repository.entity.AppLog;
import com.rafakob.logify.repository.entity.Log;
import com.rafakob.logify.repository.entity.NetworkLog;

class LogsTransformer {

    private static final String TYPE_APP = "app";
    private static final String TYPE_NETWORK = "network";

    private static final Gson gson = new Gson();

    public static Log logFromEntry(LogsDao.LogEntry logEntry) {
        Log log = null;

        switch (logEntry.type) {

            case TYPE_APP:
                log = gson.fromJson(logEntry.log, AppLog.class);
                break;

            case TYPE_NETWORK:
                log = gson.fromJson(logEntry.log, NetworkLog.class);
                break;
        }

        if (log != null) {
            log.setId(logEntry.id);
        }

        return log;
    }

    public static LogsDao.LogEntry logToEntry(Log log) {
        LogsDao.LogEntry logEntry = new LogsDao.LogEntry();

        logEntry.id = log.getId();

        String type = null;

        if (log instanceof AppLog) {
            type = TYPE_APP;
        }

        if (log instanceof NetworkLog) {
            type = TYPE_NETWORK;
        }

        logEntry.type = type;
        logEntry.log = gson.toJson(log);

        return logEntry;
    }
}
