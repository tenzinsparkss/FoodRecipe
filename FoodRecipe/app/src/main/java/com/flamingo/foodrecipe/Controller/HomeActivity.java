package com.flamingo.foodrecipe.Controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.flamingo.foodrecipe.Adapter.RecyclerViewHomeAdapter;
import com.flamingo.foodrecipe.Adapter.ViewPagerHeaderAdapter;
import com.flamingo.foodrecipe.Controller.HomeView;
import com.flamingo.foodrecipe.Controller.HomePresenter;
import com.flamingo.foodrecipe.Model.Categories;
import com.flamingo.foodrecipe.Model.Meals;
import com.flamingo.foodrecipe.R;
import com.flamingo.foodrecipe.Utils;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.nfc.cardemulation.CardEmulation.EXTRA_CATEGORY;

public class HomeActivity extends AppCompatActivity implements HomeView {
    public static final String EXTRA_CATEGORY = "category";
    public static final String EXTRA_POSITION = "position";
    public static final String EXTRA_DETAIl = "detail";

    @BindView(R.id.viewPageHeader)
    ViewPager viewPagerMeal;

    @BindView(R.id.recyclerCategory)
    RecyclerView recyclerViewCategory;

    HomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        presenter = new HomePresenter(this);
        presenter.getMeals();
        presenter.getCategories();

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Need an Account?")
                .setMessage("Explore more ideas with flamingo account.. join with us!!")
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", null)
                .show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Please go down to create a new account", Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
            }
        });
    }

    @Override
    public void showLoading() {
        findViewById(R.id.shimmerMeal).setVisibility(View.VISIBLE);
        findViewById(R.id.shimmerCategory).setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoading() {
        findViewById(R.id.shimmerMeal).setVisibility(View.GONE);
        findViewById(R.id.shimmerCategory).setVisibility(View.GONE);

    }

    @Override
    public void setMeal(List<Meals.Meal> meal) {
        ViewPagerHeaderAdapter headerAdapter = new ViewPagerHeaderAdapter(meal, this);
        viewPagerMeal.setAdapter(headerAdapter);
        viewPagerMeal.setPadding(20, 0, 150, 0);
        headerAdapter.notifyDataSetChanged();

        //intent to MealDetailActivity (get the name of the meal from the edit text view, then send the name of the meal)
        headerAdapter.setOnItemClickListener((view, position) -> {
            TextView mealName = view.findViewById(R.id.mealName);
            Intent intent = new Intent(getApplicationContext(), MealDetailActivity.class);
            intent.putExtra(EXTRA_DETAIl, mealName.getText().toString());
            startActivity(intent);

        });
    }

    @Override
    public void setCategory(List<Categories.Category> category) {
        RecyclerViewHomeAdapter homeAdapter = new RecyclerViewHomeAdapter(category, this);
        recyclerViewCategory.setAdapter(homeAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerViewCategory.setLayoutManager(layoutManager);
        recyclerViewCategory.setNestedScrollingEnabled(true);
        homeAdapter.notifyDataSetChanged();
        
        homeAdapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent(this, CategoryActivity.class);
            intent.putExtra(EXTRA_CATEGORY, (Serializable) category);
            intent.putExtra(EXTRA_POSITION, position);
            startActivity(intent);
        });


    }

    @Override
    public void onErrorLoading(String message) {
        Utils.showDialogMessage(this, "Title", message);
    }


    public void btnLoginPage(View view) {
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    }
}
