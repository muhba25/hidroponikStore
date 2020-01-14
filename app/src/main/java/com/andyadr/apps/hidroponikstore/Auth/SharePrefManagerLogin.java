package com.andyadr.apps.hidroponikstore.Auth;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePrefManagerLogin {
    public static final String SP_User_APP = "spUserApp";

    public static final String SP_Username = "spUsername";
    public static final String SP_Email = "spEmail";
    public static final String SP_Tgl_lahir = "spTgll";
    public static final String SP_Jk = "spJk";
    public static final String SP_Pass = "spPass";
    public static final String SP_KodeUser = "spKodeuser";
    public static final String SP_Foto = "spFoto";

    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharePrefManagerLogin(Context context){
        sp = context.getSharedPreferences(SP_User_APP, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSPNama(){
        return sp.getString(SP_Username, "");
    }

    public String getSPEmail(){
        return sp.getString(SP_Email, "");
    }
    public String getSPKodeuser(){
        return sp.getString(SP_KodeUser, "");
    }
    public String getSP_Tgl_lahir(){
        return sp.getString(SP_Tgl_lahir, "");
    }
    public String getSP_Jk(){
        return sp.getString(SP_Jk, "");
    }
    public String getSP_Foto(){
        return sp.getString(SP_Foto, "");
    }
    public String getSP_Pass(){
        return sp.getString(SP_Pass, "");
    }

    public Boolean getSPSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }

    public void clearLoggedInUser (Context context){
        sp = context.getSharedPreferences(SP_User_APP, Context.MODE_PRIVATE);
        spEditor.clear();
        spEditor.commit();
    }
}
