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
import android.widget.Toast;

import com.example.eshop_client.Cart;
import com.example.eshop_client.DataHolder;
import com.example.eshop_client.Network;
import com.example.eshop_client.R;

import java.util.ArrayList;
import com.example.eshop_client.NetworkPost;
import com.example.eshop_client.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlaceOrder extends AppCompatActivity {
    ArrayAdapter<String> CatAdapt3;
    Network network = new Network();
    ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
    String userAddress = new String();
    ArrayList<String> ItemPrice=new ArrayList<String>();
    ArrayList<String> ItemQTY=new ArrayList<String>();
    ArrayList<String> ItemName=new ArrayList<String>();
    ArrayList<Integer> ItemImage=new ArrayList<Integer>();
    ArrayList<String> ItemSize=new ArrayList<>();
    ArrayList<String> ItemID = new ArrayList<>();
    Double finalamount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        final ListView list1 = findViewById(R.id.list1);
        Bundle Extras=getIntent().getExtras();
        ItemPrice=new ArrayList<String>();
        ItemQTY=new ArrayList<String>();
        ItemName=new ArrayList<String>();
        ItemImage=new ArrayList<Integer>();
        ItemSize=new ArrayList<>();
        ItemID = new ArrayList<>();




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
        TOTALAMOUNT.setText("Total Amount "+String.valueOf(ALfinal.addprices())+" $");

        final Spinner s = (Spinner) findViewById(R.id.spinner);
         ArrayList<String> paymentmethod= new ArrayList<>();
         paymentmethod.add("Cash on delivery");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,paymentmethod);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        TextView address = findViewById(R.id.add);
        try{
        network.execute("http://10.0.2.2:3000/getAddress?email=" + DataHolder.getInstance().getEmail()).get();
        userAddress = (String)network.jsono.get("address");
        address.setText(userAddress);
        }catch (Exception e){
            System.out.println(e);
        }
        Button placeYourOrder = findViewById(R.id.placeYourOrder);
        placeYourOrder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    JSONObject orderInfo = new JSONObject();
                    orderInfo.put("Email", DataHolder.getInstance().getEmail());
                    orderInfo.put("address", userAddress);
                    orderInfo.put("status", "pending");
                    orderInfo.put("total", String.valueOf(finalamount)+"$");
                    JSONArray orderItems = new JSONArray();
                    for (int i = 0; i < ItemID.size(); i += 1) {
                        JSONObject eachItem = new JSONObject();
                        eachItem.put("ID", ItemID.get(i));
                        eachItem.put("name",ItemName.get(i));
                        eachItem.put("price",ItemPrice.get(i));
                        eachItem.put("size",ItemSize.get(i));
                        eachItem.put("QTY",ItemQTY.get(i));
                        orderItems.put(eachItem);
                    }
                    orderInfo.put("items",orderItems);
                    data.add(new BasicNameValuePair("data",orderInfo.toString()));
                    DataHolder.getInstance().setPostInfo(data);
                    new NetworkPost().execute("http://10.0.2.2:3000/putOrder").get();
                    if(network.status == 200) {
                        Toast.makeText(PlaceOrder.this, "Your order has been placed", Toast.LENGTH_LONG).show();
                        //TODO:Empty cart and return to home
                    }
                }catch (Exception e){
                    System.out.println(e);
                }
            }});


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

    public double addprices()
    {
        double finalprice=0;
        for (int i =0 ; i<price.size(); i++)
        {
            finalprice += Double.parseDouble(this.price.get(i).replace('$',' '));
        }
        return finalprice;
    }

}
