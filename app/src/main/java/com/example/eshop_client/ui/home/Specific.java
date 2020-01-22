package com.example.eshop_client.ui.home;

import android.os.Bundle;

import com.example.eshop_client.Cart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.eshop_client.R;

import java.util.ArrayList;

public class Specific extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Cart addtocart= new Cart();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Bundle Extras=getIntent().getExtras();
        String ItemName;
        int ItemImage;
        ArrayList<String>ItemSize;
        String ItemPrice;
        ItemName=Extras.getString("itemname");
        final String INU = ItemName.substring(0, 1).toUpperCase() + ItemName.substring(1);
        ItemImage=Extras.getInt("image");
        ItemSize=Extras.getStringArrayList("itemsize");
        ItemPrice=Extras.getString("itemprice");
        TextView itemna=findViewById(R.id.itemna);
        ImageView itemim=findViewById(R.id.itemim);
        TextView itempr=findViewById(R.id.itempr);

       // itemim.setImageDrawable(getResources().getDrawable(ItemImage));
        itemna.setText(INU);
        itempr.setText(ItemPrice);

        Spinner s = (Spinner) findViewById(R.id.planets_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ItemSize);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);


    }

}
