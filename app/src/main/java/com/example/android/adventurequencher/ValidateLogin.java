package com.example.android.adventurequencher;

import android.os.AsyncTask;

public class ValidateLogin extends AsyncTask<String, String, String>
{
    private String email;
    private String password;

    public ValidateLogin(String emailInput, String passwordInput)
    {
        email = emailInput;
        password = passwordInput;
    }

    @Override
    protected String doInBackground(String... params)
    {
        return null;
    }
}
