package com.rafakob.logify.dao;

import android.content.Context;
import android.database.Cursor;

import com.rafakob.logify.entity.AppLog;

public class AppLogsDao extends BaseDao<AppLog> {

    public AppLogsDao(Context context) {
        super(context);
    }

    @Override
    protected String getTableName() {
        return AppLog.TABLE_NAME;
    }

    @Override
    protected String getColumnId() {
        return AppLog.COLUMN_ID;
    }

    @Override
    protected String getColumnLog() {
        return AppLog.COLUMN_LOG;
    }

    @Override
    protected AppLog createFromCursor(Cursor cursor) {
        AppLog appLog = gson.fromJson(cursor.getString(1), AppLog.class);
        appLog.setId(cursor.getLong(0));
        return appLog;
    }
}
