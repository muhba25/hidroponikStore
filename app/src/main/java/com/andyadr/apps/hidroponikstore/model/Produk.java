package com.andyadr.apps.hidroponikstore.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static android.provider.BlockedNumberContract.BlockedNumbers.COLUMN_ID;

public class Produk implements Parcelable {

    @SerializedName("kode_produk")
    private String kode_produk;

    @SerializedName(value = "nama_produk")
    private String nama_produk;

    @SerializedName(value = "deskripsi")
    private String deskripsi;

    @SerializedName("harga")
    private int harga;

    @SerializedName("jenis")
    private String jenis;

    @SerializedName("sold")
    private int sold;

    @SerializedName("rating")
    private double rating;

    @SerializedName("tgl_upload")
    private String tgl_upload;

    @SerializedName("foto")
    private String foto;

    public String getKode_produk() {
        return kode_produk;
    }

    public void setKode_produk(String kode_produk) {
        this.kode_produk = kode_produk;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getTgl_upload() {
        return tgl_upload;
    }

    public void setTgl_upload(String tgl_upload) {
        this.tgl_upload = tgl_upload;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kode_produk);
        dest.writeString(this.nama_produk);
        dest.writeString(this.deskripsi);
        dest.writeInt(this.harga);
        dest.writeInt(this.sold);
        dest.writeDouble(this.rating);
        dest.writeString(this.jenis);
        dest.writeString(this.tgl_upload);
        dest.writeString(this.foto);
    }

    public Produk() {
    }

    protected Produk(Parcel in) {
        this.kode_produk = in.readString();
        this.nama_produk = in.readString();
        this.deskripsi = in.readString();
        this.harga = in.readInt();
        this.sold = in.readInt();
        this.rating = in.readDouble();
        this.jenis = in.readString();
        this.tgl_upload = in.readString();
        this.foto = in.readString();
    }

    public static final Creator<Produk> CREATOR = new Creator<Produk>() {
        @Override
        public Produk createFromParcel(Parcel source) {
            return new Produk(source);
        }

        @Override
        public Produk[] newArray(int size) {
            return new Produk[size];
        }
    };
}
