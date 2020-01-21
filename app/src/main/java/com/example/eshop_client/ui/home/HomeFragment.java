package com.example.eshop_client.ui.home;

import android.content.Context;
import android.content.Intent;
//import android.net.Network;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.eshop_client.DataHolder;
import com.example.eshop_client.Network;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eshop_client.MainActivity;
import com.example.eshop_client.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    Network network = new Network();
    JSONObject brands;
    JSONArray brandsArray;
    ArrayList<String> brandNames = new ArrayList<>();//(Arrays.asList("brand 1", "brand 2", "brand 3", "brand 4", "brand 5", "brand 6", "brand 7","brand 8", "brand 9", "brand 10", "brand 11", "brand 12", "brand 13", "brand 14"));
    ArrayList<Integer> brandImages = new ArrayList<>(Arrays.asList(R.drawable.zara, R.drawable.zara, R.drawable.nike));
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
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
                System.out.println(DataHolder.getInstance().getEmail());
                brandNames.add((String)temp.get("name"));
                brandImages.add(getResources().getIdentifier((String)temp.get("name"), "drawable",getActivity().getApplicationContext().getPackageName()));
            }catch (Exception e){}
        }
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        // set a GridLayoutManager with 2 number of columns , horizontal gravity and false value for reverseLayout to show the items from start to end
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapter customAdapter = new CustomAdapter(getContext(), brandNames, brandImages);
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

    class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    ArrayList<String> brandNames;
    ArrayList<Integer> brandImages;
    Context context;

    public CustomAdapter(Context context, ArrayList<String> brandNames, ArrayList<Integer> brandImages) {
        this.context = context;

        this.brandNames = brandNames;
        this.brandImages = brandImages;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        holder.name.setText(brandNames.get(position));
        holder.image.setImageResource(brandImages.get(position));
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open another activity on item click
                Intent i= new Intent(context, Brands.class);
                Bundle X=new Bundle();
                X.putString("Bname",brandNames.get(position));
                i.putExtras(X);
                context.startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return brandNames.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            name = (TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);

        }
    }
}

