package com.rafakob.logify.repository.entity;

public class AppLog extends Log {
    public static final String TYPE_VERBOSE = "verbose";
    public static final String TYPE_INFO = "info";
    public static final String TYPE_DEBUG = "debug";
    public static final String TYPE_WARNING = "warning";
    public static final String TYPE_ERROR = "error";

    private String type;
    private String message;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}