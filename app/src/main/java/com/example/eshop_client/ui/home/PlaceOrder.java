package com.example.eshop_client.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.eshop_client.Cart;
import com.example.eshop_client.DataHolder;
import com.example.eshop_client.Network;
import com.example.eshop_client.R;

import java.util.ArrayList;

public class PlaceOrder extends AppCompatActivity {
    ArrayAdapter<String> CatAdapt3;
    Network network = new Network();
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
        ArrayList<String> ItemSize=new ArrayList<>();
        ArrayList<String> ItemID = new ArrayList<>();

        Double finalamount;

        ItemName=Extras.getStringArrayList("itemsname");
        ItemQTY=Extras.getStringArrayList("itemQtn");
        ItemPrice=Extras.getStringArrayList("itemsprice");
        ItemImage=Extras.getIntegerArrayList("itemimage");
        ItemSize =Extras.getStringArrayList("itemsize");
        finalamount=Extras.getDouble("finalam");
        ItemID = Extras.getStringArrayList("IDs");
        placeord ALfinal=new placeord(ItemID,ItemName,ItemQTY,ItemPrice,ItemSize);



            ALfinal.addarrays();
            CatAdapt3 = new ArrayAdapter<String>(PlaceOrder.this, android.R.layout.simple_list_item_1, ALfinal.returnfinalArray());
            list1.setAdapter(CatAdapt3);

        Button Manageitems= findViewById(R.id.mngitems);
        Manageitems.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
            Intent i = new Intent(PlaceOrder.this,Cart.class);
                startActivity(i);
                finish();
            }});

        TextView TOTALAMOUNT=findViewById(R.id.totamount);
        TOTALAMOUNT.setText("Total Amount "+String.valueOf(finalamount)+" $");

        final Spinner s = (Spinner) findViewById(R.id.spinner);
         ArrayList<String> paymentmethod= new ArrayList<>();
         paymentmethod.add("Cash on delivery");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,paymentmethod);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        TextView address = findViewById(R.id.add);
        try{
        network.execute("http://10.0.2.2:3000/getAddress?email=" + DataHolder.getInstance().getEmail()).get();
        address.setText((String)network.jsono.get("address"));
        }catch (Exception e){
            System.out.println(e);
        }



    }
}
class placeord{
    ArrayList<String> id;
    ArrayList<String> name;
    ArrayList<String> qty;
    ArrayList<String> price;
    ArrayList<String> size;
    ArrayList<String> finallist=new ArrayList<>();


    placeord(ArrayList<String> id,ArrayList<String> name,ArrayList<String> qty, ArrayList<String> price,ArrayList<String> size)
    {
        this.id = id;
        this.name = name;
        this.qty=qty;
        this.price=price;
        this.size=size;
    }

    public void addarrays()
    {
        for(int i=0;i<qty.size();i++)
        {
            finallist.add(i,id.get(i)+"         "+name.get(i)+"   "+qty.get(i)+"    "+price.get(i)+"     Size : "+size.get(i));
        }

    }

    public ArrayList<String> returnfinalArray()
    {
        return finallist;

    }


}
