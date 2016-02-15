package com.globalgrupp.couriers.app.classes;

import java.io.Serializable;

/**
 * Created by п on 24.12.2015.
 */
public class SimpleGeoCoords implements Serializable {
    private double longitude;

    private double latitude;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public SimpleGeoCoords() {

    }
}
