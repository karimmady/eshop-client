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
                loginURL = "http://192.168.1.41:3000/login?" + "email=" + email.getText() +"&password="+password.getText();
                System.out.println(loginURL);
//                try {
//                    boolean status = new Network().execute(loginURL).get();
//                }catch (Exception e){
//
//                }
//                if (network.status==200){
                    Intent i = new Intent(MainActivity.this,Home.class);
                    startActivity(i);
//                }
//                else {
//
//                    Toast.makeText(MainActivity.this,"ERROR",Toast.LENGTH_SHORT).show();
//                }
            }
        });
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);

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
