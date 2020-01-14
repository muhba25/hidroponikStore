package com.andyadr.apps.hidroponikstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.andyadr.apps.hidroponikstore.model.Keranjang;

public class PembelianActivity extends AppCompatActivity {
    private Keranjang keranjang;
    private TextView tv_beli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembelian);

        tv_beli = findViewById(R.id.testbe);
        Intent intent = getIntent();
        keranjang = intent.getParcelableExtra("keranjang");
        tv_beli.setText(keranjang.getJumlah_keranjang()+ keranjang.getNama_produk_keranjang());

    }
}
