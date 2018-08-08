package com.example.android.adventurequencher;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpFragment extends Fragment implements View.OnClickListener
{
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

        signUpButton.setOnClickListener(this);
        loginLink.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }


    public void onClick(View view)
    {
        if (view.getId() == R.id.createAccountButton)
        {
            Toast.makeText(getActivity(), "Account created!", Toast.LENGTH_LONG).show();
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
}
