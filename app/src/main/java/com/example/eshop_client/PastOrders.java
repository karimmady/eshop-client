package com.example.eshop_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


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
        setTitle("Past Orders");
        try {
            network.execute("http://192.168.1.20:3000/getOrder?email="+DataHolder.getInstance().getEmail()).get();
            response = network.jsono;
            orders = (JSONArray) response.get("data");
            for( int i = 0; i<orders.length();i+=1){
                JSONObject eachOrder = (JSONObject) orders.get(i);
                total.add(eachOrder.getString("total"));
                status.add(eachOrder.getString("status"));
                orderID.add(eachOrder.getString("_id"));
                JSONArray orderItems = eachOrder.getJSONArray("items");
                items.add(orderItems);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println(total);
        ArrayList<String> finalarraypastorders= new ArrayList<>();
        final ListView listPastOrder = findViewById(R.id.listPastOrders);
        for(int i=0;i<total.size();i++)
        {
                    finalarraypastorders.add("\n"+"OrderID :"+orderID.get(i)+"\n"+"Status :"+status.get(i)+"\n"+"Total :"+total.get(i)+"\n");
        }



        ArrayAdapter<String> AdapterforPastorders = new ArrayAdapter<String>(PastOrders.this, android.R.layout.simple_list_item_1,finalarraypastorders);
        listPastOrder.setAdapter(AdapterforPastorders);

        listPastOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(PastOrders.this, OrderItems.class);
                i.putExtra("jsonArray", items.get(position).toString());
                startActivity(i);


            }
        });
    }
}
