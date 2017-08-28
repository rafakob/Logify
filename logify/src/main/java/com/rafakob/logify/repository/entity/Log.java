package com.rafakob.logify.repository.entity;

public abstract class Log {
    private transient Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}