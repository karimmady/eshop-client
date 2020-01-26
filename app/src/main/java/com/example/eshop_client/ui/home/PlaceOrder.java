package com.example.eshop_client.ui.home;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.example.eshop_client.Cart;
import com.example.eshop_client.DataHolder;
import com.example.eshop_client.Home;
import com.example.eshop_client.Network;
import com.example.eshop_client.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import com.example.eshop_client.NetworkPost;
import com.example.eshop_client.R;
import com.example.eshop_client.SetAddress;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlaceOrder extends AppCompatActivity {
    ArrayAdapter<String> CatAdapt3;
    Network network = new Network();
    ArrayList<NameValuePair> data = new ArrayList<>();
    String userAddress = new String();
    ArrayList<String> ItemPrice=new ArrayList<>();
    ArrayList<String> ItemQTY=new ArrayList<>();
    ArrayList<String> ItemName=new ArrayList<>();
    ArrayList<Integer> ItemImage=new ArrayList<>();
    ArrayList<String> ItemSize=new ArrayList<>();
    ArrayList<String> ItemID = new ArrayList<>();
    Double finalamount;

    NetworkPost networkPost = new NetworkPost();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        final ListView list1 = findViewById(R.id.list1);
        final Button ChangeAddress = findViewById(R.id.change);
        Bundle Extras=getIntent().getExtras();

        final Cart c=new Cart();


        ItemName=Extras.getStringArrayList("itemsname");
        ItemQTY=Extras.getStringArrayList("itemQtn");
        ItemPrice=Extras.getStringArrayList("itemsprice");
        ItemImage=Extras.getIntegerArrayList("itemimage");
        ItemSize =Extras.getStringArrayList("itemsize");
        finalamount=Extras.getDouble("finalam");
        ItemID = Extras.getStringArrayList("IDs");
        final placeord ALfinal=new placeord(ItemID,ItemName,ItemQTY,ItemPrice,ItemSize);



            ALfinal.addarrays();
            CatAdapt3 = new ArrayAdapter<String>(PlaceOrder.this, android.R.layout.simple_list_item_1, ALfinal.returnfinalArray());
            list1.setAdapter(CatAdapt3);

        Button Manageitems= findViewById(R.id.mngitems);

        ChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlaceOrder.this, SetAddress.class);
                startActivity(i);
            }
        });
        Manageitems.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
            Intent i = new Intent(PlaceOrder.this,Cart.class);
                startActivity(i);
                finish();
            }});

        TextView TOTALAMOUNT=findViewById(R.id.totamount);
        Double truncated_price = BigDecimal.valueOf(ALfinal.addprices())
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
        TOTALAMOUNT.setText("Total Amount "+String.valueOf(truncated_price+" $"));


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
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                try {
                    JSONObject orderInfo = new JSONObject();
                    orderInfo.put("Email", DataHolder.getInstance().getEmail());
                    orderInfo.put("address", userAddress);
                    orderInfo.put("status", "pending");
                    orderInfo.put("total", String.valueOf(ALfinal.addprices())+"$");
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

                    networkPost.execute("http://10.0.2.2:3000/putOrder").get();

                    System.out.println(networkPost.status);
                    if(networkPost.status == 200) {

                        Toast.makeText(PlaceOrder.this, "Your order has been placed", Toast.LENGTH_LONG).show();
                        c.cartuse.truncateList();
                        Intent i = new Intent(PlaceOrder.this, Home.class);
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(PlaceOrder.this,"Please enter your address",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    System.out.println(e);
                }

            }});
            final EditText promocodetext = findViewById(R.id.promocodetextd);
            ImageButton promocode = findViewById(R.id.promocodes);
            final TextView total=findViewById(R.id.totamount);

            ArrayList<String> validPromocodes=new ArrayList<>();
            validPromocodes.add("eShop10");
            validPromocodes.add("eShop15");
            validPromocodes.add("eShop25");
        promocode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (String.valueOf(promocodetext.getText()).isEmpty())
                        Toast.makeText(PlaceOrder.this,"Empty Promo Code",Toast.LENGTH_SHORT).show();
                    else if (String.valueOf(promocodetext.getText()).equals("eShop10"))
                    {
                        Toast.makeText(PlaceOrder.this,"Applied eShop10",Toast.LENGTH_SHORT).show();
                        promocodetext.setText(" ");
                        double x=Double.parseDouble(total.getText().toString().replace('$',' ').replace("Total Amount"," "));
                        x=x*0.9;
                        Double truncated_price = BigDecimal.valueOf(x)
                                .setScale(3, RoundingMode.HALF_UP)
                                .doubleValue();
                        total.setText("After Dicount "+String.valueOf(truncated_price)+" $");

                    }
                    else if (String.valueOf(promocodetext.getText()).equals("eShop15"))
                    {
                        Toast.makeText(PlaceOrder.this,"Applied eShop15",Toast.LENGTH_SHORT).show();
                        promocodetext.setText(" ");
                        double x=Double.parseDouble(total.getText().toString().replace('$',' ').replace("Total Amount"," "));
                        x=x*0.85;
                        Double truncated_price = BigDecimal.valueOf(x)
                                .setScale(3, RoundingMode.HALF_UP)
                                .doubleValue();
                        total.setText("After Dicount "+String.valueOf(truncated_price)+" $");

                    }
                    else if (String.valueOf(promocodetext.getText()).equals("eShop25"))
                    {
                        Toast.makeText(PlaceOrder.this,"Applied eShop25",Toast.LENGTH_SHORT).show();
                        promocodetext.setText(" ");
                        double x=Double.parseDouble(total.getText().toString().replace('$',' ').replace("Total Amount"," "));
                        x=x*0.75;
                        Double truncated_price = BigDecimal.valueOf(x)
                                .setScale(3, RoundingMode.HALF_UP)
                                .doubleValue();
                        total.setText("After Dicount "+String.valueOf(truncated_price)+" $");

                    }
                    else
                        Toast.makeText(PlaceOrder.this,"Invalid Code",Toast.LENGTH_SHORT).show();




                }
            });

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
