package com.travel.Model;

public class HotelModel {
    private  int hotelId;
    private String name, address, image, description;
    private float rating;
    private float price;
    private float latitude, longitude;
    private DestinationModel destination;

    public HotelModel() {
    }

    public HotelModel(int hotelId, String name, String address, String image, String description, float rating, float price, float latitude, float longitude, DestinationModel destination) {
        this.hotelId = hotelId;
        this.name = name;
        this.address = address;
        this.image = image;
        this.description = description;
        this.rating = rating;
        this.price = price;
        this.latitude = latitude;
        this.longitude = longitude;
        this.destination = destination;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public DestinationModel getDestination() {
        return destination;
    }

    public void setDestination(DestinationModel destination) {
        this.destination = destination;
    }
}
