package com.example.android.adventurequencher;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        IntroFragment fragment = new IntroFragment();
        fragmentTransaction.add(R.id.login_placeholder, fragment);
        fragmentTransaction.commit();
    }
}
