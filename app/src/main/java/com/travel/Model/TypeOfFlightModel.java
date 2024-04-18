package com.travel.Model;

public class TypeOfFlightModel {
    private int typeId;
    private FlightModel flightModel;
    private String type;

    public TypeOfFlightModel() {
    }

    public TypeOfFlightModel(int typeId, FlightModel flightModel, String type) {
        this.typeId = typeId;
        this.flightModel = flightModel;
        this.type = type;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public FlightModel getFlightModel() {
        return flightModel;
    }

    public void setFlightModel(FlightModel flightModel) {
        this.flightModel = flightModel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
