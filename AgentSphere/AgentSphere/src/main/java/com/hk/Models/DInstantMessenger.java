package com.hk.Models;

/**
 * Created by Hari on 11/25/13.
 */
public class DInstantMessenger {

    private String name;
    private String type;

    public DInstantMessenger() {
        super();
    }

    public DInstantMessenger(String name, String type) {
        super();
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DInstantMessenger [name=" + name + ", type=" + type + "]";
    }
}
