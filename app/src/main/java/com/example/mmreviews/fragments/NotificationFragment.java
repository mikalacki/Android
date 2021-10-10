package com.example.mmreviews.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mmreviews.adapters.MyAdapterNotifications;
import com.example.mmreviews.R;
import com.example.mmreviews.models.PlaceReview;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {

    DatabaseReference database;
    RecyclerView recyclerView;
    MyAdapterNotifications myAdapterNotifications;
    ArrayList<PlaceReview> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        recyclerView = view.findViewById(R.id.notificationList);
        database = FirebaseDatabase.getInstance().getReference("PlaceReviews");

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        list = new ArrayList<>();

        myAdapterNotifications = new MyAdapterNotifications(getContext(), list);
        recyclerView.setAdapter(myAdapterNotifications);

        database.orderByChild("placeName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PlaceReview placeReview = dataSnapshot.getValue(PlaceReview.class);
                    list.add(placeReview);
                }
                myAdapterNotifications.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

}
