package com.mobileproto.lab5;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

    public List<FeedItem> allData;


    public void onCreate(Bundle savedInstanceState) {
        allData = new ArrayList<FeedItem>();
        super.onCreate(savedInstanceState);

        //Getting JSON data
        //URL to GET all tweet data from the FEED
        String feedURL = "http://twitterproto.herokuapp.com/tweets";

        // Creating JSON Parser instance
        JSONParser jParser = new JSONParser();

        try {
            //Getting the array with all Tweets
            jParser.makeTweetList(feedURL);
            allData = jParser.getAllData();
        }
        catch (Exception E){
            System.out.println("JPARSER CANNOT RETRIEVE TWEETS");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.feed_fragment, null);


        // Set up the ArrayAdapter for the feedList
       FeedListAdapter feedListAdapter = new FeedListAdapter(this.getActivity(), allData);
        ListView feedList = (ListView) v.findViewById(R.id.feedList);
        feedList.setAdapter(feedListAdapter);

        /*feedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Creating intent to pass information
                Intent in = new Intent(getApplicationContext(), NoteDetailActivity.class);

                //Getting the Title and content of the note
                String title = notedb.getString(1);
                String text = notedb.getString(2);
                in.putExtra("title", title);
                in.putExtra("text", text);

                //Going to new display of the note
                startActivity(in);
            }
        }); */

        return v;

    }
}
