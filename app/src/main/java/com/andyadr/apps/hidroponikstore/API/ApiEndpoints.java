package com.andyadr.apps.hidroponikstore.API;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiEndpoints {
	//produk
	@GET("produk")
	Call<ResponseProduk> getProduk();

	//user
	@GET("user")
	Call<ResponseGetUser> loginRequest(@Query("nama") String nama, @Query("pass") String pass);

	@GET("ambiluserbyname")
	Call<ResponsePostPutDelUser> getUser(@Query("nama") String nama);

	@FormUrlEncoded
	@POST("user")
	Call<ResponsePostPutDelUser> postUser(@Field("username") String user,
										  @Field("password") String pass, @Field("email") String email,
										  @Field("tgl_lahir") String tgl_lahir, @Field("jk") String jk);
	@FormUrlEncoded
	@PUT("user")
	Call<ResponsePostPutDelUser> putKontak(@Field("uid") String uid, @Field("username") String user,
										   @Field("password") String pass, @Field("email") String email,
										   @Field("tgl_lahir") String tgl_lahir, @Field("jk") String jk);
	@FormUrlEncoded
	@HTTP(method = "DELETE", path = "user", hasBody = true)
	Call<ResponsePostPutDelUser> deleteUser(@Field("uid") String uid);

	//uploadfoto
	@Multipart
	@POST("uploadfoto")
	Call<ResponseUploadFoto> uploadFile(@Part MultipartBody.Part file, @Part("name") RequestBody name, @Part("uid") RequestBody kodeuser);

	@Multipart
	@POST("uploadfoto")
	Call<ResponseFotoProfil> uploadImage(@Part MultipartBody.Part file, @Part("file") RequestBody name, @Part("uid") RequestBody kodeuser);

	//keranjang
	@GET("keranjang")
	Call<ResponseKeranjang> getKeranjang(@Query("member") String memberid);

	@FormUrlEncoded
	@POST("keranjang")
	Call<ResponsePostPutDelKeranjang> postKeranjang(@Field("memberid") String member,
										  @Field("kodeproduk") String kode, @Field("jumlah") String jumlah);

//	@FormUrlEncoded
//	@HTTP(method = "DELETE", path = "keranjang", hasBody = true)
//	Call<ResponsePostPutDelKeranjang> deleteKeranjang(@Field("kodeproduk") String uid,@Field("member") String member);

	@FormUrlEncoded
	@POST("keranjangdel")
	Call<ResponsePostPutDelKeranjang> deleteKeranjang(@Field("member") String member,
													@Field("kodeproduk") String kode);

	//transaksi_pay
	@FormUrlEncoded
	@POST("checkout")
	Call<ResponsePostPutDelKeranjang> postPay(@Field("memberc") String member,
													  @Field("kodeprodukc") String kode, @Field("jumlahc") String jumlah);

	//history
	@GET("buyhistory")
	Call<ResponseHistory> getHistory(@Query("member") String member);

	//Pembelian
	@GET("pembelian")
	Call<ResponsePembelian> getPembelian(@Query("memberi") String member);
}
