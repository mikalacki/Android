package com.example.mmreviews;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    private FirebaseUser user;
    private DatabaseReference reference;
    private Button btnLogout, btnEdit;
    private String userID;



    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btnLogout = view.findViewById(R.id.btn_logout);
        btnEdit = view.findViewById(R.id.btn_edit);


        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LogIn.class);
            startActivity(intent);

        });


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView fullNameTextView = (TextView) view.findViewById(R.id.fullNameProfile);
        final TextView usernameTextView = (TextView) view.findViewById(R.id.usernameProfile);
        final TextView emailTextView = (TextView) view.findViewById(R.id.emailProfile);



        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    String fullName = userProfile.getFullName();
                    String username = userProfile.getUsername();
                    String email = userProfile.getEmail();

                    fullNameTextView.setText(fullName);
                    usernameTextView.setText(username);
                    emailTextView.setText(email);
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

        return view;
    }
}
