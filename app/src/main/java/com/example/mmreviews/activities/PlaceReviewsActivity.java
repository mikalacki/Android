package com.example.mmreviews.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.mmreviews.R;
import com.example.mmreviews.adapters.MyAdapter;
import com.example.mmreviews.models.PlaceReview;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlaceReviewsActivity extends AppCompatActivity {

    TextView placeName;
    Button addComment;

    DatabaseReference database;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<PlaceReview> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_card);

        placeName = findViewById(R.id.placeName);
        addComment = findViewById(R.id.addComment);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;
        String userID = user.getEmail();

        Intent data = getIntent();
        String place = data.getStringExtra("placeName");
        placeName.setText(place);

        recyclerView = findViewById(R.id.cardList);
        database = FirebaseDatabase.getInstance().getReference("PlaceReviews");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);

        database.orderByChild("placeName").equalTo(place).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PlaceReview placeReview1 = dataSnapshot.getValue(PlaceReview.class);
                    list.add(placeReview1);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addComment.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddCommentActivity.class);
            intent.putExtra("user", userID);
            intent.putExtra("placeName", place);
            startActivity(intent);
            finish();
        });
    }
}