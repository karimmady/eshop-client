package com.example.eshop_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    Button Register;
    String registerURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");

        Register=  findViewById(R.id.register2);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email = findViewById(R.id.email2);
                EditText firstName = findViewById(R.id.FirstName);
                EditText lastName = findViewById(R.id.LastName);
                EditText Password = findViewById(R.id.Password);
                EditText RePassword =findViewById(R.id.ReEnter);
               // System.out.println(String.valueOf(firstName.getText()).equals(null));
                if(!String.valueOf(Password.getText()).equals(String.valueOf(RePassword.getText())))
                {

                    Toast.makeText(Register.this,"Password does not match",Toast.LENGTH_SHORT).show();
                }
                else{
                    registerURL = "http://10.0.2.2:3000/register?" + "email=" + email.getText() +"&password="+Password.getText()+"&name="+String.valueOf(firstName.getText())+"%20"+String.valueOf(lastName.getText());
                    new Network().execute(registerURL);
                    Intent i= new Intent(Register.this,MainActivity.class);
                    startActivity(i);
                }

            }
        });

    }
}
