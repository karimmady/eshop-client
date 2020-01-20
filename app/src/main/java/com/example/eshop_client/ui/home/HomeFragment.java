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

import com.example.eshop_client.CustomAdapter;

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
    ArrayList<Integer> brandImages = new ArrayList<>();//(Arrays.asList(R.drawable.Bisho, R.drawable.tahan, R.drawable.tahan, R.drawable.tahan, R.drawable.tahan, R.drawable.tahan, R.drawable.tahan,R.drawable.tahan, R.drawable.tahan, R.drawable.tahan, R.drawable.tahan, R.drawable.tahan, R.drawable.tahan, R.drawable.tahan));
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        try {
            network.execute("http://192.168.1.41:3000/getbrands").get();
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

