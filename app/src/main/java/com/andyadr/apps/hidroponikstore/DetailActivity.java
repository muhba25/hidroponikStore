package com.andyadr.apps.hidroponikstore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.andyadr.apps.hidroponikstore.Auth.SharePrefManagerLogin;
import com.andyadr.apps.hidroponikstore.model.Produk;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {
//    @BindView(R.id.toolbar_detail)
//    Toolbar toolbardet;
    @BindView(R.id.kode_produkdet)
    TextView tv_kode_produk;
    @BindView(R.id.nama_produkdet)
    TextView tv_nama_produk;
    @BindView(R.id.deskripsidet)
    TextView tv_deskripsi;
    @BindView(R.id.hargadet)
    TextView tv_harga;
    @BindView(R.id.jenisdet)
    TextView tv_jenis;
    @BindView(R.id.solddet)
    TextView tv_sold;
    @BindView(R.id.tgl_uploaddet)
    TextView tv_tgl_upload;
    @BindView(R.id.ratingdet)
    RatingBar tv_rating;
    @BindView(R.id.fotodet)
    ImageView iv_foto;
    @BindView(R.id.fotodet2)
    ImageView iv_foto2;
    @BindView(R.id.navigation_bottom)
    BottomNavigationView navigation_bottom;

    private Produk produk;
    SharePrefManagerLogin sesslogin;
    private String title,kode,foto,member;
    private Integer harga;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        navigation_bottom.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        Intent intent = getIntent();
        produk = intent.getParcelableExtra("produk");
        getSupportActionBar().setTitle("Detail Produk");

        //sesslogin nav bottom
        sesslogin = new SharePrefManagerLogin(this);

        if (sesslogin.getSPSudahLogin()==false) {
            navigation_bottom.getMenu().removeItem(R.id.nav_bottom_1);
            navigation_bottom.getMenu().removeItem(R.id.nav_bottom_2);
        }

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);

        collapsingToolbarLayout.setCollapsedTitleTextColor(
                ContextCompat.getColor(this, R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(
                ContextCompat.getColor(this, R.color.white));
//        initToolbar();
        showDetails(produk);


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//            Fragment fragment;
//            toolbar.collapseActionView();
            Bundle bundle = new Bundle();
            switch (menuItem.getItemId()) {
                case R.id.nav_bottom_1:
//                    if (getSupportActionBar() != null)
//                        getSupportActionBar().setTitle(R.string.title_tab1);
//                    fragment = new ModalBottomCartFragment();
//                    View view = getLayoutInflater().inflate(R.layout.fragment_modal_bottom_cart, null);
//                    BottomSheetDialog dialog = new BottomSheetDialog(DetailActivity.this);
//                    dialog.setContentView(view);
//                    dialog.show();

                    title = produk.getNama_produk();
                    harga = produk.getHarga();
                    kode = produk.getKode_produk();
                    foto = produk.getFoto();
                    member = sesslogin.getSPKodeuser();
                    ModalBottomCartFragment bottomcartfragment = new ModalBottomCartFragment();
                    bundle.putString(ModalBottomCartFragment.EXTRA_TITLE, title);
                    bundle.putString(ModalBottomCartFragment.EXTRA_KODE, kode);
                    bundle.putString(ModalBottomCartFragment.EXTRA_FOTO, foto);
                    bundle.putString(ModalBottomCartFragment.EXTRA_MEMBER, member);
                    bundle.putInt(ModalBottomCartFragment.EXTRA_HARGA, harga);

                    bottomcartfragment.setArguments(bundle);
                    bottomcartfragment.show(getSupportFragmentManager(), bottomcartfragment.getTag());
                    return true;
                case R.id.nav_bottom_2:
//                    if (getSupportActionBar() != null)
//                        getSupportActionBar().setTitle(R.string.title_tab2);
                    title = produk.getNama_produk();
                    harga = produk.getHarga();
                    kode = produk.getKode_produk();
                    foto = produk.getFoto();
                    member = sesslogin.getSPKodeuser();
                    ModalBottomBuyFragment bottombuyfragment = new ModalBottomBuyFragment();
                    bundle.putString(ModalBottomBuyFragment.EXTRA_TITLE, title);
                    bundle.putString(ModalBottomBuyFragment.EXTRA_KODE, kode);
                    bundle.putString(ModalBottomBuyFragment.EXTRA_FOTO, foto);
                    bundle.putString(ModalBottomBuyFragment.EXTRA_MEMBER, member);
                    bundle.putInt(ModalBottomBuyFragment.EXTRA_HARGA, harga);

                    bottombuyfragment.setArguments(bundle);
                    bottombuyfragment.show(getSupportFragmentManager(), bottombuyfragment.getTag());
                    return true;
            }
            return false;
        }
    };

//    private void loadFragment(Fragment fragment) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame_container, fragment);
//        transaction.commit();
//    }


//    private void initToolbar() {
//        setSupportActionBar(toolbardet);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//        setTitle(R.string.detailproduk);
//    }

    private void showDetails(Produk produks) {
        String url_image = BuildConfig.URL_IMG + produks.getFoto();

        tv_kode_produk.setText(produks.getKode_produk());
        tv_nama_produk.setText(produks.getNama_produk());
        tv_deskripsi.setText(produks.getDeskripsi());
        tv_jenis.setText(produks.getJenis()) ;
        Integer harga= produks.getHarga();

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator('.');
        formatRp.setGroupingSeparator(',');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        String x = kursIndonesia.format(harga);

        tv_harga.setText(x);

        String sold= Integer.toString(produks.getSold());
        tv_sold.setText(sold);

        String rating= Double.toString(produks.getRating());
        tv_rating.setRating(Float.parseFloat(rating));
        tv_tgl_upload.setText(produks.getTgl_upload()) ;
        Glide.with(DetailActivity.this)
                .load(url_image)
                .into(iv_foto);

        Glide.with(DetailActivity.this)
                .load(url_image)
                .into(iv_foto2);
    }

}
