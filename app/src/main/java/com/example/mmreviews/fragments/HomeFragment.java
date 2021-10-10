package com.example.mmreviews.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mmreviews.activities.EditActivity;
import com.example.mmreviews.activities.LogInActivity;
import com.example.mmreviews.R;
import com.example.mmreviews.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    private DatabaseReference reference;
    private String userID;


    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button btnLogout = view.findViewById(R.id.btn_logout);
        Button btnEdit = view.findViewById(R.id.btn_edit);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        assert user != null;
        userID = user.getUid();

        final TextView fullNameTextView = view.findViewById(R.id.fullNameProfile);
        final TextView usernameTextView = view.findViewById(R.id.usernameProfile);
        final TextView emailTextView = view.findViewById(R.id.emailProfile);



        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    fullNameTextView.setText(userProfile.getFullName());
                    usernameTextView.setText(userProfile.getUsername());
                    emailTextView.setText(userProfile.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnEdit.setOnClickListener(v -> {
            reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userProfile = snapshot.getValue(User.class);

                    if (userProfile != null) {
                        String fullName = userProfile.getFullName();
                        String username = userProfile.getUsername();
                        String email = userProfile.getEmail();

                        Intent intent = new Intent(getActivity(), EditActivity.class);
                        intent.putExtra("username", username);
                        intent.putExtra("fullname", fullName);
                        intent.putExtra("email", email);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        });

        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getActivity(), LogInActivity.class));
            getActivity().finish();
        });

        return view;
    }


}
