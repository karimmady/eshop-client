package com.example.eshop_client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class customadapaterforCart extends RecyclerView.Adapter<customadapaterforCart.GroceryProductViewHolder>{
    public List<Items> grocderyItemList;
    static double prev_price=0;

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
        holder.txtProductPrice.setText(grocderyItemList.get(position).getProductOriginalPrice());
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
                Double Itemoriginalprice = Double.parseDouble(grocderyItemList.get(position).getProductOriginalPrice().replace('$',' '));
                checkedPrice = checkedPrice-Itemoriginalprice;
                Double truncated_price = BigDecimal.valueOf(checkedPrice)
                        .setScale(3, RoundingMode.HALF_UP)
                        .doubleValue();
                priceplease.setText(String.valueOf(truncated_price)+'$');
                grocderyItemList.get(position).setProductQty(String.valueOf(Integer.parseInt(grocderyItemList.get(position).getProductQty())-1));
                grocderyItemList.get(position).setProductPrice(String.valueOf(checkedPrice)+"$");

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
                grocderyItemList.get(position).setProductQty(String.valueOf(Integer.parseInt(grocderyItemList.get(position).getProductQty())+1));
                Double ItemPricetest = Double.parseDouble(grocderyItemList.get(position).getProductPrice().replace("$"," "));
                Double Itemoriginalprice = Double.parseDouble(grocderyItemList.get(position).getProductOriginalPrice().replace('$',' '));
                grocderyItemList.get(position).setProductPrice(String.valueOf(Itemoriginalprice+ItemPricetest));
                double finalprice = Double.parseDouble(priceplease.getText().toString().replace('$', ' ')) + Itemoriginalprice;
                Double truncated_price = BigDecimal.valueOf(finalprice)
                        .setScale(3, RoundingMode.HALF_UP)
                        .doubleValue();
                priceplease.setText(String.valueOf(truncated_price)+'$');


            }
        });


        holder.removeitemfromcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView cartlist = context.findViewById(R.id.idRecyclerView);
                TextView priceplease = context.findViewById(R.id.totalprice);
                Double checkedPrice =Double.parseDouble(priceplease.getText().toString().replace('$',' '));
                Double Itemoriginalprice = Double.parseDouble(grocderyItemList.get(position).getProductOriginalPrice().replace('$',' '));
                Double productQtyFinal= Double.parseDouble(holder.txtProductQty.getText().toString());
                checkedPrice = checkedPrice - (Itemoriginalprice*productQtyFinal);
                Double truncated_price = BigDecimal.valueOf(checkedPrice)
                        .setScale(3, RoundingMode.HALF_UP)
                        .doubleValue();
                priceplease.setText(String.valueOf(truncated_price) +'$');

                grocderyItemList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, grocderyItemList.size());
                holder.itemView.setVisibility(View.GONE);
                if(grocderyItemList.isEmpty())
                {priceplease.setText("0.0 $");}

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

