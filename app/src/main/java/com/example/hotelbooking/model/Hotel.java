package com.example.hotelbooking.model;

import com.google.gson.annotations.SerializedName;

public class Hotel {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("rating")
    String rating;
    @SerializedName("address")
    String address;
    @SerializedName("phone")
    String phone;
    @SerializedName("image")
    String image;

    @SerializedName("error_hotel")
    boolean error_hotel;
    @SerializedName("message_hotel")
    String message_hotel;

    public Hotel(int id, String name, String rating, String address, String phone, String image, boolean error_hotel, String message_hotel) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.address = address;
        this.phone = phone;
        this.image = image;
        this.error_hotel = error_hotel;
        this.message_hotel = message_hotel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean getError_hotel() {
        return error_hotel;
    }

    public void setError_hotel(boolean error_hotel) {
        this.error_hotel = error_hotel;
    }

    public String getMessage_hotel() {
        return message_hotel;
    }

    public void setMessage_hotel(String message_hotel) {
        this.message_hotel = message_hotel;
    }
}