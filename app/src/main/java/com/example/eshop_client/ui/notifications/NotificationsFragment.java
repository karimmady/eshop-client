package com.example.eshop_client.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eshop_client.Network;
import com.example.eshop_client.R;
import com.example.eshop_client.ui.home.Specific;
import com.example.eshop_client.ui.notifications.CustomAdaptertwo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    Network network = new Network();
    JSONObject brands;
    JSONArray brandsArray;
    ArrayList<String> itemName = new ArrayList<>();//(Arrays.asList("brand 1", "brand 2", "brand 3", "brand 4", "brand 5", "brand 6", "brand 7","brand 8", "brand 9", "brand 10", "brand 11", "brand 12", "brand 13", "brand 14"));
    ArrayList<Integer> itemImage = new ArrayList<>();//(Arrays.asList(R.drawable.nike1, R.drawable.nike2, R.drawable.nike3,R.drawable.nike4));
    ArrayList<String> itemPrice = new ArrayList<>();
    ArrayList<String> itemSizes = new ArrayList<>();
    ArrayList<ArrayList<String>> allSizes = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        try {
            network.execute("http://192.168.1.20:3000/getFashion").get();
            brands = network.jsono;
            brandsArray = (JSONArray) brands.get("data");
            System.out.println(brandsArray);
        }catch (Exception e){
            System.out.println("Network Error");
        }
        for (int i = 0; i<brandsArray.length(); i+=1){
            try {
                JSONObject temp = (JSONObject) brandsArray.get(i);
                itemName.add((String)temp.get("name"));
                itemImage.add(getResources().getIdentifier(((String)temp.get("ID")).toLowerCase(), "drawable",getContext().getApplicationContext().getPackageName()));
                itemPrice.add((String)temp.get("price"));
                JSONArray tempSize = (JSONArray) temp.get("size");
                itemSizes.clear();
                for(int j = 0; j<tempSize.length();j+=1){
                    JSONObject eachSize = tempSize.getJSONObject(j);
                    itemSizes.add(eachSize.getString("name"));
                }
                allSizes.add(new ArrayList<String>(itemSizes));
                System.out.println(itemSizes);
            }catch (Exception e){}
        }
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerViewnot);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        CustomAdaptertwo customAdapter = new CustomAdaptertwo(getContext(), itemName, itemImage,itemPrice,itemSizes,allSizes);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView

        return root;
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

    class CustomAdaptertwo extends RecyclerView.Adapter<com.example.eshop_client.ui.notifications.CustomAdaptertwo.MyViewHolder> {


    Context context;
    ArrayList<String> itemName;
    ArrayList<String> itemPrice;
    ArrayList<String> itemSizes;
    ArrayList<Integer> itemImage;
    ArrayList<ArrayList<String>>allSizes;

    public CustomAdaptertwo(Context context, ArrayList<String> itemName, ArrayList<Integer> itemImage,ArrayList<String> itemPrice,ArrayList<String> itemSizes, ArrayList<ArrayList<String>>allSizes) {
        this.context = context;
        this.itemPrice =itemPrice;
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.itemSizes = itemSizes;
        this.allSizes = allSizes;;
    }

    @Override
    public com.example.eshop_client.ui.notifications.CustomAdaptertwo.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayoutnotif, parent, false);
        // set the view's size, margins, paddings and layout parameters
        com.example.eshop_client.ui.notifications.CustomAdaptertwo.MyViewHolder vh = new com.example.eshop_client.ui.notifications.CustomAdaptertwo.MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(com.example.eshop_client.ui.notifications.CustomAdaptertwo.MyViewHolder holder, final int position) {
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
                bundle.putStringArrayList("itemsize",allSizes.get(position));
                bundle.putInt("image",itemImage.get(position));
                i.putExtras(bundle);
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
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.nottit);
            image = (ImageView) itemView.findViewById(R.id.notim);

        }
    }
}


