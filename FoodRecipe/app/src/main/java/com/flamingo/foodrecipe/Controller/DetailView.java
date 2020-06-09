package com.flamingo.foodrecipe.Controller;

import com.flamingo.foodrecipe.Model.Meals;

public interface DetailView  {
    void showloading();
    void hideLoading();
    void setMeal(Meals.Meal mean);
    void onErrorLoading (String message);

}
