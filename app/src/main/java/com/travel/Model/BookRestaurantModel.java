package com.travel.Model;

public class BookRestaurantModel {
    private int bookingId;
    private UserModel userId;
    private RestaurantModel restaurantId;
    private int numberOfAdults;
    private int numberOfChilds;
    private float totalPrice;


    public BookRestaurantModel(int bookingId, UserModel userId, RestaurantModel restaurantId, int numberOfAdults, int numberOfChilds, float totalPrice) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.numberOfAdults = numberOfAdults;
        this.numberOfChilds = numberOfChilds;
        this.totalPrice = totalPrice;
    }


    public BookRestaurantModel() {
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public UserModel getUserId() {
        return userId;
    }

    public void setUserId(UserModel userId) {
        this.userId = userId;
    }

    public RestaurantModel getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(RestaurantModel restaurantId) {
        this.restaurantId = restaurantId;
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
