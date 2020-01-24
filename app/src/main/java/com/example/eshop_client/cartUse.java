package com.example.eshop_client;

import java.util.ArrayList;
import java.util.List;

public class cartUse {
    public static List<Items> mProductList= new ArrayList<Items>();

    public void addtocart(String name, int photo,String price,String Size,String Quantity)
    {
        Items c = new Items(name,photo,price,Size,Quantity);
        mProductList.add(c);
    }


    public List<Items> getList()
    {
        return mProductList;
    }
}