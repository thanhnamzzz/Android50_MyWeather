package com.myweather;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    //Quan trọng phải tạo được class RetrofitClient này để đọc được API từ link url
    private static Retrofit dogRetrofitInstance;

    private static Retrofit getDogRetrofitInstance(String url){
        if (dogRetrofitInstance == null){
            dogRetrofitInstance =new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return dogRetrofitInstance;
    }
    public static <T>T getServices(String url, Class<T> services){
        return getDogRetrofitInstance(url).create(services);
    }
}
