package com.example.android.adventurequencher;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

//this class hosts the login activity for where the sign in and login fragments are occupied
public class LoginActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set background image (copyright free)
        getWindow().setBackgroundDrawableResource(R.drawable.background_tongariro_crossing);

        //load intitial fragment of the login screen
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LoginFragment fragment = new LoginFragment();
        fragmentTransaction.add(R.id.login_placeholder, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
