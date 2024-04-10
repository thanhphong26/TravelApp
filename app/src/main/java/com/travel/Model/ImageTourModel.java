package com.travel.Model;

public class ImageTourModel {
    private int imageId;
    private int  tourId;
    private String image;

    public ImageTourModel() {
    }

    public ImageTourModel(int imageId, int tourId, String image) {
        this.imageId = imageId;
        this.tourId = tourId;
        this.image = image;
    }

    public ImageTourModel(int tourId, String image) {
        this.tourId = tourId;
        this.image = image;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
