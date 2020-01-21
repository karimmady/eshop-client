package com.example.eshop_client;

import org.apache.http.NameValuePair;

import java.util.List;

public class DataHolder {
    private String email;
    List<NameValuePair> postInfo;
    public String getEmail() {return email;}
    public void setEmail(String mail) {this.email = mail;}
    public void setPostInfo(List<NameValuePair> nameValuePairs){postInfo = nameValuePairs;}
    public List<NameValuePair>getPostInfo(){return postInfo;}
    private static final DataHolder holder = new DataHolder();
    public static DataHolder getInstance() {return holder;}
}
