package com.rafakob.logify.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseDao<T> {
    protected SQLiteDatabase database;
    protected SQLiteHelper helper;
    protected Gson gson;

    public BaseDao(Context context) {
        helper = new SQLiteHelper(context);
        gson = new GsonBuilder().create();
    }

    public void open() {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public void clear() {
        database.delete(getTableName(), null, null);
    }

    public void delete(long id) {
        database.delete(getTableName(), getColumnId() + " = " + id, null);
    }

    public List<T> select() {
        final List<T> list = new ArrayList<>();

        final Cursor cursor = database.query(getTableName(), null, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            list.add(createFromCursor(cursor));
            cursor.moveToNext();
        }

        cursor.close();
        return list;
    }

    protected T insert(T object) {
        final ContentValues values = new ContentValues();
        values.put(getColumnLog(), gson.toJson(object));

        final long id = database.insert(getTableName(), null, values);

        final Cursor cursor = database.query(getTableName(), null, getColumnId() + " = " + id, null, null, null, null);
        cursor.moveToFirst();

        final T insertedObject = createFromCursor(cursor);

        cursor.close();

        return insertedObject;
    }

    protected abstract String getTableName();

    protected abstract String getColumnId();

    protected abstract String getColumnLog();

    protected abstract T createFromCursor(Cursor cursor);
}
