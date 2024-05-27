package com.example.budgetapp;

public class RachelCar {
    private String model21F21817;
    private String registration21F21817No;
    private String brand21F21817;
    private double price21F21817;
    private int imageRes21F21817;

    public RachelCar(String rachelmodel, String rachelregistration, String rachelbrand, double rachelprice, int rachelimageResId) {
        this.model21F21817 = rachelmodel;
        this.registration21F21817No = rachelregistration;
        this.brand21F21817 = rachelbrand;
        this.price21F21817 = rachelprice;
        this.imageRes21F21817 = rachelimageResId;
    }

    public String getModel() {
        return model21F21817;
    }

    public void setModel(String model) {
        this.model21F21817 = model;
    }

    public String getRegistrationNo() {
        return registration21F21817No;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registration21F21817No = registrationNo;
    }

    public String getBrand() {
        return brand21F21817;
    }

    public void setBrand(String brand) {
        this.brand21F21817 = brand;
    }

    public double getPrice() {
        return price21F21817;
    }

    public void setPrice(double price) {
        this.price21F21817 = price;
    }

    public int getImageResId() {
        return imageRes21F21817;
    }

    public void setImageResId(int imageResId) {
        this.imageRes21F21817 = imageResId;

    }
}
