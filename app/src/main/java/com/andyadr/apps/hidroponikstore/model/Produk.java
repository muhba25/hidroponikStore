package com.andyadr.apps.hidroponikstore.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static android.provider.BlockedNumberContract.BlockedNumbers.COLUMN_ID;

@Entity(tableName = "produk", indices = @Index(value = {"kode_produk"}, unique = true))
public class Produk implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @SerializedName("kode_produk")
    private String kode_produk;

    @SerializedName("nama_produk")
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


    @SerializedName("total_bayar")
    private int total_bayar;

    @SerializedName("MemberId")
    private String MemberId;

    @SerializedName("payed")
    private String payed;

    @SerializedName("tgl_belanja")
    private String tgl_belanja;

    @SerializedName("kemas")
    private String kemas;

    @SerializedName("kirim")
    private String kirim;

    @SerializedName("cancel")
    private String cancel;

    @SerializedName("finish")
    private String finish;



    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

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

    public int getTotal_bayar() {
        return total_bayar;
    }

    public void setTotal_bayar(int total_bayar) {
        this.total_bayar = total_bayar;
    }


    public String getMemberId() {
        return MemberId;
    }

    public void setMemberId(String memberId) {
        MemberId = memberId;
    }

    public String getPayed() {
        return payed;
    }

    public void setPayed(String payed) {
        this.payed = payed;
    }

    public String getTgl_belanja() {
        return tgl_belanja;
    }

    public void setTgl_belanja(String tgl_belanja) {
        this.tgl_belanja = tgl_belanja;
    }

    public String getKemas() {
        return kemas;
    }

    public void setKemas(String kemas) {
        this.kemas = kemas;
    }

    public String getKirim() {
        return kirim;
    }

    public void setKirim(String kirim) {
        this.kirim = kirim;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.uid);
        dest.writeString(this.kode_produk);
        dest.writeString(this.nama_produk);
        dest.writeString(this.deskripsi);
        dest.writeInt(this.harga);
        dest.writeInt(this.sold);
        dest.writeDouble(this.rating);
        dest.writeString(this.jenis);
        dest.writeString(this.tgl_upload);
        dest.writeString(this.foto);
        dest.writeInt(this.total_bayar);
        dest.writeString(this.MemberId);
        dest.writeString(this.payed);
        dest.writeString(this.tgl_belanja);
        dest.writeString(this.kemas);
        dest.writeString(this.kirim);
        dest.writeString(this.cancel);
        dest.writeString(this.finish);
    }

    public Produk() {
    }

    protected Produk(Parcel in) {
        this.uid = in.readInt();
        this.kode_produk = in.readString();
        this.nama_produk = in.readString();
        this.deskripsi = in.readString();
        this.harga = in.readInt();
        this.sold = in.readInt();
        this.rating = in.readDouble();
        this.jenis = in.readString();
        this.tgl_upload = in.readString();
        this.foto = in.readString();
        this.total_bayar = in.readInt();
        this.MemberId = in.readString();
        this.payed = in.readString();
        this.tgl_belanja = in.readString();
        this.kemas = in.readString();
        this.kirim = in.readString();
        this.cancel = in.readString();
        this.finish = in.readString();
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
