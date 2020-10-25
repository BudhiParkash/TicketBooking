package com.example.ticketbokking.model;

import com.google.gson.annotations.SerializedName;

public class Booking {
    @SerializedName("Price")
    String Price;

    @SerializedName("FlightFrom")
    String FlightFrom;

    @SerializedName("FlightTo")
    String FlightTo;

    @SerializedName("DestinationTime")
    String DestinationTime;

    @SerializedName("Duration")
    String Duration;

    @SerializedName("FlightName")
    String FlightName;

    @SerializedName("DepatureTime")
    String DepatureTime;

    public Booking(){

    }

    public Booking(String price, String flightFrom, String flightTo, String destinationTime, String duration, String flightName, String depatureTime) {
        Price = price;
        FlightFrom = flightFrom;
        FlightTo = flightTo;
        DestinationTime = destinationTime;
        Duration = duration;
        FlightName = flightName;
        DepatureTime = depatureTime;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getFlightFrom() {
        return FlightFrom;
    }

    public void setFlightFrom(String flightFrom) {
        FlightFrom = flightFrom;
    }

    public String getFlightTo() {
        return FlightTo;
    }

    public void setFlightTo(String flightTo) {
        FlightTo = flightTo;
    }

    public String getDestinationTime() {
        return DestinationTime;
    }

    public void setDestinationTime(String destinationTime) {
        DestinationTime = destinationTime;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public String getFlightName() {
        return FlightName;
    }

    public void setFlightName(String flightName) {
        FlightName = flightName;
    }

    public String getDepatureTime() {
        return DepatureTime;
    }

    public void setDepatureTime(String depatureTime) {
        DepatureTime = depatureTime;
    }
}
