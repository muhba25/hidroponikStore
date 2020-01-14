package com.andyadr.apps.hidroponikstore.Auth;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andyadr.apps.hidroponikstore.API.ApiEndpoints;
import com.andyadr.apps.hidroponikstore.API.ResponseUploadFoto;
import com.andyadr.apps.hidroponikstore.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadFotoActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private static final String TAG = UploadFotoActivity.class.getSimpleName();
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private static final String SERVER_PATH = "http://118.98.121.244/teshidro/api_hidroponik/";
    private Uri uri;
    SharePrefManagerLogin sesslogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_foto);
        Button selectUploadButton = findViewById(R.id.select_image);
        selectUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, UploadFotoActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK){
            uri = data.getData();
            if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String filePath = getRealPathFromURIPath(uri, UploadFotoActivity.this);
                File file = new File(filePath);
                Log.d(TAG, "Filename " + file.getName());
//                RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
//                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
//                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
//                RequestBody kode_user = RequestBody.create(MultipartBody.FORM, sesslogin.getSPKodeuser());
                sesslogin = new SharePrefManagerLogin(this);
                RequestBody kode_user =  RequestBody.create(MediaType.parse("text/plain"), sesslogin.getSPKodeuser());

                RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(SERVER_PATH)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                ApiEndpoints uploadImage = retrofit.create(ApiEndpoints.class);
                Call<ResponseUploadFoto> fileUpload = uploadImage.uploadFile(fileToUpload, filename,kode_user);
                fileUpload.enqueue(new Callback<ResponseUploadFoto>() {
                    @Override
                    public void onResponse(Call<ResponseUploadFoto> call, Response<ResponseUploadFoto> response) {
                        Toast.makeText(UploadFotoActivity.this, "Response " + response.raw().message(), Toast.LENGTH_LONG).show();
                        Toast.makeText(UploadFotoActivity.this, "Success " + response.body().getSuccess(), Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onFailure(Call<ResponseUploadFoto> call, Throwable t) {
                        Log.d(TAG, "Error " + t.getMessage());
                    }
                });
            }else{
                EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }
    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if(uri != null){
            String filePath = getRealPathFromURIPath(uri, UploadFotoActivity.this);
            File file = new File(filePath);
//            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
//            RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
            sesslogin = new SharePrefManagerLogin(this);
            RequestBody kode_user =  RequestBody.create(MediaType.parse("text/plain"), sesslogin.getSPKodeuser());
//            RequestBody kode_user = RequestBody.create(MultipartBody.FORM, sesslogin.getSPKodeuser());
            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
            RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(SERVER_PATH)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            ApiEndpoints uploadImage = retrofit.create(ApiEndpoints.class);
            Call<ResponseUploadFoto> fileUpload = uploadImage.uploadFile(fileToUpload, filename,kode_user);
            fileUpload.enqueue(new Callback<ResponseUploadFoto>() {
                @Override
                public void onResponse(Call<ResponseUploadFoto> call, Response<ResponseUploadFoto> response) {
                    Toast.makeText(UploadFotoActivity.this, "Success " + response.message(), Toast.LENGTH_LONG).show();
                    Toast.makeText(UploadFotoActivity.this, "Success " + response.body().toString(), Toast.LENGTH_LONG).show();
                }
                @Override
                public void onFailure(Call<ResponseUploadFoto> call, Throwable t) {
                    Log.d(TAG, "Error " + t.getMessage());
                }
            });
        }
    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");
    }
}
