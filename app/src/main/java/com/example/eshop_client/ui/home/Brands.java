package com.example.eshop_client.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.eshop_client.CustomAdapter;
import com.example.eshop_client.Network;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eshop_client.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class Brands extends AppCompatActivity {
    Network network = new Network();
    JSONObject brands;
    JSONArray brandsArray;
    ArrayList<String> brandNames = new ArrayList<>();//(Arrays.asList("brand 1", "brand 2", "brand 3", "brand 4", "brand 5", "brand 6", "brand 7","brand 8", "brand 9", "brand 10", "brand 11", "brand 12", "brand 13", "brand 14"));
    ArrayList<Integer> brandImages = new ArrayList<>(Arrays.asList(R.drawable.bisho, R.drawable.tahan, R.drawable.tahan, R.drawable.tahan, R.drawable.tahan, R.drawable.tahan, R.drawable.tahan,R.drawable.tahan, R.drawable.tahan, R.drawable.tahan, R.drawable.tahan, R.drawable.tahan, R.drawable.tahan, R.drawable.tahan));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brands);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);

         String A2 = new String();
         Bundle Extras=getIntent().getExtras();
         A2=Extras.getString("Bname");
         final String A3 = A2.substring(0, 1).toUpperCase() + A2.substring(1);
         setTitle(A3);
         fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uri = "http://maps.google.com/maps?daddr=" + A3 ;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });



        try {
            network.execute("http://172.20.10.2:3000/getbrands").get();
            brands = network.jsono;
            brandsArray = (JSONArray) brands.get("data");
            System.out.println(brandsArray);
        }catch (Exception e){
            System.out.println("Network Error");
        }
        for (int i = 0; i<brandsArray.length(); i+=1){
            try {
                JSONObject temp = (JSONObject)brandsArray.get(i);
                System.out.println(temp.get("name"));
                brandNames.add((String)temp.get("name"));
//                int resID = getResId((String)temp.get("name"), R.drawable.class);
//                ContextCompat.getDrawable(getActivity().getApplicationContext().getPackageName(), R.drawable.<you_name_it>)

//                brandImages.add(getResources().getIdentifier((String)temp.get("name")+".jpg", "drawable",this));
            }catch (Exception e){}
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        // set a GridLayoutManager with 2 number of columns , horizontal gravity and false value for reverseLayout to show the items from start to end
        GridLayoutManager gridLayoutManager = new GridLayoutManager(Brands.this,1);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        //  call the constructor of CustomAdapter   to send the reference and data to Adapter
        CustomAdapter CustomAdapter   = new CustomAdapter  (Brands.this, brandNames, brandImages);
        recyclerView.setAdapter(CustomAdapter); // set the Adapter to RecyclerView


    }
    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}




