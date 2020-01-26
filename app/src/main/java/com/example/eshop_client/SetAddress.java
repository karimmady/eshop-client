package com.example.eshop_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eshop_client.ui.dashboard.DashboardFragment;
import com.example.eshop_client.ui.dashboard.change_password;

import java.net.URLEncoder;

public class SetAddress extends AppCompatActivity {
    Button submitAddress;
    Network network = new Network();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_address);
        setTitle("Set Address");
        submitAddress = findViewById(R.id.submitAddress);
        submitAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView address = findViewById(R.id.Address);
                try {
                    network.execute("http://192.168.1.20:3000/setAddress?email=" + DataHolder.getInstance().getEmail() + "&address=" + URLEncoder.encode(String.valueOf(address.getText()),"UTF-8")).get();
                    System.out.println(network.status);
                    if (network.status == 200){
                        Intent i = new Intent(SetAddress.this, Home.class);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(SetAddress.this,"Please try again",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    System.out.println(e);
                    Toast.makeText(SetAddress.this,"Please try again",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
