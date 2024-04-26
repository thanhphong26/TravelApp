package com.travel.Model;

import java.sql.Timestamp;

public class LoyaltyPointModel {
    private int pointId;
    private int userId;
    private int points;
    private Timestamp createdAt;

    public LoyaltyPointModel() {
    }

    public LoyaltyPointModel(int pointId, int userId, int points, Timestamp createdAt) {
        this.pointId = pointId;
        this.points = points;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public LoyaltyPointModel(int pointId, int userId, int points) {
        this.pointId = pointId;
        this.points = points;
        this.userId = userId;
    }

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
