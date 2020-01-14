package com.andyadr.apps.hidroponikstore.Auth;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andyadr.apps.hidroponikstore.API.ApiClient;
import com.andyadr.apps.hidroponikstore.API.ApiEndpoints;
import com.andyadr.apps.hidroponikstore.API.ResponsePostPutDelUser;
import com.andyadr.apps.hidroponikstore.MainActivity;
import com.andyadr.apps.hidroponikstore.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarActivity extends AppCompatActivity {

    @BindView(R.id.btn_once_date)
    ImageButton btnOpenDatePicker;
    @BindView(R.id.tv_once_date)
    EditText etTanggal;
    @BindView(R.id.userdaftar)
    EditText etUser;
    @BindView(R.id.emaildaftar)
    EditText etEmail;
    @BindView(R.id.spinnerjk)
    Spinner etJk;
    @BindView(R.id.act_signup)
    Button btnSignup;

    Calendar myCalendar;
    private ApiEndpoints apiService = ApiClient.getClient().create(ApiEndpoints.class);
    private AppCompatCheckBox checkbox;
    private android.widget.EditText edtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        getSupportActionBar().setTitle("Daftar");
        ButterKnife.bind(this);
        edtPassword = findViewById(R.id.edtPassworddaftar);
        checkbox = findViewById(R.id.checkboxdaftar);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    // show password
                    edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    // hide password
                    edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });


        myCalendar = Calendar.getInstance();

        btnOpenDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DaftarActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        String formatTanggal = "yyyy-MM-dd";
                        SimpleDateFormat sdf = new SimpleDateFormat(formatTanggal);
                        etTanggal.setText(sdf.format(myCalendar.getTime()));
                    }
                },
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = etUser.getText().toString().trim();
                final String password = edtPassword.getText().toString().trim();
                final String email = etEmail.getText().toString().trim();
                final String tgl_lahir = etTanggal.getText().toString().trim();
                final String jk = etJk.getSelectedItem().toString().trim();
                boolean isEmptyFields = false;
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(email) || TextUtils.isEmpty(tgl_lahir)){
                    isEmptyFields = true;
                    etUser.setError("Field ini tidak boleh kosong");
                    edtPassword.setError("Field ini tidak boleh kosong");
                    etEmail.setError("Field ini tidak boleh kosong");
                    etTanggal.setError("Field ini tidak boleh kosong");
                } else {
                    Call<ResponsePostPutDelUser> userCall = apiService.getUser(username);
                    userCall.enqueue(new Callback<ResponsePostPutDelUser>() {
                        @Override
                        public void onResponse(Call<ResponsePostPutDelUser> call, Response<ResponsePostPutDelUser> response) {
                            ResponsePostPutDelUser resource = response.body();
                            Integer touser = resource.getTotaluser();
//                            String oke = Integer.toString(touser);
                            if (touser==1) {
                                Toast.makeText(DaftarActivity.this, "Username sudah ada", Toast.LENGTH_SHORT).show();
                            } else {
                                Call<ResponsePostPutDelUser> postKontakCall = apiService.postUser(username,password,email,tgl_lahir,jk);
                                postKontakCall.enqueue(new Callback<ResponsePostPutDelUser>() {
                                    @Override
                                    public void onResponse(Call<ResponsePostPutDelUser> call, Response<ResponsePostPutDelUser> response) {
                                        Toast.makeText(DaftarActivity.this, "Anda telah berhasil mendaftar", Toast.LENGTH_LONG).show();
                                        finish();
                                        Intent i = new Intent(DaftarActivity.this, MainActivity.class);
                                        startActivity(i);
                                    }

                                    @Override
                                    public void onFailure(Call<ResponsePostPutDelUser> call, Throwable t) {
                                        Toast.makeText(DaftarActivity.this, "Error", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponsePostPutDelUser> call, Throwable t) {
                            Toast.makeText(DaftarActivity.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    });

                }
                //Toast.makeText(DaftarActivity.this,username+" "+password+" "+email+" "+tgl_lahir+" "+jk,Toast.LENGTH_LONG).show();


            }
        });

    }
}
