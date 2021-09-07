package com.example.mmreviews;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.mopub.volley.Request;
import com.mopub.volley.toolbox.StringRequest;
import com.mopub.volley.toolbox.Volley;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.mmreviews.R.id.username;

public class LogIn extends AppCompatActivity {

    TextInputEditText textInputEditTextPassword, textInputEditTextUsername;
    TextView textViewSignup;
    Button buttonLogin, buttonOverride;
    ProgressBar progressBar;


    private static final String USER_URL = "http://192.168.1.59/LoginRegister/myApi.php";
    List<User> userList;
    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        userList = new ArrayList<>();

        textInputEditTextUsername = findViewById(R.id.usernameLogin);
        textInputEditTextPassword = findViewById(R.id.passwordLogin);
        buttonLogin = findViewById(R.id.btn_login);
        textViewSignup = findViewById(R.id.signupText);
        progressBar = findViewById(R.id.progressBar);

        buttonOverride = findViewById(R.id.btn_override);

        final TextView textViewUsernameInfo = (TextView) findViewById(R.id.username);
        textViewUsernameInfo.setText("Some text");


        buttonOverride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        textViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {




                String username, password;

                username = String.valueOf(textInputEditTextUsername.getText());
                password = String.valueOf(textInputEditTextPassword.getText());

               // users = loadUsers();
                //setProfileInfo(users, username);

                if (!username.equals("") && !password.equals("")) {

                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];

                            field[0] = "username";
                            field[1] = "password";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = password;
                            // 192.168.1.59 ip address for pc
                            // 192.168.2.252 ip address for phone
                            PutData putData = new PutData("http://192.168.1.59/LoginRegister/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Login Success")) {

                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "All tings are required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

   /* private List<User> loadUsers() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, USER_URL, s -> {
            try {
                JSONArray users = new JSONArray(s);
                for (int i = 0; i < s.length(); i++) {
                    JSONObject userObject = users.getJSONObject(i);

                    int userId = userObject.getInt("userID");
                    String username = userObject.getString("username");
                    String fullname = userObject.getString("fullName");
                    String email = userObject.getString("email");

                    User user = new User(username, fullname, email);
                    userList.add(user);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, volleyError -> Toast.makeText(LogIn.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show());

        Volley.newRequestQueue(this, null).add(stringRequest);
        return userList;
    }

    public void setProfileInfo(List<User> users, String username) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {

            }
        }
    }*/
}