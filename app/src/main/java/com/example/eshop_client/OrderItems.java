package com.example.eshop_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OrderItems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_items);
        Intent intent = getIntent();
        String jsonArray = intent.getStringExtra("jsonArray");
        ArrayList<String> finalArrayOrderItems= new ArrayList<>();
        try {
            JSONArray array = new JSONArray(jsonArray);
            System.out.println(array);
            for(int i = 0;i<array.length();i+=1){
                JSONObject item = (JSONObject) array.get(i);
                String temp = new String();
                temp = "Item: "+ item.get("name") + "\nPrice: " + item.get("price") + "\nSize: "+ item.get("size");
                finalArrayOrderItems.add(temp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



       ArrayAdapter<String> AdapterforOrderItems= new ArrayAdapter<String>(OrderItems.this, android.R.layout.simple_list_item_1,finalArrayOrderItems);
        final ListView listOrderitems = findViewById(R.id.orderitemslist);

        listOrderitems.setAdapter(AdapterforOrderItems);

        listOrderitems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

    }
}
