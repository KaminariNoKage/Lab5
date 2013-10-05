package com.mobileproto.lab5;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kaustin on 10/5/13.
 */
public class TweetFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // FOR MAKING NEW TWEETS
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.new_tweet_fragment, null);

        Button newTweet = (Button) v.findViewById(R.id.tweetitButton);

        newTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Gets the words entered by the user
                final TextView enteredTweet = (TextView) getActivity().findViewById(R.id.newtweetField);
                String newtweetWords = enteredTweet.getText().toString();

                String urlToPost = "http://twitterproto.herokuapp.com/" + FeedActivity.myname + "/tweets";

                // Creating JSON Parser instance
                JSONParser jParser = new JSONParser();

                try {
                    //posting to URL place
                    jParser.postTweetToUrl(urlToPost, newtweetWords);
                }
                catch (Exception E){
                    System.out.println("FAILED TO POST NEW TWEET");
                }

                //Clear tweet Field so not hanging
                enteredTweet.setText("");
            }
        });


        return v;
    }
}
