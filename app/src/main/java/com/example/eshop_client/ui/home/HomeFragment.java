package com.example.eshop_client.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eshop_client.Home;
import com.example.eshop_client.MainActivity;
import com.example.eshop_client.R;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    ArrayList<String> brandNames = new ArrayList<>(Arrays.asList("brand 1", "brand 2", "brand 3", "brand 4", "brand 5", "brand 6", "brand 7","brand 8", "brand 9", "brand 10", "brand 11", "brand 12", "brand 13", "brand 14"));
    ArrayList<Integer> brandImages = new ArrayList<>(Arrays.asList(R.drawable.brand1, R.drawable.brand1, R.drawable.brand1, R.drawable.brand1, R.drawable.brand1, R.drawable.brand1, R.drawable.brand1,R.drawable.brand1, R.drawable.brand1, R.drawable.brand1, R.drawable.brand1, R.drawable.brand1, R.drawable.brand1, R.drawable.brand1));

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

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        // set a GridLayoutManager with 2 number of columns , horizontal gravity and false value for reverseLayout to show the items from start to end
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapter customAdapter = new CustomAdapter(getContext(), brandNames, brandImages);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView

        return root;
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
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("image", brandImages.get(position)); // put image data in Intent
                context.startActivity(intent); // start Intent
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