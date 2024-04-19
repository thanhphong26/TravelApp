package com.travel.Model;

public class TourModel {
    private int tourId;
//    private DestinationModel destination;
    private int destinationId;
    private String name,description, image;
    private float rating;
    private float price;
    public TourModel() {
    }
    public TourModel(int tourId, int destinationId, String name, String description, String image, float rating, float price) {
        this.tourId = tourId;
        this.destinationId = destinationId;
        this.name = name;
        this.description = description;
        this.image = image;
        this.rating = rating;
        this.price = price;
    }
    public int getDestinationId() {
        return destinationId;
    }
    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }
//       public TourModel(int tourId, DestinationModel destination, String name, String description, String image, float rating, float price) {
//        this.tourId = tourId;
//        this.destination = destination;
//        this.name = name;
//        this.description = description;
//        this.image = image;
//        this.rating = rating;
//        this.price = price;
//    }
//
//    public TourModel(DestinationModel destination, String name, String description, String image, float rating, float price) {
//        this.destination = destination;
//        this.name = name;
//        this.description = description;
//        this.image = image;
//        this.rating = rating;
//        this.price = price;
//    }


    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

//    public DestinationModel getDestination() {
//        return destination;
//    }
//
//    public void setDestination(DestinationModel destination) {
//        this.destination = destination;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

}
