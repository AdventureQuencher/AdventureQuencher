package com.example.android.adventurequencher;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class LoginActivity extends AppCompatActivity
{

    LinearLayout login;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        final TextInputLayout passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);

        usernameWrapper.setHint("Username");
        passwordWrapper.setHint("Password");

        login = findViewById(R.id.login);
        login.setVisibility(LinearLayout.GONE); //hide login input fields
    }
}
