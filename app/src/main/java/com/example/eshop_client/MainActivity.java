package com.example.eshop_client;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    SharedPreferences preferences;
    boolean status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");
        preferences = getSharedPreferences("myprefs", MODE_PRIVATE);
        String token = preferences.getString("token","");
        String storedEmail = preferences.getString("email","");
        System.out.println(token);
        try {
            network.execute("http://192.168.1.9:3000/checkToken?" + "email=" + storedEmail + "&token=" + token).get();
            if(network.status==200)
            {
                DataHolder.getInstance().setEmail(storedEmail);
                Intent i = new Intent(MainActivity.this, Home.class);
                startActivity(i);
            }
            else{
                preferences.edit().remove("token");
                preferences.edit().remove("email");
            }
        }catch (Exception e){

        }
        loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                TextView email = findViewById(R.id.email);
                TextView password = findViewById(R.id.password);
                DataHolder.getInstance().setEmail(String.valueOf(email.getText()));
                loginURL = "http://192.168.1.9:3000/login?" + "email=" + email.getText() +"&password="+password.getText();
                System.out.println(loginURL);
                    try {
                        boolean status = new Network().execute(loginURL).get();
                    }catch (Exception e){

                    }
                    if (network.status==200){
                        try {
                            preferences.edit().putString("token", network.jsono.getString("token")).commit();
                            preferences.edit().putString("email", String.valueOf(email.getText())).commit();
                            Intent i = new Intent(MainActivity.this, Home.class);
                            startActivity(i);
                        }catch (Exception e){
                            Toast.makeText(MainActivity.this,"ERROR",Toast.LENGTH_SHORT).show();
                        }
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
