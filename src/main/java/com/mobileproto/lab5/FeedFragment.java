package com.mobileproto.lab5;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

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

    public static List<FeedItem> allData;

    public void onCreate(Bundle savedInstanceState) {
        allData = FeedActivity.allData;
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.feed_fragment, null);


        // Set up the ArrayAdapter for the feedList
        FeedListAdapter feedListAdapter = new FeedListAdapter(this.getActivity(), allData);
        ListView feedList = (ListView) v.findViewById(R.id.feedList);
        feedList.setAdapter(feedListAdapter);

        feedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.print("CLICKED ME");
                //Getting title (id) of what is clicked
                TextView textView = (TextView) view.findViewById(R.id.feedDate);
                String getTweetDate = textView.getText().toString();

                Cursor tweetdb = FeedActivity.dbHelper.getTweetFeedDB(getTweetDate);
                //Moving the cursor to the selected point (what was clicked on) in the database

                tweetdb.moveToFirst();
                //Getting the Title and content of the note
                String username = tweetdb.getString(2);
                String tweet = tweetdb.getString(3);
                String date = tweetdb.getString(4);

                //Creating intent to pass information
                Intent in = new Intent(getActivity(), DetailActivity.class);
                in.putExtra("username", username);
                in.putExtra("tweet", tweet);
                in.putExtra("date", date);

                //Going to new display of the note
                startActivity(in);
            }
        });

        return v;

    }
}
