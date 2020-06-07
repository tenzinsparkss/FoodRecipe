package com.flamingo.foodrecipe.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.flamingo.foodrecipe.Adapter.MyAdapter;
import com.flamingo.foodrecipe.Model.FoodInfo;
import com.flamingo.foodrecipe.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<FoodInfo> myFoodList;
    FoodInfo mFoodData;

    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;
    ProgressDialog progressDialog;

    MyAdapter myAdapter;
    EditText txt_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        //Showing rows of items with the help of Grid layout manager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        txt_search = (EditText)findViewById(R.id.txt_searchtext);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading items . . . ");

        myFoodList = new ArrayList<>();

        myAdapter = new MyAdapter(MainActivity.this, myFoodList);
        mRecyclerView.setAdapter(myAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Flamingo_FoodRecipe");
        progressDialog.show();

        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                myFoodList.clear();

                for(DataSnapshot itemSnapshot: dataSnapshot.getChildren()){

                    FoodInfo foodData = itemSnapshot.getValue(FoodInfo.class);
                    foodData.setKey(itemSnapshot.getKey());//delete from db

                    myFoodList.add(foodData);
                }

                myAdapter.notifyDataSetChanged();
                progressDialog.dismiss();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

        txt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());
            }
        });

    }
    //Search in recycler view by item title
    private void filter(String text) {

        ArrayList<FoodInfo> filterList = new ArrayList<>();

        for(FoodInfo item: myFoodList){

            if(item.getItemName().toLowerCase().contains(text.toLowerCase())){

                filterList.add(item);
            }
        }

        myAdapter.filteredList(filterList);
    }

    public void btn_uploadActivity(View view) {

        startActivity(new Intent(this, UploadRecipe.class));
    }

    public void btn_HomeActivity(View view) {
        startActivity(new Intent(this, HomeActivity.class));
    }
}
