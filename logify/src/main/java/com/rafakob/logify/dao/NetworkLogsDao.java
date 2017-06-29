package com.rafakob.logify.dao;

import android.content.Context;
import android.database.Cursor;

import com.rafakob.logify.entity.NetworkLog;

public class NetworkLogsDao extends BaseDao<NetworkLog> {

    public NetworkLogsDao(Context context) {
        super(context);
    }

    @Override
    protected String getTableName() {
        return NetworkLog.TABLE_NAME;
    }

    @Override
    protected String getColumnId() {
        return NetworkLog.COLUMN_ID;
    }

    @Override
    protected String getColumnLog() {
        return NetworkLog.COLUMN_LOG;
    }

    @Override
    protected NetworkLog createFromCursor(Cursor cursor) {
        NetworkLog networkLog = gson.fromJson(cursor.getString(1), NetworkLog.class);
        networkLog.setId(cursor.getLong(0));
        return networkLog;
    }
}
