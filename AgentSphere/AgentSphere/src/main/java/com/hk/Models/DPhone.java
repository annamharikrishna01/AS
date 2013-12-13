package com.hk.Models;

/**
 * Created by Hari on 11/25/13.
 */
public class DPhone {
    private String number;
    private String type;

    public DPhone() {
    }
    public DPhone(String number, String type) {
        super();
        this.number = number;
        this.type = type;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return "DPhones [number=" + number + ", type=" + type + "]";
    }
}
