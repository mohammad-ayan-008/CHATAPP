package com.example.Holders;

import com.google.gson.annotations.SerializedName;

public class NotificationModel {
    @SerializedName("title")
    String Title;

    @SerializedName("body")
    String Text;

    public NotificationModel(String Title, String Text) {
        this.Title = Title;
        this.Text = Text;
    }

    public String getTitle() {
        return this.Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getText() {
        return this.Text;
    }

    public void setText(String Text) {
        this.Text = Text;
    }
}
