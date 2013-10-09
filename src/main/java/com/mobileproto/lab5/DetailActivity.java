package com.mobileproto.lab5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by kaustin on 10/6/13.
 */
public class DetailActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);

        Intent intent = getIntent();

        final TextView username = (TextView) findViewById(R.id.feedItemUser);
        final TextView tweet = (TextView) findViewById(R.id.feedText);
        final TextView date = (TextView) findViewById(R.id.feedDate);



        username.setText(intent.getStringExtra("username"));
        tweet.setText(intent.getStringExtra("tweet"));
        date.setText(intent.getStringExtra("date"));

        ImageView img = (ImageView) findViewById(R.id.imageView);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View vtwo) {
                //Getting title (id) of what is clicked
                TextView textView = (TextView) vtwo.findViewById(R.id.feedItemUser);
                String getUserName = textView.getText().toString();

                //Creating intent to pass information
                Intent in = new Intent(getApplicationContext(), UserViewActivity.class);
                in.putExtra("username", username.toString());

                //Going to new display of the note
                startActivity(in);
            }
        });

    }

}
