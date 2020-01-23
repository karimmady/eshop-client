package com.example.eshop_client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class Cart extends AppCompatActivity {
    public cartUse cartuse=new cartUse();
    private RecyclerView mRecyclerView;
    private customadapaterforCart mAdapter;
    private List<Items> mProductList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //getting the recyclerview from xml
        mRecyclerView = (RecyclerView) findViewById(R.id.idRecyclerView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
       //cartuse.addtocart("Black Pants",R.drawable.blackpants, "150 EGP", "remove", "1");
        //artuse.addtocart("White shirt",R.drawable.whiteshirt,"Rs. 250", "remove", "1");c
        //Populate the products
        mProductList=cartuse.getList();


        //set adapter to recyclerview
        mAdapter = new customadapaterforCart(mProductList,Cart.this);
        mRecyclerView.setAdapter(mAdapter);

    }
}


class Items {
    public int productImage;
    public String productName;
    public String productPrice;
    public String productWeight;
    public String productQty;

    public Items(String productName, int productImage, String productPrice, String productWeight, String productQty) {
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productWeight = productWeight;
        this.productQty = productQty;
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

    public String getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(String productWeight) {
        this.productWeight = productWeight;
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
}




