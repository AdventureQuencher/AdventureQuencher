package com.example.android.adventurequencher;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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

        }
        else if(view.getId() == R.id.link_signup)
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

        public ValidateLogin(String emailInput, String passwordInput)
        {
            email = emailInput;
            password = passwordInput;
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
                String link = "http://43.245.55.133/validateLogin.php";
                String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                URL url = new URL(link);

                Log.d("aq", "credentials set");

                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestMethod("POST");

                Log.d("aq", "parameters set, url connection opened");

                request = new OutputStreamWriter(connection.getOutputStream());
                request.write(data);
                request.flush();
                request.close();

                int status = connection.getResponseCode();

                Log.d("aq", "status code:"+status);

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
                Log.d("aq", "server response!!!!---->"+response);
                input.close();
                reader.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Log.d("aq", "error!");
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result)
        {
            Toast.makeText(getActivity(), result,
                    Toast.LENGTH_LONG).show();
            /*
            if (result.equalsIgnoreCase("true"))
            {
                Intent intent = new Intent(getActivity(),MenuMaps.class);
                startActivity(intent);
            } else
            {
                TextView error = (TextView) getView().findViewById(R.id.loginError);

                error.setVisibility(View.VISIBLE);
            }*/
        }
    }
}
