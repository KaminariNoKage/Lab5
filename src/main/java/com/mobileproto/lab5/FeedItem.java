package com.mobileproto.lab5;

/**
 * Created by evan on 9/25/13.
 */
public class FeedItem {

    public String text;
    public String userName;
    public String date;

    public FeedItem(String userName, String text, String date){
        this.userName = userName;
        this.text = text;
        this.date = date;
    }

}
