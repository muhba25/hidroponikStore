package com.andyadr.apps.hidroponikstore.API;

import com.google.gson.annotations.SerializedName;

public class ResponseFotoProfil {
    @SerializedName("success")
    boolean success;
    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }
}
