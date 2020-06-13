package com.flamingo.foodrecipe.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.flamingo.foodrecipe.Model.UserInfo;
import com.flamingo.foodrecipe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    EditText rName, rEmail, rPassword;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    FloatingActionButton mregisterBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rName = findViewById(R.id.rName);
        rEmail = findViewById(R.id.rEmail);
        rPassword = findViewById(R.id.rPassword);
        mregisterBtn = findViewById(R.id.registerBtn);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mregisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = rName.getText().toString().trim();
                String email = rEmail.getText().toString().trim();
                String password = rPassword.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    rName.setError("Required field");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    rEmail.setError("Required field");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    rPassword.setError("Required field");
                    return;
                }

                if (password.length() < 6) {
                    rPassword.setError("Password must be greater than six digit characters");
                    return;
                }

                //firebase: register the user
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success
                                    Toast.makeText(RegisterActivity.this, "You have registered successfully.!!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                    RegisterActivity.this.finish();
                                }
                                else {
                                    Toast.makeText(RegisterActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//
                                }
                            }
                        });
            }
        });


    }


    public void loginBtn(View view) {

        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }

}

