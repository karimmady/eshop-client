package com.example.eshop_client.ui.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eshop_client.DataHolder;
import com.example.eshop_client.Network;
import com.example.eshop_client.R;

public class change_password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Button submit= findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText password= findViewById(R.id.oldPassword);
                EditText newpassword= findViewById(R.id.newpassword);
                EditText repassword= findViewById(R.id.Reenter);
                System.out.println(getIntent().getStringExtra("password"));
                if(!String.valueOf(newpassword.getText()).equals(String.valueOf(repassword.getText()))
                        ||String.valueOf(password.getText()).isEmpty() ||String.valueOf(newpassword.getText()).isEmpty()
                        ||String.valueOf(repassword.getText()).isEmpty()||!String.valueOf(password.getText()).equals(getIntent().getStringExtra("password")))
                {
                    if(String.valueOf(newpassword.getText()).isEmpty() && String.valueOf(newpassword.getText()).isEmpty()
                            &&String.valueOf(repassword.getText()).isEmpty())
                    Toast.makeText(change_password.this,"Missing information",Toast.LENGTH_SHORT).show();


                else if (String.valueOf(password.getText()).isEmpty())
                    Toast.makeText(change_password.this,"Please enter your password",Toast.LENGTH_SHORT).show();
                else if (String.valueOf(newpassword.getText()).isEmpty())
                    Toast.makeText(change_password.this,"Please enter your new password",Toast.LENGTH_SHORT).show();
                else if (String.valueOf(repassword.getText()).isEmpty())
                    Toast.makeText(change_password.this,"Please enter your Re-enter password",Toast.LENGTH_SHORT).show();
                else if (!String.valueOf(newpassword.getText()).equals(String.valueOf(repassword.getText())))
                    Toast.makeText(change_password.this,"Password does not match",Toast.LENGTH_SHORT).show();
                else if(!String.valueOf(password.getText()).equals(getIntent().getStringExtra("password")))
                        Toast.makeText(change_password.this,"Wrong Password",Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        new Network().execute("http://10.0.2.2:3000/changePassword?" + "email=" + DataHolder.getInstance().getEmail() + "&newPassword=" + newpassword.getText()).get();
                    }catch (Exception e){}
                    Toast.makeText(change_password.this,"Password Changed",Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}
