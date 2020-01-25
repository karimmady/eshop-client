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

public class OrderItems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_items);

        ArrayList<String> finalarrayorderitems= new ArrayList<>();

       ArrayAdapter<String> AdapterforOrderItems= new ArrayAdapter<String>(OrderItems.this, android.R.layout.simple_list_item_1,finalarrayorderitems);
        final ListView listOrderitems = findViewById(R.id.orderitemslist);

        listOrderitems.setAdapter(AdapterforOrderItems);

        listOrderitems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) listOrderitems.getItemAtPosition(position);
                Toast.makeText(OrderItems.this,clickedItem,Toast.LENGTH_LONG).show();
    }
});
    }
}
