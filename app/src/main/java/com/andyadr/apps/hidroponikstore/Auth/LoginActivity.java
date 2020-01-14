package com.andyadr.apps.hidroponikstore.Auth;

import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.andyadr.apps.hidroponikstore.API.ApiClient;
import com.andyadr.apps.hidroponikstore.API.ApiEndpoints;
import com.andyadr.apps.hidroponikstore.API.ResponseGetUser;
import com.andyadr.apps.hidroponikstore.API.ResponsePostPutDelUser;
import com.andyadr.apps.hidroponikstore.MainActivity;
import com.andyadr.apps.hidroponikstore.R;
import com.andyadr.apps.hidroponikstore.model.Produk;
import com.andyadr.apps.hidroponikstore.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private android.widget.EditText edtPasswordlog;
    private AppCompatCheckBox checkbox;
    @BindView(R.id.userlogin)
    EditText edtUser;
    @BindView(R.id.btn_login)
    Button btn_signin;
    private ApiEndpoints apiService = ApiClient.getClient().create(ApiEndpoints.class);
    private User user;
    private MutableLiveData<ArrayList<User>> userList = new MutableLiveData<>();
    SharePrefManagerLogin Sharepreflog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");
        ButterKnife.bind(this);
        checkbox = findViewById(R.id.checkbox);
        edtPasswordlog = findViewById(R.id.edtPassword);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    // show password
                    edtPasswordlog.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    edtPasswordlog.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        Sharepreflog = new SharePrefManagerLogin(this);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String usernamelog = edtUser.getText().toString().trim();
                final String passwordlog = edtPasswordlog.getText().toString().trim();
                boolean isEmptyFields = false;
                if (TextUtils.isEmpty(usernamelog) || TextUtils.isEmpty(passwordlog)){
                    isEmptyFields = true;
                    edtUser.setError("Field ini tidak boleh kosong");
                    edtPasswordlog.setError("Field ini tidak boleh kosong");
                } else {
                    Call<ResponseGetUser> userCall = apiService.loginRequest(usernamelog,passwordlog);
                    userCall.enqueue(new Callback<ResponseGetUser>() {
                        @Override
                        public void onResponse(Call<ResponseGetUser> call, Response<ResponseGetUser> response) {
                            ResponseGetUser resource = response.body();
                            Integer touser = resource.getTous();
                            if (touser==1) {
                                Call<ResponseGetUser> loginreqCall = apiService.loginRequest(usernamelog, passwordlog);
                                loginreqCall.enqueue(new Callback<ResponseGetUser>() {
                                    @Override
                                    public void onResponse(Call<ResponseGetUser> call, Response<ResponseGetUser> response) {
                                        if (response.isSuccessful()) {
                                            String Name = response.body().getUser().get(0).getUsername();
                                            String Email = response.body().getUser().get(0).getEmail();
                                            String Tgllahir = response.body().getUser().get(0).getTgl_lahir();
                                            String Jk = response.body().getUser().get(0).getJk();
                                            String Uid = response.body().getUser().get(0).getUid();
                                            String Pass = response.body().getUser().get(0).getPassword();
                                            String Foto = response.body().getUser().get(0).getFoto();
                                            Sharepreflog.saveSPString(Sharepreflog.SP_Username, Name);
                                            Sharepreflog.saveSPString(Sharepreflog.SP_Email, Email);
                                            Sharepreflog.saveSPString(Sharepreflog.SP_Tgl_lahir, Tgllahir);
                                            Sharepreflog.saveSPString(Sharepreflog.SP_Jk, Jk);
                                            Sharepreflog.saveSPString(Sharepreflog.SP_Pass, Pass);
                                            Sharepreflog.saveSPString(Sharepreflog.SP_KodeUser, Uid);
                                            Sharepreflog.saveSPString(Sharepreflog.SP_Foto, Foto);
                                            Sharepreflog.saveSPBoolean(Sharepreflog.SP_SUDAH_LOGIN, true);
                                            Toast.makeText(LoginActivity.this, "Berhasil Login " + Name , Toast.LENGTH_LONG).show();
                                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(i);
                                        } else {
                                            Toast.makeText(LoginActivity.this, "Gagal Login", Toast.LENGTH_LONG).show();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<ResponseGetUser> call, Throwable t) {
                                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
                                    }
                                });
                            } else {
                                Toast.makeText(LoginActivity.this, "Username / Password Salah", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseGetUser> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }});
    }
}
