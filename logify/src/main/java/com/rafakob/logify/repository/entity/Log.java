package com.rafakob.logify.repository.entity;

import java.io.Serializable;

public abstract class Log implements Serializable {
    private transient Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}