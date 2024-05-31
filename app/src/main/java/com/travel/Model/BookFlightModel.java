package com.travel.Model;

public class BookFlightModel {
    private int bookingId;
    private UserModel user;
    private FlightModel flight;
    private int typeId;
    private int numberOfAdults;
    private int numberOfChilds;
    private float totalPrice;

    public BookFlightModel() {
    }

    public BookFlightModel(int bookingId, UserModel user, FlightModel flight, int typeId, int numberOfAdults, int numberOfChilds, float totalPrice) {
        this.bookingId = bookingId;
        this.user = user;
        this.flight = flight;
        this.typeId = typeId;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChilds = numberOfChilds;
        this.totalPrice = totalPrice;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public FlightModel getFlight() {
        return flight;
    }

    public void setFlight(FlightModel flight) {
        this.flight = flight;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getNumberOfAdults() {
        return numberOfAdults;
    }

    public void setNumberOfAdults(int numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    public int getNumberOfChilds() {
        return numberOfChilds;
    }

    public void setNumberOfChilds(int numberOfChilds) {
        this.numberOfChilds = numberOfChilds;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
