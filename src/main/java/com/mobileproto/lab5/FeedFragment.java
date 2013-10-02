package com.mobileproto.lab5;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by evan on 9/25/13.
 */
public class FeedFragment extends Fragment {

    //JSON Node names
    static final String TAG_TWEET = "tweet";
    static final String TAG_USERNAME = "username";
    static final String TAG_DATE = "date";
    static final String TAG_ID = "_id";

    // contacts JSONArray
    JSONArray contacts = null;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.feed_fragment, null);


        //Getting JSON data
        //URL to GET all tweet data from
        String feedURL = "http://twitterproto.herokuapp.com/tweets";

        // Creating JSON Parser instance
        JSONParser jParser = new JSONParser();

        try {
            //Getting the array with all Tweets
            List allData = jParser.makeTweetList(feedURL);

            // Set up the ArrayAdapter for the feedList
            FeedListAdapter feedListAdapter = new FeedListAdapter(this.getActivity(), allData);
            ListView feedList = (ListView) v.findViewById(R.id.feedList);
            feedList.setAdapter(feedListAdapter);
        }
        catch (JSONException E){
            System.out.println("JPARSER CANNOT RETRIEVE TWEETS");
        }


        return v;

    }
}
