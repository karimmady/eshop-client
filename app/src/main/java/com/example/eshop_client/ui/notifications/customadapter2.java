package com.example.eshop_client.ui.notifications;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eshop_client.R;
import com.example.eshop_client.ui.notifications.NotificationsFragment;

import java.util.ArrayList;

class CustomAdaptertwo extends RecyclerView.Adapter<com.example.eshop_client.ui.notifications.CustomAdaptertwo.MyViewHolder> {

    ArrayList<String> brandNames;
    ArrayList<Integer> brandImages;
    Context context;

    public CustomAdaptertwo(Context context, ArrayList<String> brandNames, ArrayList<Integer> brandImages) {
        this.context = context;

        this.brandNames = brandNames;
        this.brandImages = brandImages;
    }

    @Override
    public com.example.eshop_client.ui.notifications.CustomAdaptertwo.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        com.example.eshop_client.ui.notifications.CustomAdaptertwo.MyViewHolder vh = new com.example.eshop_client.ui.notifications.CustomAdaptertwo.MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(com.example.eshop_client.ui.notifications.CustomAdaptertwo.MyViewHolder holder, final int position) {
        // set the data in items
        holder.name.setText(brandNames.get(position));
        holder.image.setImageResource(brandImages.get(position));
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open another activity on item click
                Intent intent = new Intent(context, NotificationsFragment.class);
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
