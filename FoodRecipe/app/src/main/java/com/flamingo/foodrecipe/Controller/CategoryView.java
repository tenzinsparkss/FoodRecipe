package com.flamingo.foodrecipe.Controller;

import com.flamingo.foodrecipe.Model.Meals;

import java.util.List;

public interface CategoryView {
    void showLoading();

    void hideLoading();

    void setMeals(List<Meals.Meal> meals);

    void onErrorLoading(String message);
}
