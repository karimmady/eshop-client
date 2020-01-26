package com.example.eshop_client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.eshop_client.ui.home.PlaceOrder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.round;

public class Cart extends AppCompatActivity {
    public cartUse cartuse=new cartUse();
    private RecyclerView mRecyclerView;
    public customadapaterforCart mAdapter;
    private List<Items> mProductList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setTitle("Cart");
        //getting the recyclerview from xml
        mRecyclerView = (RecyclerView) findViewById(R.id.idRecyclerView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Populate the products
        mProductList=cartuse.getList();


        //set adapter to recyclerview
        mAdapter = new customadapaterforCart(mProductList,Cart.this);
        mRecyclerView.setAdapter(mAdapter);
        TextView totalprice= findViewById(R.id.totalprice);
        String amount;
        double finalamount=0;
        for(int i=0; i<mProductList.size();i++)
        {
            amount=mProductList.get(i).productPrice.replace('$',' ');
            finalamount+=Double.valueOf(amount);
        }

        Double truncated_price = BigDecimal.valueOf(finalamount)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
        totalprice.setText(String.valueOf(truncated_price)+ "$");

        Button placeorder= findViewById(R.id.placeorder);
        final ArrayList<String> ITEMNAME = new ArrayList<>();
        final ArrayList<String> ITEMQuan = new ArrayList<>();
        final ArrayList<String> ITEMPRICE = new ArrayList<>();
        final ArrayList<Integer> ITEMIMAGE = new ArrayList<>();
        final ArrayList<String> ITEMSIZE = new ArrayList<>();
        final ArrayList<String> ITEMID =new ArrayList<>();

        placeorder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if (mProductList.size() == 0) {
                    Toast.makeText(Cart.this, "Empty Cart", Toast.LENGTH_SHORT).show();
                } else {
                    for (int j = 0; j < mAdapter.grocderyItemList.size(); j++) {
                        ITEMIMAGE.add(mAdapter.grocderyItemList.get(j).productImage);
                        ITEMNAME.add(mAdapter.grocderyItemList.get(j).productName);
                        ITEMQuan.add(mAdapter.grocderyItemList.get(j).productQty);
                        ITEMPRICE.add(mAdapter.grocderyItemList.get(j).productPrice);
                        ITEMSIZE.add(mAdapter.grocderyItemList.get(j).productSize);
                        ITEMID.add(mAdapter.grocderyItemList.get(j).productID);

                    }


                    Intent i = new Intent(Cart.this, PlaceOrder.class);
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("itemsname", ITEMNAME);
                    bundle.putStringArrayList("itemsprice", ITEMPRICE);
                    bundle.putStringArrayList("itemQtn", ITEMQuan);
                    bundle.putIntegerArrayList("itemimage", ITEMIMAGE);
                    bundle.putStringArrayList("itemsize", ITEMSIZE);
                    bundle.putStringArrayList("IDs", ITEMID);

                    i.putExtras(bundle);
                    startActivity(i);
                    finish();
                }
            }
        });
    }





}


class Items {
    public int productImage;
    public String productName;
    public String productPrice;
    public String productSize;
    public String productQty;
    public String productID;
    public String productOriginalPrice;

    public Items(String productName, int productImage, String productPrice, String productSize, String productQty,String productID,String ProductOriginalPrice) {

        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productSize = productSize;
        this.productQty = productQty;
        this.productID=productID;
        this.productOriginalPrice=ProductOriginalPrice;

    }

    public String getProductQty() {
        return productQty;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductWeight(String productWeight) {
        this.productSize = productWeight;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductID(String productID)
    {
        this.productID=productID;
    }

    public String getProductID()
    {
        return productID;
    }

    public String getProductOriginalPrice()
    {
        return productOriginalPrice;
    }

}




