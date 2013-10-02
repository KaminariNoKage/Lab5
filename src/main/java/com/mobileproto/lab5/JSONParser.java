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

    // constructor
    public JSONParser() {
        System.out.println("JSONPARSER CONSTRUCTED");

    }

    public JSONArray getJSONFromUrl(String url) {

        //Passing URL to Asynced task, retrieving JSON string from internet
        BackgroundTask urlJSON = new BackgroundTask();
        urlJSON.execute(url);

        try {
            //Converting String => JSONObject => JSONArray
            System.out.println("RETRIEVED JSON");
            JSONObject json = new JSONObject(urlJSON.get());
            JSONArray jsonarray = json.getJSONArray("tweets");
            return jsonarray;
        }
        catch (Exception e){
            Log.v("json err", e.getMessage());
            System.out.println("UNABLE TO CONVERT JSON");
        }


        return null;
    }

    public List makeTweetList (String feedURL) throws JSONException {

        //Extracting Data and putting it in the feed
        JSONArray allTweets = getJSONFromUrl(feedURL);
        List<FeedItem> allData = new ArrayList<FeedItem>();

        for (int i=0; i < allTweets.length(); i++){
            try{
                //Unpacking tweet into displayable form
                String username = "@" + allTweets.getJSONObject(i).get("username").toString();
                String tweet = allTweets.getJSONObject(i).get("tweet").toString();
                FeedItem feedTweet = new FeedItem(username, tweet);
                allData.add(feedTweet);
            }
            catch (JSONException E){
                //Will throw if the JSONArray is not valid or null
                System.out.print("JSON NOT FOUND");
            }
        }

        System.out.println("ALL TWEETS RETRIEVED");

        return allData;
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