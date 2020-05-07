package com.sapronov.tourist.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Coordinates {

    @PrimaryKey
    private long id;
    private double latitude;
    private double longitude;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
