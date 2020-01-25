package com.example.eshop_client.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.eshop_client.R;

import java.util.ArrayList;

public class PlaceOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        final ListView list1 = findViewById(R.id.list1);
        Bundle Extras=getIntent().getExtras();
        ArrayList<String> ItemPrice=new ArrayList<String>();
        ArrayList<String> ItemQTY=new ArrayList<String>();
        ArrayList<String> ItemName=new ArrayList<String>();
        ArrayList<Integer> ItemImage=new ArrayList<Integer>();

        ItemName=Extras.getStringArrayList("itemsname");
        ItemQTY=Extras.getStringArrayList("itemQtn");
        ItemPrice=Extras.getStringArrayList("itemsprice");
        ItemImage=Extras.getIntegerArrayList("itemimage");
        placeord ALfinal=new placeord(ItemImage,ItemName,ItemQTY,ItemPrice);


        ArrayAdapter<String> CatAdapt3 = new ArrayAdapter<String>(PlaceOrder.this, android.R.layout.simple_list_item_1,ALfinal.addarrays());
        list1.setAdapter(CatAdapt3);


    }
}

class placeord{
    ArrayList<Integer> img;
    ArrayList<String> name;
    ArrayList<String> qty;
    ArrayList<String> price;
    ArrayList<String> finallist=new ArrayList<String>();


    placeord(ArrayList<Integer> img,ArrayList<String> name,ArrayList<String> qty, ArrayList<String> price)
    {
        this.img = img;
        this.name = name;
        this.qty=qty;
        this.price=price;
    }

    public ArrayList<String> addarrays()
    {
        for(int i=0;i<qty.size();i++)
        {
            finallist.add(i,img.get(i)+"         "+name.get(i)+"         "+qty.get(i)+"        "+price.get(i));
        }
        return finallist;
    }


}
