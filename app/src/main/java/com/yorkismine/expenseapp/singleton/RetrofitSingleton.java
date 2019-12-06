package com.yorkismine.expenseapp.singleton;

import com.github.leonardoxh.livedatacalladapter.LiveDataCallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {
    private static RetrofitSingleton instance;

    private Retrofit retrofit;


    private RetrofitSingleton(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.cbr-xml-daily.ru/")
                .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public static RetrofitSingleton getInstance() {
        if (instance == null) instance = new RetrofitSingleton();
        return instance;
    }
}
