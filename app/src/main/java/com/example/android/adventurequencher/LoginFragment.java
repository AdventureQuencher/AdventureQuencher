package com.example.android.adventurequencher;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
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
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

public class LoginFragment extends Fragment implements View.OnClickListener
{
    private EditText email;
    private EditText password;

    public LoginFragment()
    {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        email = (EditText) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);

        Button login = (Button) view.findViewById(R.id.loginButton);
        TextView signUpLink = (TextView) view.findViewById(R.id.link_signup);

        signUpLink.setOnClickListener(this);
        login.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    public void onClick(View view)
    {
        if (view.getId() == R.id.loginButton)
        {
            String emailInput = email.getText().toString();
            String passwordInput = password.getText().toString();

            new ValidateLogin(emailInput, passwordInput).execute();

        } else if (view.getId() == R.id.link_signup)
        {
            // Begin the transaction
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            // Replace the contents of the container with the new fragment
            ft.replace(R.id.login_placeholder, SignUpFragment.newInstance());
            // Complete the changes added above
            ft.commit();
            ft.addToBackStack(null);
        }
    }

    public static LoginFragment newInstance()
    {
        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private class ValidateLogin extends AsyncTask<String, String, String>
    {
        private String email;
        private String password;
        private ProgressDialog nDialog;

        public ValidateLogin(String emailInput, String passwordInput)
        {
            email = emailInput;
            password = passwordInput;
        }

        protected void onPreExecute()
        {
            Log.d("aq", "loading screen started");
            nDialog = new ProgressDialog(getActivity());
            nDialog.setMessage("Please wait..");
            nDialog.setTitle("Logging in");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }

        @Override
        protected String doInBackground(String... params)
        {

            HttpURLConnection connection;
            OutputStreamWriter request = null;
            String response = null;
            try
            {
                //establish connection string
                String link = "http://43.245.55.133/validateLogin.php";
                //user inputs stored in data string to be sent to server
                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                URL url = new URL(link);

                Log.d("aq", "credentials set");

                //connect to server using the link
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestMethod("POST");       //POST method

                //test internet connection by pinging a server
                if (!isNetworkWorking())
                {
                    response = "no connection";
                }
                else
                {
                    Log.d("aq", "parameters set, url connection opened");

                    //set up stream to write to server
                    request = new OutputStreamWriter(connection.getOutputStream());
                    request.write(data);    //send user input to server
                    request.flush();
                    request.close();    //close stream

                    //status code sent from server
                    int status = connection.getResponseCode();

                    Log.d("aq", "status code:" + status);

                    Log.d("aq", "starting to build string response from server");
                    // Read data sent from server
                    InputStream input = connection.getInputStream();
                    Log.d("aq", "input stream instantiate");

                    //server response
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                    Log.d("aq", "buffered reader instantiate");
                    StringBuilder sb = new StringBuilder();

                    Log.d("aq", "string builder instantiate");

                    String line;

                    Log.d("aq", "reading lines");

                    //read lines sent by server
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                    }

                    // Response from server after login process will be stored in response variable.
                    response = sb.toString();
                    Log.d("aq", "server response!!!!---->" + response);
                    //close streams
                    input.close();
                    reader.close();
                }
            }
            catch (Exception e)
            {

                //e.printStackTrace();
                Log.d("aq", "error!");
                Toast.makeText(getActivity(), "An error occurred when connecting to the server",
                        Toast.LENGTH_LONG).show();
            }


            return response;
        }

        @Override
        protected void onPostExecute(String result)
        {
            try
            {
                if (nDialog.isShowing())
                {
                    nDialog.dismiss();
                }
                nDialog = null;
            } catch (Exception e)
            {
                // nothing
            }
            if(result.equals("no connection"))
            {
                Toast.makeText(getActivity(), "Error connecting to server, please check your internet connection settings.",
                        Toast.LENGTH_LONG).show();
            }
            else
            {
                try
                {
                    JSONObject jsonResult = new JSONObject(result);
                    if (!jsonResult.getBoolean("error"))
                    {
                        Intent intent = new Intent(getActivity(), MenuMaps.class);
                        startActivity(intent);
                    } else
                    {
                        Toast.makeText(getActivity(), "Login failed",
                                Toast.LENGTH_LONG).show();

                    }
                }
                catch (JSONException e)
                {
                    Toast.makeText(getActivity(), "Error logging in.",
                            Toast.LENGTH_LONG).show();
                }
            }
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
