package com.flamingo.foodrecipe.API;

import com.flamingo.foodrecipe.Model.Categories;
import com.flamingo.foodrecipe.Model.Meals;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodApi {
    @GET("random.php")
    Call<Meals> getMeal();


    @GET("categories.php")
    Call<Categories> getCategories();

    @GET("filter.php")
    Call<Meals> getMealByCategory(@Query("c") String category);
}
