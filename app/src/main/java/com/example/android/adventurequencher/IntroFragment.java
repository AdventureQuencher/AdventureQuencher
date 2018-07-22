package com.example.android.adventurequencher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class IntroFragment extends Fragment implements View.OnClickListener
{
    View rootView;
    public IntroFragment()
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
        View view = inflater.inflate(R.layout.fragment_intro, container, false);
        Button signupButton = (Button) view.findViewById(R.id.signUpButton);
        Button loginButton = (Button) view.findViewById(R.id.loginButtonStart);

        signupButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View view)
    {
        if(view.getId() == R.id.loginButtonStart)
        {
            // Begin the transaction
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            // Replace the contents of the container with the new fragment
            ft.replace(R.id.login_placeholder, LoginFragment.newInstance());
            // Complete the changes added above
            ft.commit();
        }
        if(view.getId() == R.id.signUpButton)
        {
            Log.w("appmsg", "Sign up button pressed");
        }
    }

    public static IntroFragment newInstance()
    {
        Bundle args = new Bundle();

        IntroFragment fragment = new IntroFragment();
        fragment.setArguments(args);

        return fragment;
    }

}
