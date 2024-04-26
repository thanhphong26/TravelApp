package com.travel.Model;

import java.sql.Timestamp;

public class HotelBookingModel {
    private int bookingId;
    private HotelModel hotel;
    private String bookingDate;
    private float totalPrice;
    private int numberOfAdults;
    private int numberOfChildren;
    private Timestamp createdAt;

    public HotelBookingModel() {
    }

    public HotelBookingModel(int bookingId, HotelModel hotel, String bookingDate, float totalPrice, int numberOfAdults, int numberOfChildren, Timestamp createdAt) {
        this.bookingId = bookingId;
        this.hotel = hotel;
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

    public HotelModel getHotel() {
        return hotel;
    }

    public void setHotel(HotelModel hotel) {
        this.hotel = hotel;
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
