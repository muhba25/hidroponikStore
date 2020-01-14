package com.andyadr.apps.hidroponikstore.Auth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andyadr.apps.hidroponikstore.API.ApiClient;
import com.andyadr.apps.hidroponikstore.API.ApiEndpoints;
import com.andyadr.apps.hidroponikstore.API.ResponseFotoProfil;
import com.andyadr.apps.hidroponikstore.API.ResponseGetUser;
import com.andyadr.apps.hidroponikstore.API.ResponseUploadFoto;
import com.andyadr.apps.hidroponikstore.MainActivity;
import com.andyadr.apps.hidroponikstore.R;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FotoProfilActivity extends AppCompatActivity {

    Button btnUpload, btnMulUpload, btnPickImage, btnPickVideo;
    String mediaPath, mediaPath1;
    ImageView imgView;
    String[] mediaColumns = {MediaStore.Video.Media._ID};
    ProgressDialog progressDialog;
    TextView str1, str2;
    SharePrefManagerLogin sesslogin;
    private ApiEndpoints apiService = ApiClient.getClient().create(ApiEndpoints.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_profil);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        btnUpload = findViewById(R.id.upload);
//        btnMulUpload = findViewById(R.id.uploadMultiple);
        btnPickImage = findViewById(R.id.pick_img);
        btnPickVideo = findViewById(R.id.pick_vdo);
        imgView = findViewById(R.id.preview);
        str1 = findViewById(R.id.filename1);
//        str2 = findViewById(R.id.filename2);

        sesslogin = new SharePrefManagerLogin(this);

        if (sesslogin.getSP_Foto().isEmpty()){
            imgView.setImageResource(R.drawable.hidroo);
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
                                    String profileImageUrl = "http://118.98.121.244/teshidro/assets/images/user/"+Foto;
                                    Glide.with(FotoProfilActivity.this)
                                            .load(profileImageUrl)
                                            .into(imgView);
                                } else {
                                    Toast.makeText(FotoProfilActivity.this, "Tidak Ada Foto", Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseGetUser> call, Throwable t) {
                                Toast.makeText(FotoProfilActivity.this, "Error", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        Toast.makeText(FotoProfilActivity.this, "Username / Password Salah", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<ResponseGetUser> call, Throwable t) {
                    Toast.makeText(FotoProfilActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            });
        }

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });

//        btnMulUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                uploadMultipleFiles();
//            }
//        });

        btnPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });

        // Video must be low in Memory or need to be compressed before uploading...
        btnPickVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                str1.setText(mediaPath);
                // Set the Image in ImageView for Previewing the Media
                imgView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();

            } // When an Video is picked
            else if (requestCode == 1 && resultCode == RESULT_OK && null != data) {

                // Get the Video from data
                Uri selectedVideo = data.getData();
                String[] filePathColumn = {MediaStore.Video.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedVideo, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                mediaPath1 = cursor.getString(columnIndex);
                str2.setText(mediaPath1);
                // Set the Video Thumb in ImageView Previewing the Media
                imgView.setImageBitmap(getThumbnailPathForLocalFile(FotoProfilActivity.this, selectedVideo));
                cursor.close();

            } else {
                Toast.makeText(this, "You haven't picked Image/Video", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }

    // Providing Thumbnail For Selected Image
    public Bitmap getThumbnailPathForLocalFile(Activity context, Uri fileUri) {
        long fileId = getFileId(context, fileUri);
        return MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(),
                fileId, MediaStore.Video.Thumbnails.MICRO_KIND, null);
    }

    // Getting Selected File ID
    public long getFileId(Activity context, Uri fileUri) {
        Cursor cursor = context.managedQuery(fileUri, mediaColumns, null, null, null);
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
            return cursor.getInt(columnIndex);
        }
        return 0;
    }

    // Uploading Image/Video
    private void uploadFile() {
        progressDialog.show();

        // Map is used to multipart the file using okhttp3.RequestBody
        File file = new File(mediaPath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        sesslogin = new SharePrefManagerLogin(this);
        RequestBody kodeuser = RequestBody.create(MediaType.parse("text/plain"), sesslogin.getSPKodeuser());
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        ApiEndpoints getResponse = ApiClient.getClient().create(ApiEndpoints.class);
        Call<ResponseFotoProfil> call = getResponse.uploadImage(fileToUpload, filename,kodeuser);
        call.enqueue(new Callback<ResponseFotoProfil>() {
            @Override
            public void onResponse(Call<ResponseFotoProfil> call, Response<ResponseFotoProfil> response) {
                ResponseFotoProfil serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert serverResponse != null;
                    Log.v("Response", serverResponse.toString());
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseFotoProfil> call, Throwable t) {

            }
        });
    }


}
