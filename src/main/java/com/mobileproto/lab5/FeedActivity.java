package com.mobileproto.lab5;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FeedActivity extends Activity {

    public static String myname = "reaper";
    public static List<FeedItem> allData;
    public static TweetsDbHelper dbHelper;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allData = new ArrayList<FeedItem>();
        dbHelper = new TweetsDbHelper(this);

        // Define view fragments
        FeedFragment feedFragment = new FeedFragment();
        ConnectionFragment connectionFragment = new ConnectionFragment();
        SearchFragment searchFragment = new SearchFragment();
        TweetFragment tweetFragment = new TweetFragment();

        /*
         *  The following code is used to set up the tabs used for navigation.
         *  You shouldn't need to touch the following code.
         */
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        ActionBar.Tab feedTab = actionBar.newTab().setText(R.string.tab1);
        feedTab.setTabListener(new NavTabListener(feedFragment));

        ActionBar.Tab connectionTab = actionBar.newTab().setText(R.string.tab2);
        connectionTab.setTabListener(new NavTabListener(connectionFragment));

        ActionBar.Tab searchTab = actionBar.newTab().setText(R.string.tab3);
        searchTab.setTabListener(new NavTabListener(searchFragment));

        ActionBar.Tab newtweetTab = actionBar.newTab().setText(R.string.tab4);
        newtweetTab.setTabListener(new NavTabListener(tweetFragment));

        actionBar.addTab(feedTab);
        actionBar.addTab(connectionTab);
        actionBar.addTab(searchTab);
        actionBar.addTab(newtweetTab);

        actionBar.setStackedBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.android_dark_blue)));

        /*handler=new Handler();
        handler.post(new Runnable(){

            @Override
            public void run() {*/

                // Syncing with server
                updateDB();

                /*handler.postDelayed(this,5000); // Every 5 seconds
            }

        });*/

    }

    public void updateDB(){
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
        catch (Exception e){
            e.printStackTrace();
            System.out.println("JPARSER CANNOT RETRIEVE TWEETS");
        }

    }
    public static String getDate(Long milliSeconds)
    {
        // Create a DateFormatter object for displaying date in specified format.
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss MM/dd/yyyy");

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

}
