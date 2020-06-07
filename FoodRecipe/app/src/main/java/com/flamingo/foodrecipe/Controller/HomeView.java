package com.flamingo.foodrecipe.Controller;

import com.flamingo.foodrecipe.Model.Categories;
import com.flamingo.foodrecipe.Model.Meals;

import java.util.List;

public interface HomeView {

    void showLoading();
    void hideLoading();
    void setMeal(List<Meals.Meal> meal);
    void setCategory(List<Categories.Category> category);
    void onErrorLoading(String message);

}
