package com.flamingo.foodrecipe;

import com.flamingo.foodrecipe.Meals;
import com.flamingo.foodrecipe.Categories;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FoodApi {
    @GET("random.php")
    Call<Meals> getMeal();


    @GET("categories.php")
    Call<Categories> getCategories();
}
