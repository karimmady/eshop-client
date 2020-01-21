package com.example.eshop_client;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

public class MainActivity extends AppCompatActivity {
    Button loginButton;
    String loginURL ;
    Button RegisterButton;
    Network network = new Network();
    boolean status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");
        loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                TextView email = findViewById(R.id.email);
                TextView password = findViewById(R.id.password);
                DataHolder.getInstance().setEmail(String.valueOf(email.getText()));
                loginURL = "http://10.0.2.2:3000/login?" + "email=" + email.getText() +"&password="+password.getText();
                System.out.println(loginURL);
                    try {
                        boolean status = new Network().execute(loginURL).get();
                    }catch (Exception e){

                    }
                    if (network.status==200){
                    Intent i = new Intent(MainActivity.this,Home.class);
                    startActivity(i);
                }
                else {

                    Toast.makeText(MainActivity.this,"ERROR",Toast.LENGTH_SHORT).show();
                }
            }
        });
        RegisterButton=  findViewById(R.id.register);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent F= new Intent(MainActivity.this,Register.class);
                startActivity(F);

            }
        });
    }

}
