package com.rafakob.logify.entity;

public class AppLog {
    public static final String TABLE_NAME = "app_logs";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LOG = "log";

    private transient Long id;
    private String type = "type";
    private String body = "body";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
