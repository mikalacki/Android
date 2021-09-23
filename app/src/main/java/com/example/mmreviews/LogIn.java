package com.example.mmreviews;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.widget.Button;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import org.jetbrains.annotations.NotNull;


public class LogIn extends AppCompatActivity {

    TextInputEditText textInputEditTextPassword, textInputEditTextEmail;
    TextView textViewSignup;
    Button buttonLogin, buttonOverride;
    ProgressBar progressBar;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        textInputEditTextEmail = findViewById(R.id.emailLogin);
        textInputEditTextPassword = findViewById(R.id.passwordLogin);
        fAuth = FirebaseAuth.getInstance();
        buttonLogin = findViewById(R.id.btn_login);
        textViewSignup = findViewById(R.id.signupText);
        progressBar = findViewById(R.id.progressBar);

        buttonOverride = findViewById(R.id.btn_override);

        buttonOverride.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });

        textViewSignup.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SignUp.class);
            startActivity(intent);
            finish();
        });


        buttonLogin.setOnClickListener(v -> {

            fAuth.signInWithEmailAndPassword(textInputEditTextEmail.getText().toString(), textInputEditTextPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LogIn.this, "User loged in", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        Toast.makeText(LogIn.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        });


    }
}