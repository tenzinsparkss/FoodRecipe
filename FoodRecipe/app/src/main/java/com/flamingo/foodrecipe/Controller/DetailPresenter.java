package com.flamingo.foodrecipe.Controller;

import com.flamingo.foodrecipe.Model.Meals;
import com.flamingo.foodrecipe.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPresenter {

    private DetailView view;

    public DetailPresenter(DetailView view) {
        this.view = view;
    }
    void getMealbyId(String mealName){

        //call the void show loading before starting to make a request to the server
        view.showloading();

        //make a request to the server dont forget to hide loading when the response is received
        Utils.getApi().getMealByName(mealName)
                .enqueue(new Callback<Meals>() {
                    @Override
                    public void onResponse(Call<Meals> call, Response<Meals> response) {
                        view.hideLoading();
                        if(response.isSuccessful() && response.body() != null){
                            view.setMeal(response.body().getMeals().get(0));
                        }
                        else {
                            view.onErrorLoading(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Meals> call, Throwable t) {
                        view.hideLoading();
                        view.onErrorLoading(t.getLocalizedMessage());
                    }
                });

        // set response meal
    }
}
