package com.andyadr.apps.hidroponikstore.API;

import com.andyadr.apps.hidroponikstore.model.User;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseGetUser {
    @SerializedName("data")
    ArrayList<User> mUser;
    @SerializedName("total")
    Integer tous;

    public ArrayList<User> getUser() {
   return mUser;
    }
    public void setKontak(ArrayList<User> user) {
     mUser = user;
     }

    public Integer getTous() {
        return tous;
    }
}
