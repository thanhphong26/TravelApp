package com.travel.Model;

public class ImageTourModel {
    private int imageId;
    private TourModel tour;
    private String image;

    public ImageTourModel() {
    }

    public ImageTourModel(TourModel tour, String image) {
        this.tour = tour;
        this.image = image;
    }

    public ImageTourModel(int imageId, TourModel tour, String image) {
        this.imageId = imageId;
        this.tour = tour;
        this.image = image;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public TourModel getTour() {
        return tour;
    }

    public void setTour(TourModel tour) {
        this.tour = tour;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
