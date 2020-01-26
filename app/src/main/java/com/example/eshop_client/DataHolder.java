package com.example.eshop_client;

import org.apache.http.NameValuePair;

import java.util.List;

public class DataHolder {
    private String email;
    List<NameValuePair> postInfo;
    private boolean night;
    public String getEmail() {return email;}
    public boolean getNight(){return night;}
    public void setNight(boolean nightmode) {night = nightmode;}
    public void setEmail(String mail) {this.email = mail;}
    public void setPostInfo(List<NameValuePair> nameValuePairs){postInfo = nameValuePairs;}
    public List<NameValuePair>getPostInfo(){return postInfo;}
    private static final DataHolder holder = new DataHolder();
    public static DataHolder getInstance() {return holder;}

}
