package com.example.instagram;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    private AnimationDrawable animationDrawable;
    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginBtn;
    private EditText signupUsername;
    private EditText signupPassword;
    private EditText signupEmail;
    private Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // onCreate
        ConstraintLayout login = findViewById(R.id.clLogin);
        animationDrawable = (AnimationDrawable) login.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);
        // onResume
        animationDrawable.start();

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) goToHomeActivity();

        usernameInput = findViewById(R.id.etUsername);
        passwordInput = findViewById(R.id.etPassword);
        loginBtn = findViewById(R.id.login_button);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameInput.getText().toString();
                final String password = passwordInput.getText().toString();

                login(username, password);
            }
        });

        signupUsername = findViewById(R.id.etSignupUsername);
        signupPassword = findViewById(R.id.etSignupPassword);
        signupEmail = findViewById(R.id.etSignupEmail);
        signupBtn = findViewById(R.id.singup_button);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = signupUsername.getText().toString();
                final String password = signupPassword.getText().toString();
                final String email = signupEmail.getText().toString();

                signup(username, password, email);
            }
        });

    }

    private void goToHomeActivity() {
        final Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void login(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Log.d("LoginActivity", "Login successful!");
                    goToHomeActivity();
                } else {
                    Log.e("LoginActivity", "Login failure.");
                    e.printStackTrace();
                }
            }
        });
    }

    private void signup(String username, String password, String email) {
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("SignupActivity", "Signup successful!");
                    goToHomeActivity();
                } else {
                    Log.e("SignupActivity", "Signup failure.");
                    e.printStackTrace();
                }
            }
        });
    }

}
