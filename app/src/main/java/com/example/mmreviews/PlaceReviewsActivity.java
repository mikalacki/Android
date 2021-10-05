package com.example.mmreviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Kod je generalno malo ruznjikavo formatiran :D
 * Nista strasno, moj je bio isti takav u pocetku hah
 * Evo predloga da ubacis formater u studio pa da ti on to lepo poslaze:
 * https://metova.com/how-to-import-the-official-android-code-style/
 * I za ovo ima more alternativa, uglavnom ide na ono sto se tebi dopadne
 * ili sto se tim dogovori da ce da koristi kao formatter
 */
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
        /*
        Bilo bi dobro da imas centralno mesto, jednu klasu koja ti upravlja bazom
        pa da ne moras na sto mesta da imas hardcoded string kao ovaj PlaceReviews.
        Mozes da zamotas ceo firebaseDatabase u svoju neku klasu koju onda koristis
        svuda a na jednom mestu imas samo kod koji zaista radi sa bazom
         */
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