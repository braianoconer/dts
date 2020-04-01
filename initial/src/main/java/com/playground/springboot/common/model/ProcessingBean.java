package com.playground.springboot.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessingBean {

    private String UUID;

    private long lightTimestamp;

    private long mediumTimestamp;

    private long heavyTimestamp;

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public long getLightTimestamp() {
        return lightTimestamp;
    }

    public void setLightTimestamp(long lightTimestamp) {
        this.lightTimestamp = lightTimestamp;
    }

    public long getMediumTimestamp() {
        return mediumTimestamp;
    }

    public void setMediumTimestamp(long mediumTimestamp) {
        this.mediumTimestamp = mediumTimestamp;
    }

    public long getHeavyTimestamp() {
        return heavyTimestamp;
    }

    public void setHeavyTimestamp(long heavyTimestamp) {
        this.heavyTimestamp = heavyTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessingBean that = (ProcessingBean) o;
        return UUID.equals(that.UUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(UUID);
    }

    @Override
    public String toString() {
        return "ProcessingBean{" +
                "UUID='" + UUID + '\'' +
                ", lightTimestamp=" + lightTimestamp +
                ", mediumTimestamp=" + mediumTimestamp +
                ", heavyTimestamp=" + heavyTimestamp +
                '}';
    }
}