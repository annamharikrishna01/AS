package com.hk.Models;

/**
 * Created by Hari on 11/25/13.
 */
public class DAddress {
    private String address;
    private String type;
    public DAddress() {
        super();
    }
    public DAddress(String address, String type) {
        super();
        this.address = address;
        this.type = type;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return "DAddress [address=" + address + ", type=" + type + "]";
    }
}
