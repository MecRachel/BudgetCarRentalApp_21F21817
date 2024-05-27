package com.example.budgetapp;

public class RachelBookingClass {
    private String bookingId;
    private String carModel;
    private String startDate;
    private String endDate;
    private double pricePerDay;

    public RachelBookingClass() {
    }

    public RachelBookingClass(String bookingId, String carModel, String startDate, String endDate, double pricePerDay) {
        this.bookingId = bookingId;
        this.carModel = carModel;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pricePerDay = pricePerDay;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    @Override
    public String toString() {
        return "RachelBookingClass{" +
                "bookingId='" + bookingId + '\'' +
                ", carModel='" + carModel + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", pricePerDay=" + pricePerDay +
                '}';
    }
}
