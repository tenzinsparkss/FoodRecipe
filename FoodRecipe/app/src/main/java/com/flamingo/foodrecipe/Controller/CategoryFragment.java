package com.flamingo.foodrecipe.Controller;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flamingo.foodrecipe.Adapter.RecyclerViewMealByCategory;
import com.flamingo.foodrecipe.Model.Meals;
import com.flamingo.foodrecipe.R;
import com.flamingo.foodrecipe.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.flamingo.foodrecipe.Controller.HomeActivity.EXTRA_DETAIl;

public class CategoryFragment extends Fragment implements CategoryView {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.imageCategory)
    ImageView imageCategory;
    @BindView(R.id.imageCategoryBg)
    ImageView imageCategoryBg;
    @BindView(R.id.textCategory)
    TextView textCategory;

    AlertDialog.Builder descDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // getArguments with KEY
        if (getArguments() != null) {
            textCategory.setText(getArguments().getString("EXTRA_DATA_DESC"));
            Picasso.get().load(getArguments().getString("EXTRA_DATA_IMAGE")).into(imageCategory);
            Picasso.get().load(getArguments().getString("EXTRA_DATA_IMAGE")).into(imageCategoryBg);}
        descDialog = new AlertDialog.Builder(getActivity())
                .setTitle(getArguments().getString("EXTRA_DATA_NAME"))
                .setMessage(getArguments().getString("EXTRA_DATA_DESC"));

        //declare presenter
        CategoryPresenter presenter = new CategoryPresenter(this);
        presenter.getMealByCategory(getArguments().getString("EXTRA_DATA_NAME"));

    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setMeals(List<Meals.Meal> meals) {
        //set RecyclerViewMealByCategory adapter;
        RecyclerViewMealByCategory adapter = new RecyclerViewMealByCategory(getActivity(), meals);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setClipToPadding(false);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener((view, position) -> {

//            Toast.makeText(getActivity(), "meal : " + meals.get(position).getStrMeal(), Toast.LENGTH_SHORT).show();
            TextView mealName = view.findViewById(R.id.mealName);
            Intent intent = new Intent(getActivity(), MealDetailActivity.class);
            intent.putExtra(EXTRA_DETAIl, mealName.getText().toString());
            startActivity(intent);


        });
    }

    @Override
    public void onErrorLoading(String message) {
        Utils.showDialogMessage(getActivity(), "Error ", message);
    }

    @OnClick(R.id.cardCategory)
    public void onClick(){
        descDialog.setPositiveButton("CLOSE", (dialog, which) -> dialog.dismiss());
        descDialog.show();
    }

}
