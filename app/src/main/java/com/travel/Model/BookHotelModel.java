package com.travel.Model;

public class BookHotelModel {
    private int bookingId;
    private UserModel user;
    private HotelModel hotel;
    private int numberOfAdults;
    private int numberOfChilds;
    private int numberOfRooms;
    private float totalPrice;

    public BookHotelModel() {
    }

    public BookHotelModel(int bookingId, UserModel userModel, HotelModel hotelModel, int numberOfAdults, int numberOfChilds, int numberOfRooms, float totalPrice) {
        this.bookingId = bookingId;
        this.user = userModel;
        this.hotel = hotelModel;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChilds = numberOfChilds;
        this.numberOfRooms = numberOfRooms;
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

    public HotelModel getHotel() {
        return hotel;
    }

    public void setHotel(HotelModel hotel) {
        this.hotel = hotel;
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

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
