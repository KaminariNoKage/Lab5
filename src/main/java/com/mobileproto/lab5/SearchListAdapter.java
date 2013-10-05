package com.mobileproto.lab5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaustin on 10/4/13.
 */
public class SearchListAdapter extends ArrayAdapter<FeedItem> {

    private final Context context;
    private final List<FeedItem> data;

    public SearchListAdapter(Context context,  List<FeedItem> data){
        super(context, R.layout.search_item, data);
        this.context = context;
        this.data = data;
        System.out.println("CONTEXT: " + this.context + ", DATA: " + this.data);
    }

    private class SearchItemHolder{

        TextView userName;
        TextView text;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        System.out.println("GETTING SEARCH LIST VIEW");

        SearchItemHolder holder;
        View searchRow = convertView;

        if(searchRow == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            searchRow = inflater.inflate(R.layout.search_item, parent, false);
            holder = new SearchItemHolder();
            holder.userName = (TextView) searchRow.findViewById(R.id.searchItemUser);
            holder.text = (TextView) searchRow.findViewById(R.id.searchText);

            searchRow.setTag(holder);
        } else {
            holder = (SearchItemHolder) searchRow.getTag();
        }

        FeedItem item = data.get(position);

        holder.userName.setText(item.userName);
        holder.text.setText(item.text);

        return searchRow;
    }
}
