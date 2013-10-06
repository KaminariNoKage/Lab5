package com.mobileproto.lab5;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

/**
 * Created by kaustin on 10/5/13.
 */
public class DetailFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.feed_item, null);

        // Get all the connections (followers, mentions, etc.)
        //List<FeedNotification> notifications = JSONParser.getAllConnections();

        //ConnectionListAdapter connectionListAdapter = new ConnectionListAdapter(this.getActivity(), notifications);
        //ListView connectionList = (ListView) v.findViewById(R.id.connectionListView);

        //connectionList.setAdapter(connectionListAdapter);

        return v;
    }
}
