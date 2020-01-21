package com.example.eshop_client.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    ArrayList<String> brandNames = new ArrayList<>();//(Arrays.asList("brand 1", "brand 2", "brand 3", "brand 4", "brand 5", "brand 6", "brand 7","brand 8", "brand 9", "brand 10", "brand 11", "brand 12", "brand 13", "brand 14"));
    ArrayList<Integer> brandImages = new ArrayList<>(Arrays.asList(R.drawable.bisho, R.drawable.tahan, R.drawable.tahan, R.drawable.tahan, R.drawable.tahan, R.drawable.tahan, R.drawable.tahan,R.drawable.tahan, R.drawable.tahan, R.drawable.tahan, R.drawable.tahan, R.drawable.tahan, R.drawable.tahan, R.drawable.tahan));
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        View root = inflater.inflate(R.layout.fragment_home, container, false);
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
                System.out.println(getResources().getIdentifier((String)temp.get("name")+".jpg", "drawable", getActivity().getApplicationContext().getPackageName()));
                System.out.println(R.drawable.hawas);
//                brandImages.add(getResources().getIdentifier((String)temp.get("name")+".jpg", "drawable",this));
            }catch (Exception e){}
        }
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        // set a GridLayoutManager with 2 number of columns , horizontal gravity and false value for reverseLayout to show the items from start to end
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdaptertwo customAdapter = new CustomAdaptertwo(getContext(), brandNames, brandImages);
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


