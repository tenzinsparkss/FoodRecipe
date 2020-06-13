package com.flamingo.foodrecipe.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.flamingo.foodrecipe.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void logoutBtn(View view) {
//        FirebaseAuth.getInstance().signOut();
//        finish();
//        startActivity(new Intent(getApplicationContext(),LoginActivity.class));

    }
}
