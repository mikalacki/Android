package com.example.mmreviews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class AddComment extends AppCompatActivity {

    Button btnCancel, btnAdd;
    EditText editTextComment;
    RatingBar ratingBar;
    float rating = 7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnC);


        btnAdd.setOnClickListener(v -> {
            editTextComment = findViewById(R.id.newComment);

            ratingBar = findViewById(R.id.rating);

            Intent data = getIntent();
            String placeName =  data.getStringExtra("placeName");
            String user = data.getStringExtra("user");
            PlaceReview placeReview = new PlaceReview();

            String comment = editTextComment.getText().toString();

            rating = ratingBar.getRating();
            int reviewNumber = 1;
            float avarage = 5;
            int likeNo = 0;

            placeReview.setLikeNo(likeNo);
            placeReview.setComment(comment);
            placeReview.setPlaceName(placeName);
            placeReview.setUser(user);
            placeReview.setReviewNumber(reviewNumber + 1);
            placeReview.setR(rating);
            placeReview.setAvarage(((avarage * reviewNumber) + rating)/(reviewNumber + 1));
            FirebaseDatabase.getInstance().getReference("PlaceReviews")
                    .push().setValue(placeReview).addOnCompleteListener(task1 -> {
                if (task1.isSuccessful()){
                    Toast.makeText(AddComment.this, "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), PlaceCard.class);
                    intent.putExtra("placeName", placeName);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(AddComment.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlaceCard.class);
                startActivity(intent);
                finish();
            }
        });





    }
}