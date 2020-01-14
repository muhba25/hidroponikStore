package com.andyadr.apps.hidroponikstore.API;

import com.andyadr.apps.hidroponikstore.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
	private static Retrofit retrofit = null;

	public static Retrofit getClient() {

		Gson gson = new GsonBuilder()
				.setLenient()
				.create();

		OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
			@Override
			public okhttp3.Response intercept(Chain chain) throws IOException {
				Request RequestMe = chain.request();
				HttpUrl httpUrl = RequestMe.url()
						.newBuilder()
						.build();

				RequestMe = RequestMe.newBuilder()
						.url(httpUrl)
						.build();

				return chain.proceed(RequestMe);
			}
		}).build();

		if (retrofit == null) {
			retrofit = new Retrofit.Builder()
					.client(okHttpClient)
					.baseUrl(BuildConfig.URL_API)
					.addConverterFactory(GsonConverterFactory.create(gson))
					.build();
		}
		return retrofit;
	}

}
