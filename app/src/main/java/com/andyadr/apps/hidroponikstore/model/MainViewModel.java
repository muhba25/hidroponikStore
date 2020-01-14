package com.andyadr.apps.hidroponikstore.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.graphics.Movie;
import android.util.Log;
import android.widget.Toast;

import com.andyadr.apps.hidroponikstore.API.ApiClient;
import com.andyadr.apps.hidroponikstore.API.ApiEndpoints;
import com.andyadr.apps.hidroponikstore.API.ResponseGetUser;
import com.andyadr.apps.hidroponikstore.API.ResponseKeranjang;
import com.andyadr.apps.hidroponikstore.API.ResponsePostPutDelUser;
import com.andyadr.apps.hidroponikstore.API.ResponseProduk;
import com.andyadr.apps.hidroponikstore.Auth.DaftarActivity;
import com.andyadr.apps.hidroponikstore.Auth.LoginActivity;
import com.andyadr.apps.hidroponikstore.KeranjangActivity;
import com.andyadr.apps.hidroponikstore.MainActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
	private MutableLiveData<ArrayList<Produk>> produkList = new MutableLiveData<>();
	private MutableLiveData<ArrayList<Keranjang>> keranjangList = new MutableLiveData<>();
	private ApiEndpoints apiService = ApiClient.getClient().create(ApiEndpoints.class);

	public void setProduk() {
		Call<ResponseProduk> Produkcall = apiService.getProduk();
		Produkcall.enqueue(new Callback<ResponseProduk>() {
			@Override
			public void onResponse(Call<ResponseProduk> call, Response<ResponseProduk> response) {
				try {
					ArrayList<Produk> produk = response.body().getListDataProduk();
					produkList.postValue(produk);
				} catch (Exception e) {
					Log.d(MainActivity.class.getSimpleName(), e.getLocalizedMessage());
				}
			}

			@Override
			public void onFailure(Call<ResponseProduk> call, Throwable t) {
				Log.d(MainActivity.class.getSimpleName(), t.getLocalizedMessage());
			}
		});
	}

	public void setKeranjang(String member) {
		Call<ResponseKeranjang> Keranjangcall = apiService.getKeranjang(member);
		Keranjangcall.enqueue(new Callback<ResponseKeranjang>() {
			@Override
			public void onResponse(Call<ResponseKeranjang> call, Response<ResponseKeranjang> response) {

				try {
					ArrayList<Keranjang> keranjang = response.body().getMkeranjang();
					keranjangList.postValue(keranjang);
				} catch (Exception e) {
					Log.d(KeranjangActivity.class.getSimpleName(), e.getLocalizedMessage());
				}
			}

			@Override
			public void onFailure(Call<ResponseKeranjang> call, Throwable t) {
				Log.d(KeranjangActivity.class.getSimpleName(), t.getLocalizedMessage());
			}
		});
	}


//
//	public void setFavMovie(ArrayList<Movie> movies) {
//		movieList.postValue(movies);
//	}
//
//	public void searchMovie(String query) {
//		Call<ResponseMovie> call = apiService.searchMovies(query);
//		call.enqueue(new Callback<ResponseMovie>() {
//			@Override
//			public void onResponse(Call<ResponseMovie> call, Response<ResponseMovie> response) {
//				try {
//					ArrayList<Movie> movies = response.body().getResults();
//					movieList.postValue(movies);
//					Log.d(MainActivity.class.getSimpleName(), movies.toString());
//				} catch (Exception e) {
//					Log.d(MainActivity.class.getSimpleName(), e.getLocalizedMessage());
//				}
//			}
//
//			@Override
//			public void onFailure(Call<ResponseMovie> call, Throwable t) {
//				Log.d(MainActivity.class.getSimpleName(), t.getLocalizedMessage());
//			}
//		});
//	}

	public LiveData<ArrayList<Produk>> getProduk() {
		return produkList;
	}
	public LiveData<ArrayList<Keranjang>> getKeranjang() {
		return keranjangList;
	}

}
