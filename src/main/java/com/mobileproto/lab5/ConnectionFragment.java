package com.mobileproto.lab5;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by evan on 9/25/13.
 */
public class ConnectionFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.connections_fragment, null);

        // Get all the connections (followers, mentions, etc.)
        List<FeedNotification> notifications = JSONParser.getAllConnections();

        ConnectionListAdapter connectionListAdapter = new ConnectionListAdapter(this.getActivity(), notifications);
        ListView connectionList = (ListView) v.findViewById(R.id.connectionListView);

        connectionList.setAdapter(connectionListAdapter);

        return v;
    }
}
