package com.rafakob.logify.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "logify.db";
    public static final Integer DB_VERSION = 1;

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + LogsDao.LogEntry.TABLE_NAME + " (" +
                LogsDao.LogEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LogsDao.LogEntry.COLUMN_TYPE + " TEXT, " +
                LogsDao.LogEntry.COLUMN_LOG + " TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LogsDao.LogEntry.TABLE_NAME);
        onCreate(db);
    }
}