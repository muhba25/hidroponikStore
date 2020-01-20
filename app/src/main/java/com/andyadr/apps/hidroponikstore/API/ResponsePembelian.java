package com.andyadr.apps.hidroponikstore.API;

import com.andyadr.apps.hidroponikstore.model.Produk;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponsePembelian {
    @SerializedName("data")
    ArrayList<Produk> Mpembelian;
    @SerializedName("total")
    Integer totaldatapembelian;

    public ArrayList<Produk> getMpembelian() {
        return Mpembelian;
    }
    public void setKontak(ArrayList<Produk> produk) {
        Mpembelian = produk;
    }

    public Integer getTotaldatapembelian() {
        return totaldatapembelian;
    }
}
