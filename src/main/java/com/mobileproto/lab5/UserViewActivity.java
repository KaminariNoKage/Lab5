package com.mobileproto.lab5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaustin on 10/7/13.
 */
public class UserViewActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);

        Intent intent = getIntent();

        TextView username = (TextView) findViewById(R.id.feedItemUser);



        username.setText(intent.getStringExtra("username"));

        List<FeedItem> allUserviewData = new ArrayList<FeedItem>();

        //URL to GET all tweet data from the the USERNAME/tweets
        String feedURL = "http://twitterproto.herokuapp.com/" + username + "tweets";

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

    }
}
