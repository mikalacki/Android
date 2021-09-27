package com.example.mmreviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;

import java.util.ArrayList;
import java.util.List;

public class PlaceCard extends AppCompatActivity {

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

        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddComment.class);
                intent.putExtra("user", userID);
                intent.putExtra("placeName", place);
                startActivity(intent);
                finish();
            }
        });




    }
}