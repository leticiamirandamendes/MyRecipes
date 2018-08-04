package com.example.android.myrecipes.api;


import com.example.android.myrecipes.objects.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipesAPI {

    @GET("baking.json")
    Call<List<Recipe>> getRecipe();

}
