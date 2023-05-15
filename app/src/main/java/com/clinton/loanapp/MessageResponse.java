package com.clinton.loanapp;

import com.google.gson.annotations.SerializedName;

public class MessageResponse {
    @SerializedName("status")
    String status;

    @SerializedName("resultCode")
    int resultCode;

    @SerializedName("date")
    String date;

    @SerializedName("message_status")
    String message_status;

    @SerializedName("heading")
    String heading;

    public String getStatus() {

        return status;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getDate() {

        return date;
    }

    public String getMessage_status() {

        return message_status;
    }

    public String getHeading() {
        return heading;
    }
}
