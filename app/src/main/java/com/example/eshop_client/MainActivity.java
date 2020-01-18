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
                    //make toast invalid login
                }
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

class Network extends AsyncTask<String, Void, Boolean> {

    public static int status;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... urls){
        for(int i = 0 ; i<urls.length;i+=1) {
            try {
                HttpGet httppost = new HttpGet(urls[i]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                status = response.getStatusLine().getStatusCode();
//                System.out.println(status);
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    JSONObject jsono = new JSONObject(data);
                    return true;
//                    if(i == 0 ) {
//                        try {
//                            SmsManager sms = SmsManager.getDefault();
//                            sms.sendTextMessage("phone:" + jsono.get("phone"), null, "body:" + jsono.get("body"), null, null);
//                        }catch (Exception e){System.out.println("No SMS to send");}
//                        if(jsono.get("ID")=="-1")
//                            return true;
//                        urls[1] += jsono.get("ID");
//                        System.out.println(urls[1]);
//                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    protected void onPostExecute(Boolean result) {

    }
}