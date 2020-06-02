package com.flamingo.foodrecipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FoodDetailActivity extends AppCompatActivity {

    TextView foodDescription, foodTitle, foodType;
    ImageView foodImage;
    String key = "";
    String imageURL = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        foodDescription = (TextView)findViewById(R.id.txtDescription);
        foodImage = (ImageView)findViewById(R.id.ivImg1);
        foodTitle = (TextView)findViewById(R.id.txtTitle);
        foodType = (TextView)findViewById(R.id.txtType);

        Bundle mBundle = getIntent().getExtras();

        if(mBundle!=null){
            foodTitle.setText(mBundle.getString("Title"));
            foodDescription.setText(mBundle.getString("Description"));
            foodType.setText(mBundle.getString("Type"));
//            foodImage.setImageResource(mBundle.getInt("Image"));
            key = mBundle.getString("keyValue");
            imageURL = mBundle.getString("Image");

            Glide.with(this)
                    .load(mBundle.getString("Image"))
                    .into(foodImage);

        }



    }
    //Delete method from db
    public void btn_delete_Recipe(View view) {

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Flamingo_FoodRecipe");
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageReference = storage.getReferenceFromUrl(imageURL);

        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                reference.child(key).removeValue();
                Toast.makeText(FoodDetailActivity.this, "Food recipe has been deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }

    public void btn_update_Recipe(View view) {

        startActivity(new Intent(getApplicationContext(), UpdateRecipe.class)
        .putExtra("foodTitleKey", foodTitle.getText().toString())
        .putExtra("descriptionKey", foodDescription.getText().toString())
        .putExtra("typeKey", foodType.getText().toString())
        .putExtra("oldimageUrl", imageURL)
        .putExtra("key", key));
    }
}
