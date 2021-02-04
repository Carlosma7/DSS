package com.example.myapplication;

import java.io.Serializable;

public class Mobile   implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    public Mobile(Long id, String brand, String model, String os, String color, int year, int price) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.os = os;
        this.color = color;
        this.year = year;
        this.price = price;
    }

    public Mobile(String brand, String model, String os, String color, int year, int price) {
        this.brand = brand;
        this.model = model;
        this.os = os;
        this.color = color;
        this.year = year;
        this.price = price;
    }

    private String brand;

    
    private String model;

    
    private String os;

    
    private String color;

    
    private int year;

    
    private int price;

    public Mobile() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getOS() {
        return os;
    }
    public void setOS(String os) {
        this.os = os;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Mobile{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", os='" + os + '\'' +
                ", color='" + color + '\'' +
                ", year=" + year +
                ", price=" + price +
                '}';
    }
}
