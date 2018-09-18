package com.example.android.adventurequencher;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(checkServices()){
            Thread myThread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(1000);
                        Intent intent = new Intent(getApplicationContext(), MenuMaps.class);
                        startActivity(intent);
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            myThread.start();
        }
    }

    public boolean checkServices(){
        Log.d(TAG, "checkServices: checking google services version");
        int check = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);
        if(check == ConnectionResult.SUCCESS){
            //Request for play services was successful
            Log.d(TAG, "checkServices: Google Play is Enabled");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(check)){
            //request failed but can be fixed
            Log.d(TAG, "checkServices: Google Play had a conflicting error");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, check, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else {
            Toast.makeText(this, "Could not make map request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
