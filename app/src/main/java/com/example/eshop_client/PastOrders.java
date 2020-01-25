package com.example.eshop_client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PastOrders extends AppCompatActivity {

    Network network = new Network();
    JSONObject response;
    JSONArray orders;
    ArrayList<JSONArray> items = new ArrayList<>();//(Arrays.asList("brand 1", "brand 2", "brand 3", "brand 4", "brand 5", "brand 6", "brand 7","brand 8", "brand 9", "brand 10", "brand 11", "brand 12", "brand 13", "brand 14"));
    ArrayList<String> total = new ArrayList<>();//(Arrays.asList(R.drawable.nike1, R.drawable.nike2, R.drawable.nike3,R.drawable.nike4));
    ArrayList<String> status = new ArrayList<>();
    ArrayList<String> orderID = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_orders);
        network.execute("http://10.0.2.2:3000/getOrder?email="+DataHolder.getInstance().getEmail());
        response = network.jsono;
        try {
            orders = (JSONArray) response.get("data");
            for( int i = 0; i<orders.length();i+=1){
                JSONObject eachOrder = (JSONObject) orders.get(i);
                total.add(eachOrder.getString("total"));
                status.add(eachOrder.getString("status"));
                orderID.add(eachOrder.getString("ID"));
                JSONArray orderItems = eachOrder.getJSONArray("items");
                items.add(orderItems);
            }
        }catch (Exception e){

        }
    }
}
