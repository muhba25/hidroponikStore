package com.andyadr.apps.hidroponikstore.API;

import com.andyadr.apps.hidroponikstore.model.Produk;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseProduk {
    @SerializedName("data")
    ArrayList<Produk> results;
    @SerializedName("status")
    String status;
    @SerializedName("message")
    String message;

    public ArrayList<Produk> getListDataProduk() {
        return results;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setListDataProduk(ArrayList<Produk> listDataProduk) {
        this.results = listDataProduk;
    }
}
