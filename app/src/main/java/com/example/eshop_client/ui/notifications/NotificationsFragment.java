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
    ArrayList<String> notifTitle = new ArrayList<>();//(Arrays.asList("brand 1", "brand 2", "brand 3", "brand 4", "brand 5", "brand 6", "brand 7","brand 8", "brand 9", "brand 10", "brand 11", "brand 12", "brand 13", "brand 14"));
    ArrayList<Integer> notifImages = new ArrayList<>();//(Arrays.asList(R.drawable.nike4, R.drawable.nike2));
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
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
                System.out.println(temp.get("name"));
                notifTitle.add((String)temp.get("name"));
                notifImages.add(getResources().getIdentifier((String)temp.get("name"), "drawable",getActivity().getApplicationContext().getPackageName()));

            }catch (Exception e){}
        }
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerViewnot);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        CustomAdaptertwo customAdapter = new CustomAdaptertwo(getContext(), notifTitle, notifImages);
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

    ArrayList<String> notifTitle;
    ArrayList<Integer> notifImages;
    Context context;

    public CustomAdaptertwo(Context context, ArrayList<String> notifTitle, ArrayList<Integer> notifImages) {
        this.context = context;

        this.notifTitle = notifTitle;
        this.notifImages = notifImages;
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
        holder.name.setText(notifTitle.get(position));
        holder.image.setImageResource(notifImages.get(position));
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open another activity on item click
                Intent intent = new Intent(context, NotificationsFragment.class);
                intent.putExtra("image", notifImages.get(position)); // put image data in Intent
                context.startActivity(intent); // start Intent
            }
        });

    }


    @Override
    public int getItemCount() {
        return notifTitle.size();
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


