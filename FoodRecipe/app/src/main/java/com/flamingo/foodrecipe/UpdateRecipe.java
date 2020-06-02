package com.flamingo.foodrecipe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class UpdateRecipe extends AppCompatActivity {

    ImageView recipeImage;
    Uri uri;
    EditText txt_name, txt_description, txt_type;
    String imageUrl;
    String key, oldImageUrl;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    String recipeTitle, recipeDescription, recipeType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_recipe);

        recipeImage = (ImageView)findViewById(R.id.iv_foodImage);
        txt_name = (EditText)findViewById(R.id.txt_recipe_name);
        txt_description = (EditText)findViewById(R.id.text_description);
        txt_type = (EditText)findViewById(R.id.text_type);


        Bundle bundle = getIntent().getExtras();

        if(bundle != null){

            Glide.with(UpdateRecipe.this)
                    .load(bundle.getString("oldimageUrl"))
                    .into(recipeImage);

            txt_name.setText(bundle.getString("foodTitleKey"));
            txt_description.setText(bundle.getString("descriptionKey"));
            txt_type.setText(bundle.getString("Type"));
            key = bundle.getString("key");
            oldImageUrl = bundle.getString("oldimageUrl");
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Flamingo_FoodRecipe").child(key);

    }

    public void btnSelectImage(View view) {
        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");//Image extension that it allows any image's file
        startActivityForResult(photoPicker, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            uri = data.getData();
            recipeImage.setImageURI(uri);

        }
        else Toast.makeText(this, "You have not selected any image", Toast.LENGTH_SHORT).show();
    }

    public void btnUpdateRecipe(View view) {

         recipeTitle = txt_name.getText().toString().trim();
         recipeDescription = txt_description.getText().toString().trim();
         recipeType = txt_type.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Recipe Updating . . .");
        progressDialog.show();

        storageReference =  FirebaseStorage.getInstance()
                .getReference().child("RecipeImage").child(uri.getLastPathSegment());

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();

                while(!uriTask.isComplete());
                Uri uriImage = uriTask.getResult();

                imageUrl = uriImage.toString();//uploading image to db

//                Toast.makeText(UploadRecipe.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                uploadRecipe();
                progressDialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
            }
        });


    }

    public void uploadRecipe(){
        //passing constructor from FoodInfo class
        FoodInfo foodData = new FoodInfo(
                recipeTitle,
                recipeDescription,
                recipeType,
                imageUrl
        );

        databaseReference.setValue(foodData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                StorageReference storageReferenceNew = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageUrl);
                storageReferenceNew.delete();
                Toast.makeText(UpdateRecipe.this, "Data is updated", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
