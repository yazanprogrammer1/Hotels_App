package com.example.hotelbooking.model;

import com.google.gson.annotations.SerializedName;

public class Reservation {
    @SerializedName("id_reservation")
    int id;
    @SerializedName("id_user")
    int id_users;
    @SerializedName("id_room")
    int id_rooms;

    @SerializedName("number")
    String number;

    @SerializedName("image")
    String image;

    @SerializedName("price")
    String price;
    @SerializedName("count_day")
    String count_day;
    @SerializedName("nameHotel")
    String nameHotel;
    @SerializedName("rating")
    String rating;
    @SerializedName("count_person")
    String count_person;
    @SerializedName("Check_in")
    String check_in;
    @SerializedName("Check_out")
    String check_out;
    @SerializedName("isReservation")
    String isReservation;

    @SerializedName("name_user")
    String name_user;
    @SerializedName("phone_user")
    String phone_user;
    @SerializedName("image_user")
    String image_user;
    @SerializedName("error_reservation")
    boolean error_reservation;
    @SerializedName("message_reservation")
    String message_reservation;


    public Reservation(int id, int id_users, int id_rooms, String number, String image, String price, String count_day, String nameHotel, String rating, String count_person, String check_in, String check_out, String isReservation, String name_user, String phone_user, String image_user, boolean error_reservation, String message_reservation) {
        this.id = id;
        this.id_users = id_users;
        this.id_rooms = id_rooms;
        this.number = number;
        this.image = image;
        this.price = price;
        this.count_day = count_day;
        this.nameHotel = nameHotel;
        this.rating = rating;
        this.count_person = count_person;
        this.check_in = check_in;
        this.check_out = check_out;
        this.isReservation = isReservation;
        this.name_user = name_user;
        this.phone_user = phone_user;
        this.image_user = image_user;
        this.error_reservation = error_reservation;
        this.message_reservation = message_reservation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_users() {
        return id_users;
    }

    public void setId_users(int id_users) {
        this.id_users = id_users;
    }

    public int getId_rooms() {
        return id_rooms;
    }

    public void setId_rooms(int id_rooms) {
        this.id_rooms = id_rooms;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCount_day() {
        return count_day;
    }

    public void setCount_day(String count_day) {
        this.count_day = count_day;
    }

    public String getNameHotel() {
        return nameHotel;
    }

    public void setNameHotel(String nameHotel) {
        this.nameHotel = nameHotel;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCount_person() {
        return count_person;
    }

    public void setCount_person(String count_person) {
        this.count_person = count_person;
    }

    public String getCheck_in() {
        return check_in;
    }

    public void setCheck_in(String check_in) {
        this.check_in = check_in;
    }

    public String getCheck_out() {
        return check_out;
    }

    public void setCheck_out(String check_out) {
        this.check_out = check_out;
    }

    public String getIsReservation() {
        return isReservation;
    }

    public void setIsReservation(String isReservation) {
        this.isReservation = isReservation;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public String getPhone_user() {
        return phone_user;
    }

    public void setPhone_user(String phone_user) {
        this.phone_user = phone_user;
    }

    public String getImage_user() {
        return image_user;
    }

    public void setImage_user(String image_user) {
        this.image_user = image_user;
    }

    public boolean isError_reservation() {
        return error_reservation;
    }

    public void setError_reservation(boolean error_reservation) {
        this.error_reservation = error_reservation;
    }

    public String getMessage_reservation() {
        return message_reservation;
    }

    public void setMessage_reservation(String message_reservation) {
        this.message_reservation = message_reservation;
    }
}