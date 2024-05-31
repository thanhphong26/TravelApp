package com.travel.Model;

public class AirportModel {
    private String airportCode;
    private DestinationModel destination;
    private String name;
    private String description;

    public AirportModel() {
    }

    public AirportModel(String airportCode, DestinationModel destination, String name, String description) {
        this.airportCode = airportCode;
        this.destination = destination;
        this.name = name;
        this.description = description;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public DestinationModel getDestination() {
        return destination;
    }

    public void setDestination(DestinationModel destination) {
        this.destination = destination;
    }

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
}
