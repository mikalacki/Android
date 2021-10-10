package com.example.mmreviews.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.mmreviews.R;
import com.example.mmreviews.models.PlaceReview;
import com.example.mmreviews.models.User;
import com.example.mmreviews.services.NotificationSender;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddCommentActivity extends AppCompatActivity {

    Button btnCancel, btnAdd;
    EditText editTextComment;
    RatingBar ratingBar;
    float rating;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);



        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnC);


        btnAdd.setOnClickListener(v -> {



            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            reference = FirebaseDatabase.getInstance().getReference("Users");
            assert user != null;
            userID = user.getUid();

            reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userProfile = snapshot.getValue(User.class);

                    if (userProfile != null) {
                        editTextComment = findViewById(R.id.newComment);

                        ratingBar = findViewById(R.id.rating);

                        Intent data = getIntent();
                        String placeName =  data.getStringExtra("placeName");

                        PlaceReview placeReview = new PlaceReview();

                        String comment = editTextComment.getText().toString();

                        rating = ratingBar.getRating();

                        placeReview.setUser(userProfile.getUsername());

                        placeReview.setComment(comment);
                        placeReview.setPlaceName(placeName);
                        placeReview.setRating(rating);

                        FirebaseDatabase.getInstance().getReference("PlaceReviews")
                                .push().setValue(placeReview).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()){
                                Toast.makeText(AddCommentActivity.this, "Success", Toast.LENGTH_SHORT).show();

                                String message = userProfile.getUsername() + " comented on " + placeName;
                                NotificationSender notificationsSender = new NotificationSender("/topics/NEW_REVIEW_CHANNEL", "New review!", message, getApplicationContext(), AddCommentActivity.this);
                                notificationsSender.sendNotifications();

                                Intent intent = new Intent(getApplicationContext(), PlaceReviewsActivity.class);
                                intent.putExtra("placeName", placeName);

                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(AddCommentActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        });

        btnCancel.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), PlaceReviewsActivity.class);
            startActivity(intent);
            finish();
        });
        
    }

}