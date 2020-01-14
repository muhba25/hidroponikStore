package com.andyadr.apps.hidroponikstore.API;

import com.andyadr.apps.hidroponikstore.model.User;
import com.google.gson.annotations.SerializedName;

public class ResponsePostPutDelUser {

//    @SerializedName("result")
//    User mUser;
    @SerializedName("total")
    Integer totaluser;

    public Integer getTotaluser() {
        return totaluser;
    }
    public void setTotaluser(Integer  user) {
        this.totaluser = user;
    }
//    public User getUser() {
//        return mUser;
//    }
//    public void setKontak(User user) {
//        mUser = user;
//    }
}
