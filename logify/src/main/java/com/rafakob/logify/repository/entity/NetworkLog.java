package com.rafakob.logify.repository.entity;

public class NetworkLog extends Log {

    private Long duration;

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}