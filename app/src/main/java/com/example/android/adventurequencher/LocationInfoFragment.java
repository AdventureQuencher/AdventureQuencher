package com.example.android.adventurequencher;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

//class collects the required tourist location information data from the server to display to the user
public class LocationInfoFragment extends Fragment
{
    private String title;

    public LocationInfoFragment()
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
        //inflate view
        View view = inflater.inflate(R.layout.fragment_location_info, container, false);
        Bundle args = getArguments();
        this.title = args.getString("locationTitle", "null");

        //get view elements and store as variables so server responses can be stored here
        TextView locTitle = view.findViewById(R.id.location_title);
        TextView locDesc = view.findViewById(R.id.location_description);
        TextView locAddr = view.findViewById(R.id.location_address);
        TextView locHours = view.findViewById(R.id.location_hours);

        Log.d("aq", "loc frag created");
        locTitle.setText(title);


        //asynchronously collect data and place in the required textviews
        new LocationInfo(title, locDesc, locAddr, locHours).execute();

        return view;
    }

    //class collects data from the server and displays it to the user
    private class LocationInfo extends AsyncTask<String, String, String>
    {
        String loc;
        TextView desc;
        TextView addr;
        TextView hours;

        public LocationInfo(String loc, TextView desc, TextView addr, TextView hours)
        {
            this.loc = loc;
            this.desc = desc;
            this.addr = addr;
            this.hours = hours;
        }

        //user has clicked on the pin and this class has been called
        protected void onPreExecute()
        {
            Log.d("aq", "started to load location description");
        }


        //complete connections
        @Override
        protected String doInBackground(String... params)
        {

            OutputStreamWriter request = null;
            HttpURLConnection connection;
            String response = null;
            try
            {
                String link = "http://43.245.55.133/locationDescription.php";   //server ip and uri
                //user inputs stored in data string to be sent to server
                String data = URLEncoder.encode("location", "UTF-8") + "=" + URLEncoder.encode(loc, "UTF-8");
                URL url = new URL(link);

                Log.d("aq", "credentials set");

                //connect to server using the link
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setRequestMethod("POST");       //POST method

                //test internet connection by pinging a server
                if (!isNetworkWorking(getActivity()))
                {
                    response = "no connection";
                } else
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
            }

            publishProgress(response);
            return response;
        }

        //get data item sent from the server
        @Override
        protected void onProgressUpdate(String... items)
        {
            //get location data and populate space
            try
            {
                JSONObject jsonResult = new JSONObject(items[0]);

                desc.setText(jsonResult.getString("desc"));
                addr.setText("Address: "+jsonResult.getString("address"));
                hours.setText("Opening Hours: "+jsonResult.getString("dates"));

            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        //check if network connection working for device
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
