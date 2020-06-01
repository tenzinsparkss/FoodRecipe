package com.flamingo.foodrecipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.View;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        //Showing rows of items with the help of Grid layout manager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading items . . . ");

        myFoodList = new ArrayList<>();

        final MyAdapter myAdapter = new MyAdapter(MainActivity.this, myFoodList);
        mRecyclerView.setAdapter(myAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Flamingo_FoodRecipe");
        progressDialog.show();

        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                myFoodList.clear();

                for(DataSnapshot itemSnapshot: dataSnapshot.getChildren()){

                    FoodInfo foodData = itemSnapshot.getValue(FoodInfo.class);

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


    }

    public void btn_uploadActivity(View view) {

        startActivity(new Intent(this, UploadRecipe.class));
    }
}
