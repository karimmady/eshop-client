package com.example.eshop_client.ui.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.eshop_client.MainActivity;
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

    ArrayList<String> itemName = new ArrayList<>();//(Arrays.asList("brand 1", "brand 2", "brand 3", "brand 4", "brand 5", "brand 6", "brand 7","brand 8", "brand 9", "brand 10", "brand 11", "brand 12", "brand 13", "brand 14"));
    ArrayList<Integer> itemImage = new ArrayList<>();//(Arrays.asList(R.drawable.nike1, R.drawable.nike2, R.drawable.nike3,R.drawable.nike4));
    ArrayList<String> itemPrice = new ArrayList<>();
    ArrayList<String> itemSizes = new ArrayList<>();
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
            network.execute("http://10.0.2.2:3000/getbrands").get();
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
                itemName.add((String)temp.get("name"));
                itemImage.add(getResources().getIdentifier((String)temp.get("name"), "drawable",getApplicationContext().getPackageName()));
                itemPrice.add((String)temp.get("name"));
                itemSizes.add((String)temp.get("name"));
//                int resID = getResId((String)temp.get("name"), R.drawable.class);
//                ContextCompat.getDrawable(getActivity().getApplicationContext().getPackageName(), R.drawable.<you_name_it>)

//                itemImage.add(getResources().getIdentifier((String)temp.get("name")+".jpg", "drawable",this));
            }catch (Exception e){}
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView2);
        // set a GridLayoutManager with 2 number of columns , horizontal gravity and false value for reverseLayout to show the items from start to end
        GridLayoutManager gridLayoutManager = new GridLayoutManager(Brands.this,2);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        //  call the constructor of CustomAdapterThree   to send the reference and data to Adapter
        CustomAdapterThree CustomAdapterThree   = new CustomAdapterThree  (Brands.this, itemName, itemImage,itemPrice,itemSizes);
        recyclerView.setAdapter(CustomAdapterThree); // set the Adapter to RecyclerView


    }
}

    class CustomAdapterThree extends RecyclerView.Adapter<CustomAdapterThree.MyViewHolder> {

    ArrayList<String> itemName;
    ArrayList<String> itemPrice;
    ArrayList<String> itemSizes;
    ArrayList<Integer> itemImage;
    Context context;

    public CustomAdapterThree(Context context, ArrayList<String> itemName, ArrayList<Integer> itemImage,ArrayList<String> itemPrice,ArrayList<String> itemSizes) {
        this.context = context;
        this.itemPrice =itemPrice;
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.itemSizes = itemSizes;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayoutitems, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        holder.name.setText(itemName.get(position));
        holder.image.setImageResource(itemImage.get(position));
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open another activity on item click
                Intent i= new Intent(context, Specific.class);
                Bundle bundle=new Bundle();
                bundle.putString("itemname",itemName.get(position));
                bundle.putString("itemprice",itemPrice.get(position));
                bundle.putString("itemsize",itemSizes.get(position));
                bundle.putIntegerArrayList("image",itemImage);

                context.startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return itemName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name;
        TextView itemP;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.itemname);
            image = (ImageView) itemView.findViewById(R.id.itemimage);
            itemP=itemView.findViewById(R.id.itemprice);
        }
    }
}




