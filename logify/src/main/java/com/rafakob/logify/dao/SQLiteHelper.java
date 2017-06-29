package com.rafakob.logify.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rafakob.logify.entity.AppLog;
import com.rafakob.logify.entity.NetworkLog;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "logify.db";
    public static final Integer DB_VERSION = 1;

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + NetworkLog.TABLE_NAME + " (" + NetworkLog.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NetworkLog.COLUMN_LOG + " TEXT)");
        db.execSQL("CREATE TABLE " + AppLog.TABLE_NAME + " (" + AppLog.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + AppLog.COLUMN_LOG + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NetworkLog.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + AppLog.TABLE_NAME);
        onCreate(db);
    }
}