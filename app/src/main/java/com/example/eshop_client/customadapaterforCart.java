package com.example.eshop_client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class customadapaterforCart extends RecyclerView.Adapter<customadapaterforCart.GroceryProductViewHolder>{
    private List<Items> grocderyItemList;
    Context context;

    public customadapaterforCart(List<Items> mProductList, Cart mainActivity) {
        this.grocderyItemList = mProductList;
        this.context = mainActivity;
    }



    @Override
    public GroceryProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout, parent, false);
        GroceryProductViewHolder gvh = new GroceryProductViewHolder(groceryProductView);
        return gvh;
    }

    @Override
    public void onBindViewHolder(final GroceryProductViewHolder holder, final int position) {
        holder.imageProductImage.setImageResource(grocderyItemList.get(position).getProductImage());
        holder.txtProductName.setText(grocderyItemList.get(position).getProductName());
        holder.txtProductPrice.setText(grocderyItemList.get(position).getProductPrice());
//        holder.txtProductWeight.setText(grocderyItemList.get(position).getProductWeight());
        holder.txtProductQty.setText(grocderyItemList.get(position).getProductQty());

        holder.imageProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = grocderyItemList.get(position).getProductName().toString();
                Toast.makeText(context, productName + " is selected", Toast.LENGTH_SHORT).show();
            }
        });

        holder.minusButtonincart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button removeImg= holder.minusButtonincart;

                TextView Quant = holder.txtProductQty;
                int QuantQ= Integer.valueOf(Quant.getText().toString());
                QuantQ=QuantQ-1;
                Quant.setText(String.valueOf(QuantQ));
            }
        });

        holder.plusButtonincart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button removeImg= holder.plusButtonincart;

                TextView Quant = holder.txtProductQty;
                int QuantQ= Integer.valueOf(Quant.getText().toString());
                QuantQ=QuantQ+1;
                Quant.setText(String.valueOf(QuantQ));
            }
        });


        holder.removeitemfromcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                grocderyItemList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, grocderyItemList.size());
                holder.itemView.setVisibility(View.GONE);            }
        });





    }

    @Override
    public int getItemCount() {
        return grocderyItemList.size();
    }

    public class GroceryProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProductImage;
        TextView txtProductName;
        TextView txtProductPrice;
        TextView txtProductWeight;
        TextView txtProductQty;
        ImageButton removeitemfromcart;
        Button minusButtonincart;
        Button plusButtonincart;
        public GroceryProductViewHolder(View view) {
            super(view);
            imageProductImage=view.findViewById(R.id.idProductImage);
            txtProductName=view.findViewById(R.id.idProductName);
            txtProductPrice = view.findViewById(R.id.idProductPrice);
//            txtProductWeight = view.findViewById(R.id.idProductWeight);
            txtProductQty = view.findViewById(R.id.idProductQty);
            removeitemfromcart=view.findViewById(R.id.idProductRemove);
            minusButtonincart=view.findViewById(R.id.idMinusICon);
            plusButtonincart=view.findViewById(R.id.idPlusIcon);
        }
    }
}