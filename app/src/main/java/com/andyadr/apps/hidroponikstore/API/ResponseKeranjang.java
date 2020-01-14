package com.andyadr.apps.hidroponikstore.API;

import com.andyadr.apps.hidroponikstore.model.Keranjang;
import com.andyadr.apps.hidroponikstore.model.User;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseKeranjang {
    @SerializedName("data")
    ArrayList<Keranjang> Mkeranjang;
    @SerializedName("total")
    Integer totaldatakeranjang;

    public ArrayList<Keranjang> getMkeranjang() {
        return Mkeranjang;
    }
    public void setKontak(ArrayList<Keranjang> keranjang) {
        Mkeranjang = keranjang;
    }

    public Integer getTotaldatakeranjang() {
        return totaldatakeranjang;
    }
}
