package com.example.eshop_client.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.eshop_client.DataHolder;
import com.example.eshop_client.Home;
import com.example.eshop_client.MainActivity;
import com.example.eshop_client.NetworkPost;
import com.example.eshop_client.R;
import com.example.eshop_client.ui.home.Brands;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class SetSizes extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton size;
    Button submit;
    String chosenSize;
    String pantsSize;
    String height;
    String shoeSize;
    List<NameValuePair> data = new ArrayList<NameValuePair>();
    NetworkPost networkPost = new NetworkPost();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_sizes);
        radioGroup = findViewById(R.id.sizeRadioGroup);
        submit = findViewById(R.id.submitSize);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean serverError = false;
                try {
                    data.clear();
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    size = findViewById(selectedId);
                    chosenSize = (String) size.getText();
                    data.add(new BasicNameValuePair("Email", DataHolder.getInstance().getEmail()));
                    data.add(new BasicNameValuePair("Tshirt",chosenSize));
                    EditText temp = findViewById(R.id.pantsText);
                    pantsSize = String.valueOf(temp.getText());
                    data.add(new BasicNameValuePair("pantsSize",pantsSize));
                    temp = findViewById(R.id.shoeSizeText);
                    shoeSize = String.valueOf(temp.getText());
                    data.add(new BasicNameValuePair("shoeSize",shoeSize));
                    temp = findViewById(R.id.heightText);
                    height = String.valueOf(temp.getText());
                    data.add(new BasicNameValuePair("height",height));
                    data.add(new BasicNameValuePair("points","0"));
                    DataHolder.getInstance().setPostInfo(data);
                    networkPost.execute("http://192.168.1.11:3000/setSize").get();
                    if(networkPost.status==200) {
                        Intent i= new Intent(SetSizes.this, Home.class);
                        SetSizes.this.startActivity(i);
                    }
                    else {
                        Toast.makeText(SetSizes.this,
                                "please try again", Toast.LENGTH_SHORT).show();
                        serverError = true;
                    }

                }catch (Exception e){
                    System.out.println(e);
                    if(!serverError)
                        Toast.makeText(SetSizes.this,
                                "please fill in all info", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
