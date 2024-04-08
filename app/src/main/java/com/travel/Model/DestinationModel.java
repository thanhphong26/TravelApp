package com.travel.Model;

import java.sql.Timestamp;

public class DestinationModel {
    private int destinationId;
    private String name;
    private String image;
    private Timestamp createdAt;

    public DestinationModel() {
    }

    public DestinationModel(int destinationId, String name, String image, Timestamp createdAt) {
        this.destinationId = destinationId;
        this.name = name;
        this.image = image;
        this.createdAt = createdAt;
    }

    public DestinationModel(String name, String image, Timestamp createdAt) {
        this.name = name;
        this.image = image;
        this.createdAt = createdAt;
    }

    public int getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(int destinationId) {
        this.destinationId = destinationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
