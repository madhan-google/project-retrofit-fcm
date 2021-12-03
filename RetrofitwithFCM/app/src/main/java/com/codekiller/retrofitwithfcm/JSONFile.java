package com.codekiller.retrofitwithfcm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JSONFile {

    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("userId")
    @Expose
    int userId;

    @SerializedName("title")
    @Expose
    String title;

    @SerializedName("body")
    @Expose
    String body;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
