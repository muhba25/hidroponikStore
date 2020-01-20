package com.andyadr.apps.hidroponikstore;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andyadr.apps.hidroponikstore.API.ApiClient;
import com.andyadr.apps.hidroponikstore.API.ApiEndpoints;
import com.andyadr.apps.hidroponikstore.API.ResponseFotoProfil;
import com.andyadr.apps.hidroponikstore.API.ResponsePostPutDelKeranjang;
import com.andyadr.apps.hidroponikstore.Auth.SharePrefManagerLogin;
import com.andyadr.apps.hidroponikstore.adapter.KeranjangAdapter;
import com.andyadr.apps.hidroponikstore.adapter.ProdukAdapter;
import com.andyadr.apps.hidroponikstore.model.Keranjang;
import com.andyadr.apps.hidroponikstore.model.MainViewModel;
import com.andyadr.apps.hidroponikstore.model.Produk;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeranjangActivity extends AppCompatActivity {

    static final String TAG = ProdukFragment.class.getSimpleName();
    public static final String EXTRAS = "extras";
    SharePrefManagerLogin sesslogin;

    private KeranjangAdapter pr_adapter;
    private Context context;
    private Button btntambah,btnkurang,btncheckout;
    private EditText etjumlah,etkodeproduk,etkodemember;
    private String ke,kp;
    private ApiEndpoints apiService = ApiClient.getClient().create(ApiEndpoints.class);
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);
        getSupportActionBar().setTitle("Keranjang");
        btntambah = findViewById(R.id.buttonincrease_keranjang);
        btnkurang = findViewById(R.id.buttondecrease_keranjang);
        btncheckout = findViewById(R.id.btncheckout);
        etjumlah = findViewById(R.id.jumlah_keranjang);
        etkodemember = findViewById(R.id.kodemember_keranjang);
        etkodeproduk = findViewById(R.id.kodeproduk_keranjang);
        sesslogin = new SharePrefManagerLogin(this);
        pr_adapter = new KeranjangAdapter(this);
        RecyclerView recyclerView = findViewById(R.id.rvListprodukkeranjang);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(pr_adapter);


//        progressBar = view.findViewById(R.id.progressbar);

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.setKeranjang(sesslogin.getSPKodeuser());
        mainViewModel.getKeranjang().observe(this, getKeranjang);



    }
    private Observer<ArrayList<Keranjang>> getKeranjang = new Observer<ArrayList<Keranjang>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Keranjang> keranjangs) {
            if (keranjangs != null) {
                pr_adapter.setKeranjang(keranjangs);
            }
        }
    };
}
