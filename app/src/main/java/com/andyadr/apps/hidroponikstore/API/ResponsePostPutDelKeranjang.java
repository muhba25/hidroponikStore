package com.andyadr.apps.hidroponikstore.API;

import com.google.gson.annotations.SerializedName;

public class ResponsePostPutDelKeranjang {
    @SerializedName("success")
    boolean success;
    @SerializedName("message")
    String message;
    @SerializedName("status")
    String status;

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getStatus() {
        return status;
    }
}
