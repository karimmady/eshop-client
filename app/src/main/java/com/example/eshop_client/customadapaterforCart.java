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
    public List<Items> grocderyItemList;

    Cart context; //check this



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
       holder.txtProductSize.setText(grocderyItemList.get(position).getProductSize());
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
                if(QuantQ==1)
                {
                    Toast.makeText(context, "Sorry quantity cannot go under 1", Toast.LENGTH_SHORT).show();
                    return;
                }
                QuantQ=QuantQ-1;
                TextView priceplease = context.findViewById(R.id.totalprice);
                Quant.setText(String.valueOf(QuantQ));
                Double checkedPrice =Double.parseDouble(priceplease.getText().toString().replace('$',' '));
                Double productPricefinal = Double.parseDouble(grocderyItemList.get(position).getProductPrice().replace('$',' '));
                checkedPrice = checkedPrice-productPricefinal;
                priceplease.setText(String.valueOf(checkedPrice)+'$');
                grocderyItemList.get(position).setProductQty(String.valueOf(Integer.parseInt(grocderyItemList.get(position).getProductQty())-1));

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
                TextView priceplease = context.findViewById(R.id.totalprice);
                Double checkedPrice =Double.parseDouble(priceplease.getText().toString().replace('$',' '));
                Double productPricefinal = Double.parseDouble(grocderyItemList.get(position).getProductPrice().replace('$',' '));
                checkedPrice = checkedPrice+ productPricefinal;
                priceplease.setText(String.valueOf(checkedPrice)+'$');
                grocderyItemList.get(position).setProductQty(String.valueOf(Integer.parseInt(grocderyItemList.get(position).getProductQty())+1));


            }
        });


        holder.removeitemfromcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView priceplease = context.findViewById(R.id.totalprice);
                Double checkedPrice =Double.parseDouble(priceplease.getText().toString().replace('$',' '));
                Double productPricefinal = Double.parseDouble(grocderyItemList.get(position).getProductPrice().replace('$',' '));
                Double productQtyFinal= Double.parseDouble(holder.txtProductQty.getText().toString());
                checkedPrice = checkedPrice - (productPricefinal*productQtyFinal);
                priceplease.setText(String.valueOf(checkedPrice)+'$');
                grocderyItemList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, grocderyItemList.size());
                holder.itemView.setVisibility(View.GONE);
            }
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
        TextView txtProductSize;
        TextView txtProductQty;
        ImageButton removeitemfromcart;
        Button minusButtonincart;
        Button plusButtonincart;
        public GroceryProductViewHolder(View view) {
            super(view);
            imageProductImage=view.findViewById(R.id.idProductImage);
            txtProductName=view.findViewById(R.id.idProductName);
            txtProductPrice = view.findViewById(R.id.idProductPrice);
            txtProductSize = view.findViewById(R.id.idProductSize);
            txtProductQty = view.findViewById(R.id.idProductQty);
            removeitemfromcart=view.findViewById(R.id.idProductRemove);
            minusButtonincart=view.findViewById(R.id.idMinusICon);
            plusButtonincart=view.findViewById(R.id.idPlusIcon);
        }
    }

}

