package com.andyadr.apps.hidroponikstore.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Keranjang implements Parcelable {
    @SerializedName("kode_produk")
    private String kode_produk_keranjang;

    @SerializedName("MemberId")
    private String memberid_keranjang;

    @SerializedName(value = "nama_produk")
    private String nama_produk_keranjang;

    @SerializedName(value = "deskripsi")
    private String deskripsi_keranjang;

    @SerializedName("harga")
    private int harga_keranjang;

    @SerializedName("jenis")
    private String jenis_keranjang;

    @SerializedName("sold")
    private int sold_keranjang;

    @SerializedName("rating")
    private double rating_keranjang;

    @SerializedName("jumlah")
    private String jumlah_keranjang;

    @SerializedName("foto")
    private String foto_keranjang;


    public String getKode_produk_keranjang() {
        return kode_produk_keranjang;
    }

    public void setKode_produk_keranjang(String kode_produk_keranjang) {
        this.kode_produk_keranjang = kode_produk_keranjang;
    }

    public String getMemberid_keranjang() {
        return memberid_keranjang;
    }

    public void setMemberid_keranjang(String memberid_keranjang) {
        this.memberid_keranjang = memberid_keranjang;
    }

    public String getNama_produk_keranjang() {
        return nama_produk_keranjang;
    }

    public void setNama_produk_keranjang(String nama_produk_keranjang) {
        this.nama_produk_keranjang = nama_produk_keranjang;
    }

    public String getDeskripsi_keranjang() {
        return deskripsi_keranjang;
    }

    public void setDeskripsi_keranjang(String deskripsi_keranjang) {
        this.deskripsi_keranjang = deskripsi_keranjang;
    }

    public int getHarga_keranjang() {
        return harga_keranjang;
    }

    public void setHarga_keranjang(int harga_keranjang) {
        this.harga_keranjang = harga_keranjang;
    }

    public String getJenis_keranjang() {
        return jenis_keranjang;
    }

    public void setJenis_keranjang(String jenis_keranjang) {
        this.jenis_keranjang = jenis_keranjang;
    }

    public int getSold_keranjang() {
        return sold_keranjang;
    }

    public void setSold_keranjang(int sold_keranjang) {
        this.sold_keranjang = sold_keranjang;
    }

    public double getRating_keranjang() {
        return rating_keranjang;
    }

    public void setRating_keranjang(double rating_keranjang) {
        this.rating_keranjang = rating_keranjang;
    }

    public String getJumlah_keranjang() {
        return jumlah_keranjang;
    }

    public void setJumlah_keranjang(String jumlah_keranjang) {
        this.jumlah_keranjang = jumlah_keranjang;
    }

    public String getFoto_keranjang() {
        return foto_keranjang;
    }

    public void setFoto_keranjang(String foto_keranjang) {
        this.foto_keranjang = foto_keranjang;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kode_produk_keranjang);
        dest.writeString(memberid_keranjang);
        dest.writeString(nama_produk_keranjang);
        dest.writeString(deskripsi_keranjang);
        dest.writeInt(harga_keranjang);
        dest.writeString(jenis_keranjang);
        dest.writeInt(sold_keranjang);
        dest.writeDouble(rating_keranjang);
        dest.writeString(jumlah_keranjang);
        dest.writeString(foto_keranjang);
    }

    protected Keranjang(Parcel in) {
        kode_produk_keranjang = in.readString();
        memberid_keranjang = in.readString();
        nama_produk_keranjang = in.readString();
        deskripsi_keranjang = in.readString();
        harga_keranjang = in.readInt();
        jenis_keranjang = in.readString();
        sold_keranjang = in.readInt();
        rating_keranjang = in.readDouble();
        jumlah_keranjang = in.readString();
        foto_keranjang = in.readString();
    }

    public static final Creator<Keranjang> CREATOR = new Creator<Keranjang>() {
        @Override
        public Keranjang createFromParcel(Parcel in) {
            return new Keranjang(in);
        }

        @Override
        public Keranjang[] newArray(int size) {
            return new Keranjang[size];
        }
    };
}
