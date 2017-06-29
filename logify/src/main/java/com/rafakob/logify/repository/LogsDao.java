package com.rafakob.logify.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rafakob.logify.repository.entity.Log;

import java.util.ArrayList;
import java.util.List;

class LogsDao {
    private SQLiteDatabase database;
    private SQLiteHelper helper;

    public LogsDao(Context context) {
        helper = new SQLiteHelper(context);
    }

    public void open() {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public void clear() {
        database.delete(LogEntry.TABLE_NAME, null, null);
    }

    public void delete(Log log) {
        database.delete(LogEntry.TABLE_NAME, LogEntry.COLUMN_ID + " = " + log.getId(), null);
    }

    public List<Log> select() {
        final List<Log> list = new ArrayList<>();

        final Cursor cursor = database.query(LogEntry.TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            list.add(createFromCursor(cursor));
            cursor.moveToNext();
        }

        cursor.close();
        return list;
    }

    public Log insert(Log object) {
        final ContentValues values = new ContentValues();
        final LogEntry entry = LogsTransformer.logToEntry(object);

        values.put(LogEntry.COLUMN_TYPE, entry.type);
        values.put(LogEntry.COLUMN_LOG, entry.log);

        final long id = database.insert(LogEntry.TABLE_NAME, null, values);

        final Cursor cursor = database.query(LogEntry.TABLE_NAME, null, LogEntry.COLUMN_ID + " = " + id, null, null, null, null);
        cursor.moveToFirst();

        final Log insertedObject = createFromCursor(cursor);
        cursor.close();
        return insertedObject;
    }

    private Log createFromCursor(Cursor cursor) {
        final LogEntry logEntry = new LogEntry();

        logEntry.id = cursor.getLong(0);
        logEntry.type = cursor.getString(1);
        logEntry.log = cursor.getString(2);

        return LogsTransformer.logFromEntry(logEntry);
    }

    public static class LogEntry {
        public static final String TABLE_NAME = "logs";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_LOG = "log";

        public Long id;
        public String type;
        public String log;
    }
}
