package com.mobileproto.lab5;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaustin on 10/4/13.
 */
public class UserViewFragment extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.user_fragment, null);

        TextView userviewname = (TextView) v.findViewById(R.id.userviewname);
        List<FeedItem> allUserviewData = new ArrayList<FeedItem>();

        //URL to GET all tweet data from the the USERNAME/tweets
        String feedURL = "http://twitterproto.herokuapp.com/" + userviewname + "tweets";

        // Creating JSON Parser instance
        JSONParser jParser = new JSONParser();

        try {
            //Getting the array with all Tweets
            jParser.makeTweetList(feedURL);
            allUserviewData = jParser.getAllData();
        }
        catch (Exception E){
            System.out.println("JPARSER CANNOT RETRIEVE TWEETS");
        }


        // Set up the ArrayAdapter for the feedList
        FeedListAdapter feedListAdapter = new FeedListAdapter(this.getActivity(), allUserviewData);
        ListView feedList = (ListView) v.findViewById(R.id.feedList);
        feedList.setAdapter(feedListAdapter);

        return v;

    }
}
