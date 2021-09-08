package com.example.mmreviews;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputEditText;


public class SignUp extends AppCompatActivity {

    TextInputEditText textInputEditTextFullname, textInputEditTextUsername, textInputEditTextPassword, textInputEditTextEmail;
    Button buttonRegister;
    TextView textViewLogin;

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

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LogIn.class);
                startActivity(intent);
                finish();
            }
        });

        DAOUser daoUser = new DAOUser();

        buttonRegister.setOnClickListener(v ->
        {
            User user = new User(textInputEditTextUsername.getText().toString(),
                    textInputEditTextFullname.getText().toString(),
                    textInputEditTextEmail.getText().toString(),
                    textInputEditTextPassword.getText().toString());
            daoUser.add(user).addOnSuccessListener(suc ->
                    {
                        Toast.makeText(this, "Record inserted", Toast.LENGTH_SHORT).show();
                    }
            ).addOnFailureListener(er ->
                    {
                        Toast.makeText(this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();

                    }
            );
        });

    }
}