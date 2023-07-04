package com.example.Holders;

import com.google.gson.annotations.SerializedName;

public class RootModel {
    @SerializedName("to")
    private String token;

    @SerializedName("notification")
    private NotificationModel notification;

    public RootModel(String token, NotificationModel notification) {
        this.token = token;
        this.notification = notification;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public NotificationModel getNotification() {
        return this.notification;
    }

    public void setNotification(NotificationModel notification) {
        this.notification = notification;
    }
}
