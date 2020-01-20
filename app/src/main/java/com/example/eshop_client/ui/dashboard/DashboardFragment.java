package com.example.eshop_client.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.eshop_client.DataHolder;
import com.example.eshop_client.MainActivity;
import com.example.eshop_client.Network;
import com.example.eshop_client.R;

import java.io.InputStream;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    Network network = new Network();
    TextView name, points,email;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final Button Logout = root.findViewById(R.id.logout);
        final Button changepw = root.findViewById(R.id.changepassword);
        final Button changesize = root.findViewById(R.id.changesizes);
        name = root.findViewById(R.id.nameDashboard);
        email = root.findViewById(R.id.emailDashboard);
        points = root.findViewById(R.id.pointsDashboard);
        try {
            network.execute("http://10.0.2.2:3000/getUser?email=" + DataHolder.getInstance().getEmail()).get();
            System.out.println(network.jsono);
            name.setText((String)network.jsono.get("name"));
            email.setText((String)network.jsono.get("email"));
            points.setText("0");
        }catch (Exception e){

        }
        changesize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),SetSizes.class);
                startActivity(i);
            }
        });
        changepw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),change_password.class);
                try {
                    i.putExtra("password",(String) network.jsono.get("password"));
                }
                catch (Exception e){}
                startActivity(i);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),MainActivity.class);
                startActivity(i);
            }
        });
        return root;
    }
}