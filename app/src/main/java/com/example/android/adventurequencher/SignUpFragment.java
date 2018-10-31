package com.example.android.adventurequencher;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import org.json.JSONException;
import org.json.JSONObject;

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
        //setup view
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        //create activity element variables
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

    //method called when user clicks a button/link
    public void onClick(View view)
    {
        //if user wishes to create an account
        if (view.getId() == R.id.createAccountButton)
        {
            //gather user inputs
            String emailInput = email.getText().toString();
            String passwordInput = password.getText().toString();
            String password2Input = password2.getText().toString();
            String displayNameInput = displayName.getText().toString();

            //validate user inputs and save on the database
            new ValidateRegister(emailInput, passwordInput, password2Input, displayNameInput).execute();
        }
        //if user wants to log in
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

    //asynchronous class that executes when the user registers an account to validate their inputs and contact the server
    private class ValidateRegister extends AsyncTask<String, String, String>
    {
        private String email;
        private String password;
        private String password2;
        private String displayName;
        private ProgressDialog nDialog;

        //constructor to get user inputs
        public ValidateRegister(String emailInput, String passwordInput, String password2Input, String displayNameInput)
        {
            email = emailInput;
            password = passwordInput;
            password2 = password2Input;
            displayName = displayNameInput;
        }

        //before thread starts, create loading dialog screen
        protected void onPreExecute()
        {
            Log.d("aq", "registration thread started");
            nDialog = new ProgressDialog(getActivity());
            nDialog.setMessage("Please wait..");
            nDialog.setTitle("Registering account");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }

        @Override
        protected String doInBackground(String... params)
        {

            //connect to the server
            HttpURLConnection connection;
            OutputStreamWriter request = null;
            String response = null;
            try
            {
                String link = "http://43.245.55.133/validateRegister.php";  //server ip and related php script
                //encode user inputs in the data
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
                if (!isNetworkWorking(getActivity()))
                {
                    response = "no connection"; //display no connection message
                }


                else
                {
                    Log.d("aq", "parameters set, url connection opened");

                    //open output stream and write data
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

                    //build string response from server
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                    }

                    // Response from server after login process will be stored in response variable.
                    response = sb.toString();
                    Log.d("aq", "server response---->" + response);
                    input.close();
                    reader.close();
                }
            }
            catch(Exception e)
                    {
                        //e.printStackTrace();
                        Log.d("aq", "error!");
                    }

            return response;    //return server response or no connection error
        }

        //executed after server gives a response
        @Override
        protected void onPostExecute(String result)
        {
            try
            {
                //remove loading dialogue
                if (nDialog.isShowing())
                {
                    nDialog.dismiss();
                }
                nDialog = null;
            } catch (Exception e)
            {
                // nothing
            }

            //user wasnt connected to the database, notify them
            if(result.equals("no connection"))
            {
                Toast.makeText(getActivity(), "Error connecting to server, please check your internet connection settings.",
                        Toast.LENGTH_LONG).show();
            }
            else
            {
                try
                {
                    JSONObject jsonResult = new JSONObject(result); //create json object based on the json response

                    //no error = successful login
                    if (!jsonResult.getBoolean("error"))
                    {
                        //create success alert
                        final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getActivity());

                        dlgAlert.setMessage("Account successfully created!");
                        dlgAlert.setTitle("Congratulations");
                        dlgAlert.setPositiveButton("OK", null);
                        dlgAlert.setCancelable(true);

                        //ok button to open main activity
                        dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        Intent intent = new Intent(getActivity(), BottomNavigate.class);
                                        startActivity(intent);

                                    }
                                });
                        dlgAlert.create().show();
                    }
                    //account failed to register due to incorrect input
                    else
                    {
                        //create alert message to notify user
                        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getActivity());

                        dlgAlert.setMessage("Error registering account. Email already exists");
                        dlgAlert.setTitle("Registration Failed");
                        dlgAlert.setPositiveButton("OK", null);
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();

                        //ok button closes alert message for the user to retry
                        dlgAlert.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                    }
                }
                catch (JSONException e)
                {
                    Toast.makeText(getActivity(), "Error Registering account.",
                            Toast.LENGTH_LONG).show();
                }
            }
        }

        public boolean isNetworkWorking(Context context)
        {
            if(context != null) {

                ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                return (networkInfo != null && networkInfo.isConnected());

            }
            else {
                Log.d("Network","Not Connected");
                return false;
            }
        }
    }
}
