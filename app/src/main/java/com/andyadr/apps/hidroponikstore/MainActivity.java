package com.andyadr.apps.hidroponikstore;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;


import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andyadr.apps.hidroponikstore.API.ApiClient;
import com.andyadr.apps.hidroponikstore.API.ApiEndpoints;
import com.andyadr.apps.hidroponikstore.API.ResponseGetUser;
import com.andyadr.apps.hidroponikstore.Auth.DaftarActivity;
import com.andyadr.apps.hidroponikstore.Auth.FotoProfilActivity;
import com.andyadr.apps.hidroponikstore.Auth.LoginActivity;
import com.andyadr.apps.hidroponikstore.Auth.SharePrefManagerLogin;
import com.andyadr.apps.hidroponikstore.Auth.UploadFotoActivity;
import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {


    CircleImageView profileCircleImageView;
    String profileImageUrl;
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    TextView spuser;
    TextView spemail;
    ImageView spjk;
    TextView sptgll;
    private ApiEndpoints apiService = ApiClient.getClient().create(ApiEndpoints.class);

    ActionBarDrawerToggle toggle;
    SharePrefManagerLogin sesslogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Hidroponik Store");
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hubungi admin jika ada masalah", Snackbar.LENGTH_LONG)
                        .setAction("Call Now", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:081144404385"));
                                startActivity(intent);
                            }
                        }).show();
            }
        });

        drawer = findViewById(R.id.drawer_layout);

        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Login Session dan Menu Session
        sesslogin = new SharePrefManagerLogin(this);

        if (sesslogin.getSPSudahLogin()==true){
            Menu navMenu = navigationView.getMenu();
            navMenu.findItem(R.id.item_auth).setVisible(false);
        }
        //Logout Session dan Menu Session
        if (sesslogin.getSPSudahLogin()==false){
            Menu navMenu = navigationView.getMenu();
            navMenu.findItem(R.id.nav_logout).setVisible(false);
        }
        //UploadFoto Session dan Menu Session
        if (sesslogin.getSPSudahLogin()==false){
            Menu navMenu = navigationView.getMenu();
            navMenu.findItem(R.id.nav_fotoprofil).setVisible(false);
        }

        if (sesslogin.getSP_Foto().isEmpty()){
            profileImageUrl = "https://www.kesulawesi.com/assets/img/team/2.jpg";
            profileCircleImageView = navigationView.getHeaderView(0).findViewById(R.id.fotouser);
            Glide.with(MainActivity.this)
                    .load(profileImageUrl)
                    .into(profileCircleImageView);
        } else {
            Call<ResponseGetUser> userCall = apiService.loginRequest(sesslogin.getSPNama(),sesslogin.getSP_Pass());
            userCall.enqueue(new Callback<ResponseGetUser>() {
                @Override
                public void onResponse(Call<ResponseGetUser> call, Response<ResponseGetUser> response) {
                    ResponseGetUser resource = response.body();
                    Integer touser = resource.getTous();
                    if (touser==1) {
                        Call<ResponseGetUser> loginreqCall = apiService.loginRequest(sesslogin.getSPNama(), sesslogin.getSP_Pass());
                        loginreqCall.enqueue(new Callback<ResponseGetUser>() {
                            @Override
                            public void onResponse(Call<ResponseGetUser> call, Response<ResponseGetUser> response) {
                                if (response.isSuccessful()) {
                                    String Foto = response.body().getUser().get(0).getFoto();
                                    profileImageUrl = "http://118.98.121.244/teshidro/assets/images/user/"+Foto;
                                    profileCircleImageView = navigationView.getHeaderView(0).findViewById(R.id.fotouser);
                                    Glide.with(MainActivity.this)
                                            .load(profileImageUrl)
                                            .into(profileCircleImageView);
                                } else {
                                    Toast.makeText(MainActivity.this, "Tidak Ada Foto", Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseGetUser> call, Throwable t) {
                                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        Toast.makeText(MainActivity.this, "Username / Password Salah", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<ResponseGetUser> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        //Image Nav header


        spuser = navigationView.getHeaderView(0).findViewById(R.id.namauser);
        spemail= navigationView.getHeaderView(0).findViewById(R.id.emailuser);
        sptgll = navigationView.getHeaderView(0).findViewById(R.id.tglluser);
        spjk = navigationView.getHeaderView(0).findViewById(R.id.jkuser);


        String ok=sesslogin.getSP_Jk().replace('-',' ').trim();
        String okk = ok.replaceAll("\\s+", "");

        spuser.setText(sesslogin.getSPNama());
        spemail.setText(sesslogin.getSPEmail());


        int strjk = okk.length();
        int str1 = "Lakilaki".length();
        int str2 = "Perempuan".length();
        if (strjk==str1){
            spjk.setImageResource(R.drawable.male);
        } else if(strjk==str2){
            Glide.with(MainActivity.this)
                    .load(R.drawable.female)
                    .into(spjk);
        } else {
            spjk.setVisibility(View.INVISIBLE);
        }

        String dateString = sesslogin.getSP_Tgl_lahir();
        try {

           Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            String dates = new SimpleDateFormat("dd/MMM/yyyy").format(date);
            sptgll.setText(dates);
        } catch (ParseException e) {
            e.printStackTrace();
        }




        if (savedInstanceState == null) {
            Fragment currentFragment = new HomeFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.nav_host_fragment, currentFragment)
                    .commit();
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }


    @Override
    protected void onPause() {
        super.onPause();
        drawer.removeDrawerListener(toggle);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        Bundle bundle = new Bundle();

        Fragment fragment = null;

        String title = "Hidroponik Store";

        if (id == R.id.nav_produk) {

            title = "Produk";
            fragment = new ProdukFragment();
            bundle.putString(ProdukFragment.EXTRAS, title);
            fragment.setArguments(bundle);

        } else if (id == R.id.nav_keranjang) {
//            title = "Keranjang";
//            fragment = new KeranjangFragment();
//            bundle.putString(KeranjangFragment.EXTRAS, title);
//            fragment.setArguments(bundle);
            Intent keran = new Intent(MainActivity.this, KeranjangActivity.class);
            startActivity(keran);
        } else if (id == R.id.nav_pembelian) {
            title = "Pembelian";
            fragment = new KeranjangFragment();
            bundle.putString(PembelianFragment.EXTRAS, title);
            fragment.setArguments(bundle);
        } else if (id == R.id.nav_login) {
            Intent login = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(login);
        } else if (id == R.id.nav_logout) {
            sesslogin.clearLoggedInUser(this);
            Toast.makeText(MainActivity.this, "Berhasil Logout", Toast.LENGTH_LONG).show();
            Intent i = new Intent(MainActivity.this, MainActivity.class);
            finish();
            startActivity(i);
        } else if (id == R.id.nav_daftar) {
            Intent daftar = new Intent(MainActivity.this, DaftarActivity.class);
            startActivity(daftar);

        } else if (id == R.id.nav_uploadfoto) {
            Intent upload = new Intent(MainActivity.this, UploadFotoActivity.class);
            startActivity(upload);

        } else if (id == R.id.nav_fotoprofil) {
            Intent fp = new Intent(MainActivity.this, FotoProfilActivity.class);
            startActivity(fp);

        }

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .commit();
        }

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
