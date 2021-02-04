package com.example.myapplication;

public class ShoppingCart {

    private String client;
    private String mobile;

    public ShoppingCart(String client, String mobile) {
        this.client = client;
        this.mobile = mobile;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
