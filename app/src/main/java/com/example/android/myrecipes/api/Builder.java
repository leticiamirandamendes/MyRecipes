package com.example.android.myrecipes.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Builder {

    public static RecipesAPI implementation(){
        RecipesAPI recipesAPI;
        Gson gson = new GsonBuilder().create();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        recipesAPI = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .callFactory(httpClient.build())
                .build().create(RecipesAPI.class);
        return recipesAPI;
    }
}
