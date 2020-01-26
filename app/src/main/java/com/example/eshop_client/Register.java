package com.example.eshop_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eshop_client.ui.dashboard.SetSizes;

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
               ///////////////////////////////////////////////////////////////////////////////////////////
                if(!String.valueOf(Password.getText()).equals(String.valueOf(RePassword.getText()))
                        ||String.valueOf(firstName.getText()).isEmpty() ||String.valueOf(lastName.getText()).isEmpty()
                        ||String.valueOf(email.getText()).isEmpty()||String.valueOf(Password.getText()).isEmpty()
                        ||String.valueOf(RePassword.getText()).isEmpty())
                {
                    if(String.valueOf(firstName.getText()).isEmpty() && String.valueOf(lastName.getText()).isEmpty()
                       &&String.valueOf(email.getText()).isEmpty()&& String.valueOf(Password.getText()).isEmpty()
                       &&String.valueOf(RePassword.getText()).isEmpty())
                        Toast.makeText(Register.this,"Missing information",Toast.LENGTH_SHORT).show();


                    else if (String.valueOf(firstName.getText()).isEmpty())
                        Toast.makeText(Register.this,"Please enter your first name",Toast.LENGTH_SHORT).show();
                    else if (String.valueOf(lastName.getText()).isEmpty())
                        Toast.makeText(Register.this,"Please enter your last name",Toast.LENGTH_SHORT).show();
                    else if (String.valueOf(email.getText()).isEmpty())
                        Toast.makeText(Register.this,"Please enter your E-mail address",Toast.LENGTH_SHORT).show();
                    else if (String.valueOf(Password.getText()).isEmpty())
                        Toast.makeText(Register.this,"Please type your password",Toast.LENGTH_SHORT).show();
                    else if (String.valueOf(RePassword.getText()).isEmpty())
                        Toast.makeText(Register.this,"Please Re-Enter your password",Toast.LENGTH_SHORT).show();
                    else if (!String.valueOf(Password.getText()).equals(String.valueOf(RePassword.getText())))
                        Toast.makeText(Register.this,"Password does not match",Toast.LENGTH_SHORT).show();
                }
                else{
                    registerURL = "http://192.168.1.20:3000/register?" + "email=" + email.getText() +"&password="+Password.getText()+"&name="+String.valueOf(firstName.getText())+"%20"+String.valueOf(lastName.getText());
                    new Network().execute(registerURL);
                    DataHolder.getInstance().setEmail(String.valueOf(email.getText()));
                    Intent i= new Intent(Register.this, SetSizes.class);
                    startActivity(i);
                }

            }
        });

    }
}
