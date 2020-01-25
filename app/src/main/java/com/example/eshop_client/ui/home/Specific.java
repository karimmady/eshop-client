package com.example.eshop_client.ui.home;

import android.content.Intent;
import android.os.Bundle;

import com.example.eshop_client.Cart;
import com.example.eshop_client.Home;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eshop_client.R;

import java.util.ArrayList;

public class Specific extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle Extras=getIntent().getExtras();
        final String ItemName;
        final int ItemImage;
        ArrayList<String>ItemSize;
        final String ItemID;
        final String ItemPrice;
        ItemName=Extras.getString("itemname");
        ItemImage=Extras.getInt("image");
        ItemSize=Extras.getStringArrayList("itemsize");
        ItemPrice=Extras.getString("itemprice");
        ItemID=Extras.getString("ID");
        final Cart cartfinal= new Cart();


        TextView itempr=findViewById(R.id.itempr);
        final String INU = ItemName.substring(0, 1).toUpperCase() + ItemName.substring(1);
        setTitle(INU);
        TextView itemna=findViewById(R.id.itemna);
        ImageView itemim=findViewById(R.id.itemim);

        itemim.setImageDrawable(getResources().getDrawable(ItemImage));
        itemna.setText(INU);
        itempr.setText(ItemPrice);

        final Spinner s = (Spinner) findViewById(R.id.planets_spinner);
        //final String itemsizeselected =
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ItemSize);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartfinal.cartuse.addtocart(ItemName,ItemImage,ItemPrice,s.getSelectedItem().toString(),"1",ItemID);
                Toast.makeText(Specific.this,"added to cart succesfully",Toast.LENGTH_SHORT).show();

            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add(0, 0, 0, "Cart").setIcon(R.drawable.cart)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent i = new Intent (Specific.this,Cart.class);
                startActivity(i);
                return true;
            }
        });


        return true;
    }


}
