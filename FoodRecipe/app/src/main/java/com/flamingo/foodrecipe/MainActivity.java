package com.flamingo.foodrecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<FoodInfo> myFoodList;
    FoodInfo mFoodData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        //Showing rows of items with the help of Grid layout manager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        myFoodList = new ArrayList<>();

        mFoodData = new FoodInfo("Tingmo","It is so delicious and made of Wheat and Flour","Veg", R.drawable.img1);
        myFoodList.add(mFoodData);
        mFoodData = new FoodInfo("Chowmein","It is so delicious","Non-veg", R.drawable.img2);
        myFoodList.add(mFoodData);
        mFoodData = new FoodInfo("Thukpa","It is so delicious","Non-veg", R.drawable.img3);
        myFoodList.add(mFoodData);

        MyAdapter myAdapter = new MyAdapter(MainActivity.this, myFoodList);
        mRecyclerView.setAdapter(myAdapter);

    }
}
