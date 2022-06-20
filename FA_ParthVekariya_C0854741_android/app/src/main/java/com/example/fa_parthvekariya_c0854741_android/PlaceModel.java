package com.example.fa_parthvekariya_c0854741_android;

import android.graphics.Bitmap;

public class PlaceModel {
    private String placeName;
    private String placeDescription;
    private String placeAddress;
    private String isVisited;

    public PlaceModel(String placeName, String placeDescription, String placeAddress, String isVisited) {
        this.placeName = placeName;
        this.placeDescription = placeDescription;
        this.placeAddress = placeAddress;
        this.isVisited = isVisited;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) {
        this.placeDescription = placeDescription;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public String getVisited() {
        return isVisited;
    }

    public void setVisited(String visited) {
        isVisited = visited;
    }
}
