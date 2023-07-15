package com.example.hotelbooking.model;

import com.google.gson.annotations.SerializedName;

public class Room {
    @SerializedName("id")
    int id;
    @SerializedName("number")
    String number;
    @SerializedName("image")
    String image;
    @SerializedName("count_day")
    String count_day;
    @SerializedName("price")
    String price;
    @SerializedName("descrption")
    String descrption;
    @SerializedName("id_hotel")
    int id_hotel;
    @SerializedName("rating")
    String rating;

    @SerializedName("error_room")
    boolean error_room;
    @SerializedName("message_room")
    int message_room;

    public Room(int id, String number, String image, String count_day, String price, String descrption, int id_hotel, String rating, boolean error_room, int message_room) {
        this.id = id;
        this.number = number;
        this.image = image;
        this.count_day = count_day;
        this.price = price;
        this.descrption = descrption;
        this.id_hotel = id_hotel;
        this.rating = rating;
        this.error_room = error_room;
        this.message_room = message_room;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCount_day() {
        return count_day;
    }

    public void setCount_day(String count_day) {
        this.count_day = count_day;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public int getId_hotel() {
        return id_hotel;
    }

    public void setId_hotel(int id_hotel) {
        this.id_hotel = id_hotel;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public boolean isError_room() {
        return error_room;
    }

    public void setError_room(boolean error_room) {
        this.error_room = error_room;
    }

    public int getMessage_room() {
        return message_room;
    }

    public void setMessage_room(int message_room) {
        this.message_room = message_room;
    }
}
