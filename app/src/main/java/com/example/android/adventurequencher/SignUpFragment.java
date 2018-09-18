package com.example.android.adventurequencher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SignUpFragment extends Fragment implements View.OnClickListener
{

    private EditText email;
    private EditText password;
    private EditText password2;
    private EditText displayName;

    public SignUpFragment()
    {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance()
    {
        Bundle args = new Bundle();

        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        Button signUpButton = (Button) view.findViewById(R.id.createAccountButton);
        TextView loginLink = (TextView) view.findViewById(R.id.link_login);
        email = (EditText) view.findViewById(R.id.signUpEmail);
        password = (EditText) view.findViewById(R.id.signUpPassword);
        password2 = (EditText) view.findViewById(R.id.signUpPassword2);
        displayName = (EditText) view.findViewById(R.id.signUpName);

        signUpButton.setOnClickListener(this);
        loginLink.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }


    public void onClick(View view)
    {
        if (view.getId() == R.id.createAccountButton)
        {
            String emailInput = email.getText().toString();
            String passwordInput = password.getText().toString();
            String password2Input = password2.getText().toString();
            String displayNameInput = displayName.getText().toString();



            new ValidateRegister(emailInput, passwordInput, password2Input, displayNameInput).execute();
        }
        if (view.getId() == R.id.link_login)
        {
            // Begin the transaction
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            // Replace the contents of the container with the new fragment
            ft.replace(R.id.login_placeholder, LoginFragment.newInstance());
            // Complete the changes added above
            ft.commit();
            ft.addToBackStack(null);
        }
    }

    private class ValidateRegister extends AsyncTask<String, String, String>
    {
        private String email;
        private String password;
        private String password2;
        private String displayName;

        public ValidateRegister(String emailInput, String passwordInput, String password2Input, String displayNameInput)
        {
            email = emailInput;
            password = passwordInput;
            password2 = password2Input;
            displayName = displayNameInput;
        }

        protected void onPreExecute(String result)
        {
            //TODO: ADD LOADING SCREEN HERE TO SHOW LOGGING IN ATTEMPT
        }

        @Override
        protected String doInBackground(String... params)
        {

            HttpURLConnection connection;
            OutputStreamWriter request = null;
            String response = null;
            try
            {
                String link = "http://43.245.55.133/validateRegister.php";
                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                data += "&" + URLEncoder.encode("password2", "UTF-8") + "=" + URLEncoder.encode(password2, "UTF-8");
                data += "&" + URLEncoder.encode("display_name", "UTF-8") + "=" + URLEncoder.encode(displayName, "UTF-8");
                URL url = new URL(link);

                Log.d("aq", "credentials set");

                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestMethod("POST");


                //test internet connection by pinging a server
                if (!isNetworkWorking())
                {
                    response = "no connection";
                } else
                {
                    Log.d("aq", "parameters set, url connection opened");

                    request = new OutputStreamWriter(connection.getOutputStream());
                    request.write(data);
                    request.flush();
                    request.close();

                    Log.d("aq", "starting to build string response from server");
                    // Read data sent from server
                    InputStream input = connection.getInputStream();
                    Log.d("aq", "input stream instantiate");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    Log.d("aq", "buffered reader instantiate");
                    StringBuilder sb = new StringBuilder();
                    Log.d("aq", "string builder instantiate");
                    String line;
                    Log.d("aq", "reading lines");
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                    }

                    // Response from server after login process will be stored in response variable.
                    response = sb.toString();
                    Log.d("aq", "server response!!!!---->" + response);
                    input.close();
                    reader.close();
                }
            }
            catch(Exception e)
                    {
                        //e.printStackTrace();
                        Log.d("aq", "error!");
                    }

            return response;
        }

        @Override
        protected void onPostExecute(String result)
        {
            Toast.makeText(getActivity(), result,
                    Toast.LENGTH_LONG).show();
        }

        public boolean isNetworkWorking()
        {
            //test internet connection

            try
            {
                //send ping to the server
                Runtime runtime = Runtime.getRuntime();
                Process ipProcess = runtime.exec("/system/bin/ping -c 1 43.245.55.133");
                int exitValue = ipProcess.waitFor();
                return (exitValue == 1);    //return true if response given
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            return false;
        }
    }
}
