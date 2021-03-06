package com.example.mmreviews.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mmreviews.R;
import com.example.mmreviews.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditActivity extends AppCompatActivity {

    public static final String TAG = "TAG";

    Button btnCancel, btnUpdate;
    EditText fullnameProfile, usernameProfile, emailProfile;
    private FirebaseUser user;
    private FirebaseAuth fAuth;
    private DatabaseReference reference;
    private FirebaseDatabase database;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent data = getIntent();
        String fullname = data.getStringExtra("fullname");
        String username = data.getStringExtra("username");
        String email = data.getStringExtra("email");

        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = fAuth.getCurrentUser();


        fullnameProfile = findViewById(R.id.fullNameEdit);
        usernameProfile = findViewById(R.id.usernameEdit);
        emailProfile = findViewById(R.id.emailEdit);
        btnUpdate = findViewById(R.id.btnUpdate);

        fullnameProfile.setText(fullname);
        usernameProfile.setText(username);
        emailProfile.setText(email);

        Log.d(TAG, "onCreate: " + fullname + " " + username + " " + email);


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fullnameProfile.getText().toString().isEmpty() || usernameProfile.getText().toString().isEmpty() || emailProfile.getText().toString().isEmpty()){
                    Toast.makeText(EditActivity.this, "One or many fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                String email = emailProfile.getText().toString();
                user.updateEmail(email).addOnSuccessListener(unused -> {
                    reference = FirebaseDatabase.getInstance().getReference("Users");
                    userID = user.getUid();
                    User u = new User();

                    u.setEmail(email);
                    u.setFullName(fullnameProfile.getText().toString());
                    u.setUsername(usernameProfile.getText().toString());

                    reference.child(userID).setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    });
                    Toast.makeText(EditActivity.this, "Profile has been updated", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> Toast.makeText(EditActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());


            }
        });


       btnCancel = findViewById(R.id.btnCancel);


        btnCancel.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        });

    }

}