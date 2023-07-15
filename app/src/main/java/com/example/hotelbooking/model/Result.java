package com.example.hotelbooking.model;

import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @SerializedName("imagesUser")
    private String imagesUser;
    @SerializedName("user")
    private User user;

    public Result(Boolean error, String message, String imagesUser, User user) {
        this.error = error;
        this.message = message;
        this.imagesUser = imagesUser;
        this.user = user;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImagesUser() {
        return imagesUser;
    }

    public void setImagesUser(String imagesUser) {
        this.imagesUser = imagesUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
