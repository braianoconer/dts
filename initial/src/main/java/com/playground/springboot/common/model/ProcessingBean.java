package com.playground.springboot.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessingBean {

    private final String name;
    private String message;

    public ProcessingBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void setMessage() {
        message = "Oh this is sad, but farewell dear ";
    }

    private String getMessage() {
        return message;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return message + name;
    }
}