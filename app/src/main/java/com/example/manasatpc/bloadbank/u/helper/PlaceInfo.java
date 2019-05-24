package com.example.manasatpc.bloadbank.u.helper;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

import java.net.URL;

public class PlaceInfo {
    private String id;
    private String name;
    private String address;
    private CharSequence attrbuties;
    private String phoneNumber;
    private LatLng latLng;
    private float rate;
    private Uri url;

    @Override
    public String toString() {
        return "PlaceInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", attrbuties='" + attrbuties + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", latLng=" + latLng +
                ", rate=" + rate +
                ", url=" + url +
                '}';
    }

    public Uri getUrl() {
        return url;
    }

    public void setUrl(Uri url) {
        this.url = url;
    }

    public PlaceInfo(String id, String name, String address, String attrbuties, String phoneNumber, LatLng latLng, float rate, Uri url) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.attrbuties = attrbuties;
        this.phoneNumber = phoneNumber;
        this.latLng = latLng;
        this.rate = rate;
        this.url = url;
    }

    public PlaceInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CharSequence getAttrbuties() {
        return attrbuties;
    }

    public void setAttrbuties(CharSequence attrbuties) {
        this.attrbuties = attrbuties;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
