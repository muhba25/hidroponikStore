package com.andyadr.apps.hidroponikstore;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
        getSupportActionBar().setTitle("Pembelian");

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(sectionsPagerAdapter);
        // Atur tablayout
        viewPager.setCurrentItem(0);

        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.setupWithViewPager(viewPager);


        getSupportActionBar().setElevation(1);

    }
}
