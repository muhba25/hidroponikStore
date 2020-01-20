package com.andyadr.apps.hidroponikstore.API;

import com.andyadr.apps.hidroponikstore.model.Keranjang;
import com.andyadr.apps.hidroponikstore.model.Produk;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseHistory {
    @SerializedName("data")
    ArrayList<Produk> Mproduk;
    @SerializedName("total")
    Integer totaldataproduk;

    public ArrayList<Produk> getMproduk() {
        return Mproduk;
    }
    public void setKontak(ArrayList<Produk> produk) {
        Mproduk = produk;
    }

    public Integer getTotaldataproduk() {
        return totaldataproduk;
    }
}
