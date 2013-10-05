package com.mobileproto.lab5;

//COPIED FROM http://www.androidhive.info/2012/01/android-json-parsing-tutorial/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class JSONParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    public static List<FeedItem> allData; //All the data of the main feed
    public static List<FeedNotification> allMentions; //All tweets where user is mentioned
    public static List<FeedNotification> allConnections; //All connections of the user
    public static BackgroundTask urlJSON;

    // constructor
    public JSONParser() {
        allData = new ArrayList<FeedItem>();
        allMentions = new ArrayList<FeedNotification>();
        allConnections = new ArrayList<FeedNotification>();
        urlJSON = new BackgroundTask();
    }

    public JSONArray getJSONFromUrl(String url, String minijson) {

        //Passing URL to Asynced task, retrieving JSON string from internet
        BackgroundTask urlJSON = new BackgroundTask();
        urlJSON.execute(url);

        try {
            //Converting String => JSONObject => JSONArray
            JSONObject json = new JSONObject(urlJSON.get());
            JSONArray jsonarray = json.getJSONArray(minijson);
            return jsonarray;
        }
        catch (Exception e){
            Log.v("json err", e.getMessage());
            System.out.println("UNABLE TO CONVERT JSON to usable format");
        }

        return null;
    }

    public void makeTweetList (String feedURL) throws JSONException, Exception {

        //Extracting Data and putting it in the feed
        JSONArray allTweets = getJSONFromUrl(feedURL, "tweets");

        for (int i=0; i < allTweets.length(); i++){
            try{
                //Unpacking tweet into displayable form
                String username = "@" + allTweets.getJSONObject(i).get("username").toString();
                String tweet = allTweets.getJSONObject(i).get("tweet").toString();
                FeedItem feedTweet = new FeedItem(username, tweet);
                allData.add(feedTweet);

                checkMentions(allTweets.getJSONObject(i));
            }
            catch (JSONException E){
                //Will throw if the JSONArray is not valid or null
                System.out.print("JSON NOT FOUND");
            }
        }

        makeConnections();

        System.out.println("ALL TWEETS RETRIEVED");
    }

    public List<FeedItem> getAllData(){
        return allData;
    }

    public static void checkMentions(JSONObject tweetjson) throws JSONException{
        //If user is mentioned in a tweet, add to allMentions
        String username = "@" + tweetjson.get("username").toString();
        String tweet = tweetjson.get("tweet").toString();
        String myname = "@" + FeedActivity.myname;

        //Checks to see if the user is mentioned in the tweets
        if(tweet.contains(myname)){
            MentionNotification mention = new MentionNotification(username, myname, tweet);
            //Then add it
            allConnections.add(mention);
        }

    }

    public static void makeConnections() throws JSONException, Exception {

        //Getting followers
        String followURL = "http://twitterproto.herokuapp.com/" + FeedActivity.myname + "/following";

        //Passing URL to Asynced task, retrieving JSON string from internet
        urlJSON.execute(followURL);

        JSONObject json = new JSONObject(urlJSON.get());
        JSONArray jsonarray = json.getJSONArray("following");

        //Goes through main tweets to check if @myname mentioned
        String myname = "@" + FeedActivity.myname;

        for (int i=0; i < jsonarray.length(); i++){
            String username = "@" + jsonarray.get(i);
            FollowNotification follow = new FollowNotification(username, myname);
            allConnections.add(follow);
        }

    }

    public static List<FeedNotification> getAllConnections(){
        return allConnections;
    }

    public List<FeedItem> makeSearchList (String feedURL) throws JSONException, Exception {

        //Extracting Data and putting it in the search list
        List<FeedItem> allSearchData = new ArrayList<FeedItem>();
        JSONArray allTweets = getJSONFromUrl(feedURL, "tweets");

        for (int i=0; i < allTweets.length(); i++){
            try{
                //Unpacking tweet into displayable form
                String username = "@" + allTweets.getJSONObject(i).get("username").toString();
                String tweet = allTweets.getJSONObject(i).get("tweet").toString();
                FeedItem feedTweet = new FeedItem(username, tweet);
                allSearchData.add(feedTweet);
            }
            catch (JSONException E){
                //Will throw if the JSONArray is not valid or null
                System.out.print("JSON NOT FOUND");
            }
        }

        System.out.println("RETURNING: " + allSearchData);

        return allSearchData;
    }

    private class BackgroundTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String...url){

            // Making HTTP request
            try {
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                //Calling GET to specified URL
                HttpGet httpGet = new HttpGet(url[0]);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                json = sb.toString();
            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }

            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}