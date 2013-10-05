package com.mobileproto.lab5;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evan on 9/26/13.
 */
public class SearchFragment extends Fragment {

    public static List<FeedItem> allSearchData;
    Button search;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allSearchData = new ArrayList<FeedItem>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.search_fragment, null);

        //SEARCH button setup
        Button search = (Button) v.findViewById(R.id.searchButton);
        final Context context = this.getActivity();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Gets the words entered by the user
                final TextView enteredSearch = (TextView) getActivity().findViewById(R.id.searchField);
                String searchWords = enteredSearch.getText().toString();

                //Getting JSON data
                //URL to GET all tweet data from the FEED
                String searchURL = "http://twitterproto.herokuapp.com/tweets?q=" + searchWords;

                // Creating JSON Parser
                JSONParser jParser = new JSONParser();

                try {
                    //Getting the array with all Tweets
                    allSearchData = jParser.makeSearchList(searchURL);
                }
                catch (Exception E){
                    System.out.println("JPARSER CANNOT RETRIEVE TWEETS");
                }

                // Set up the ArrayAdapter for the results of the search
                FeedListAdapter searchListAdapter = new FeedListAdapter(context, allSearchData);
                ListView searchList = (ListView) v.findViewById(R.id.searchResults);
                searchList.setAdapter(searchListAdapter);

            }

        });

        search.setFocusable(false);

        return v;
    }

}
