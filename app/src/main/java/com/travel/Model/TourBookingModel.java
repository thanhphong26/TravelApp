package com.travel.Model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TourBookingModel {
    private int bookingId;
    private TourModel tour;
    private String bookingDate;
    private float totalPrice;
    private int numberOfAdults;
    private int numberOfChildren;
    private Timestamp createdAt;
    public TourBookingModel() {
    }

    public TourBookingModel(int bookingId, TourModel tour, String bookingDate, float totalPrice, int numberOfAdults, int numberOfChildren, Timestamp createdAt) {
        this.bookingId = bookingId;
        this.tour = tour;
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

    public TourModel getTour() {
        return tour;
    }

    public void setTour(TourModel tour) {
        this.tour = tour;
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
