package com.category.base.net;

/**
 * Created by fengyin on 16-4-20.
 */
public class Params {
    private String key;
    private String value;

    public Params(){

    }

    public Params(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
