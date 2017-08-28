package com.rafakob.logify.repository.entity;

public class AppLog extends Log {
    public static final String LEVEL_VERBOSE = "verbose";
    public static final String LEVEL_INFO = "info";
    public static final String LEVEL_DEBUG = "debug";
    public static final String LEVEL_WARNING = "warning";
    public static final String LEVEL_ERROR = "error";

    private String level;
    private String tag;
    private String message;
    private Long timestamp;

    public String getLevel() {
        return level;
    }

    public void setLevel(String type) {
        this.level = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}