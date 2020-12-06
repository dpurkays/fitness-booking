package com.example.iat359project;

import com.google.android.gms.maps.model.LatLng;

/*
Basic information about Gym
 */

public class Gym {
    private String name;
    private LatLng latLng;

    public Gym(String name, LatLng latLng) {
        this.name = name;
        this.latLng = latLng;
    }

    public String getName() {
        return name;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    @Override
    public String toString() {
        return "Gym{" +
                "name='" + name + '\'' +
                ", latLng=" + latLng +
                '}';
    }
}
