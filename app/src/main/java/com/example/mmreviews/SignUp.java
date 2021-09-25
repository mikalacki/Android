package com.example.mmreviews;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class SignUp extends AppCompatActivity {

    TextInputEditText textInputEditTextFullname, textInputEditTextUsername, textInputEditTextPassword, textInputEditTextEmail;
    Button buttonRegister;
    TextView textViewLogin;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        textInputEditTextUsername = findViewById(R.id.usernameSignup);
        textInputEditTextFullname = findViewById(R.id.fullName);
        textInputEditTextEmail = findViewById(R.id.email);
        textInputEditTextPassword = findViewById(R.id.passwordSignup);
        buttonRegister = findViewById(R.id.btn_register);
        textViewLogin = findViewById(R.id.loginText);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        fAuth = FirebaseAuth.getInstance();

        textViewLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LogIn.class);
            startActivity(intent);
            finish();
        });



        buttonRegister.setOnClickListener(v ->
        {

            String username =textInputEditTextUsername.getText().toString().trim();
            String fullname = textInputEditTextFullname.getText().toString().trim();
            String email = textInputEditTextEmail.getText().toString().trim();
            String password = textInputEditTextPassword.getText().toString();
            User user = new User(username, fullname, email);

            if (TextUtils.isEmpty(username)){
                textInputEditTextUsername.setError("Username is Required");
                return;
            }

            if (TextUtils.isEmpty(fullname)){
                textInputEditTextFullname.setError("Fullname is Required");
                return;
            }

            if (TextUtils.isEmpty(email)){
                textInputEditTextEmail.setError("Email is Required");
                return;
            }

            if (TextUtils.isEmpty(password)){
                textInputEditTextPassword.setError("Password is Required");
                return;
            }
            if (password.length() < 6){
                textInputEditTextPassword.setError("Password is too short");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(SignUp.this, "User has been registered successfully", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.VISIBLE);
                                    Intent intent = new Intent(getApplicationContext(), LogIn.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Toast.makeText(SignUp.this, "Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(SignUp.this, "Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
        });

    }
}