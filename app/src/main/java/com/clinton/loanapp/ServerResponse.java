package com.clinton.loanapp;

import com.google.gson.annotations.SerializedName;

//this class receives API responses

public class ServerResponse {
    @SerializedName("status")
    String status;

    @SerializedName("resultCode")
    int resultCode;

    @SerializedName("fullname")
    String fullname;

    @SerializedName("phone_number")
    String phone_number;

    @SerializedName("control_number")
    String control_number;

    public String getControl_number() {
        return control_number;
    }

    public String getStatus() {
        return status;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPhone_number() {
        return phone_number;
    }
}
