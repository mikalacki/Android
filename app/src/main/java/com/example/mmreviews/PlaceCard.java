package com.example.mmreviews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class PlaceCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_card);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String userID = user.getEmail();

        Intent data = getIntent();
        String place = data.getStringExtra("placeName");

        ProgressBar progressBar = findViewById(R.id.progressBar);

        PlaceReview placeReview = new PlaceReview();

        placeReview.setPlaceName(place);
        //placeReview.setUser(userID);
        placeReview.setReviewNumber(0);
        placeReview.setR(0);
        placeReview.setAvarage(0);

        FirebaseDatabase.getInstance().getReference("PlaceReviews")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .push().setValue(placeReview).addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()){
                Toast.makeText(PlaceCard.this, "Success", Toast.LENGTH_SHORT).show();


            }
            else{
                Toast.makeText(PlaceCard.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }
}