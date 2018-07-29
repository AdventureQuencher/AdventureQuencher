package com.example.android.adventurequencher;

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

            ValidateLogin request = new ValidateLogin(emailInput, passwordInput);

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
}
