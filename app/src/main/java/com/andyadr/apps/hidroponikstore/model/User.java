package com.andyadr.apps.hidroponikstore.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    @SerializedName("uid")
    private String uid;

    @SerializedName(value = "username")
    private String username;

    @SerializedName(value = "password")
    private String password;

    @SerializedName(value = "email")
    private String email;

    @SerializedName(value = "jk")
    private String jk;

    @SerializedName(value = "tgl_lahir")
    private String tgl_lahir;

    @SerializedName(value = "foto")
    private String foto;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public User() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(email);
        dest.writeString(jk);
        dest.writeString(tgl_lahir);
        dest.writeString(foto);
    }

    protected User(Parcel in) {
        uid = in.readString();
        username = in.readString();
        password = in.readString();
        email = in.readString();
        jk = in.readString();
        tgl_lahir = in.readString();
        foto = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
