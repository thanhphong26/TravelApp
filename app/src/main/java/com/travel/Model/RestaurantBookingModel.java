package com.travel.Model;

import java.sql.Timestamp;

public class RestaurantBookingModel {
    private int bookingId;
    private RestaurantModel restaurant;
    private String bookingDate;
    private float totalPrice;
    private int numberOfAdults;
    private int numberOfChildren;
    private Timestamp createdAt;
    public RestaurantBookingModel() {
    }

    public RestaurantBookingModel(int bookingId, RestaurantModel restaurant, String bookingDate, float totalPrice, int numberOfAdults, int numberOfChildren, Timestamp createdAt) {
        this.bookingId = bookingId;
        this.restaurant = restaurant;
        this.bookingDate = bookingDate;
        this.totalPrice = totalPrice;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChildren = numberOfChildren;
        this.createdAt = createdAt;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public RestaurantModel getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantModel restaurant) {
        this.restaurant = restaurant;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getNumberOfAdults() {
        return numberOfAdults;
    }

    public void setNumberOfAdults(int numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
