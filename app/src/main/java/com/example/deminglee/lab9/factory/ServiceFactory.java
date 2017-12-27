package com.example.deminglee.lab9.factory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by Deming Lee on 2017/12/27.
 */

public class ServiceFactory {
  private static OkHttpClient createOkHttp() {
    return new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build();
  }
  
  private static Retrofit createRetrofit(String str) {
    return new Retrofit.Builder()
            .baseUrl(str)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(createOkHttp())
            .build();
  }
  
  public static Retrofit getmRetrofit(String str) {
    return createRetrofit(str);
  }
}
