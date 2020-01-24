package com.example.eshop_client;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import static java.lang.Math.round;

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
        new DecimalFormat("##.##").format(finalamount);
        totalprice.setText(String.valueOf(finalamount)+ "$");

    }
}


class Items {
    public int productImage;
    public String productName;
    public String productPrice;
    public String productSize;
    public String productQty;

    public Items(String productName, int productImage, String productPrice, String productSize, String productQty) {
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productSize = productSize;
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
}




