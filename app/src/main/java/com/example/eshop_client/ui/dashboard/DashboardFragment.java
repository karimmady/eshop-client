package com.example.eshop_client.ui.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.eshop_client.DataHolder;
import com.example.eshop_client.Home;
import com.example.eshop_client.MainActivity;
import com.example.eshop_client.Network;
import com.example.eshop_client.PastOrders;
import com.example.eshop_client.R;
import com.example.eshop_client.SetAddress;

import java.io.InputStream;

import static android.content.Context.MODE_PRIVATE;

public class DashboardFragment extends Fragment {
    private DashboardViewModel dashboardViewModel;
    String password = new String();
    Network network = new Network();
    TextView name, points,email, addressField;
    boolean nightMode;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final Button Logout = root.findViewById(R.id.logout);
        final Button changepw = root.findViewById(R.id.changepassword);
        final Button changesize = root.findViewById(R.id.changesizes);
        final Button changeAddress = root.findViewById(R.id.changeAddress);
        final Button pastOrders = root.findViewById(R.id.viewOrders);
        final Switch night = (Switch) root.findViewById(R.id.night);
        night.setChecked(DataHolder.getInstance().getNight());
        //final Button night = root.findViewById(R.id.night);
        name = root.findViewById(R.id.nameDashboard);
        email = root.findViewById(R.id.emailDashboard);
        points = root.findViewById(R.id.pointsDashboard);
        addressField = root.findViewById(R.id.addressDashboard);
        try {
            network.execute("http://10.0.2.2:3000/getUser?email=" + DataHolder.getInstance().getEmail()).get();
            System.out.println(network.jsono);
            name.setText((String)network.jsono.get("name"));
            email.setText((String)network.jsono.get("email"));
            password = (String)network.jsono.get("password");
            new Network().execute("http://10.0.2.2:3000/getAddress?email=" + DataHolder.getInstance().getEmail()).get();
            System.out.println(network.jsono);
            addressField.setText((String)network.jsono.get("address"));
            points.setText("0");
        }catch (Exception e){
            System.out.println(e);
        }
        night.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked)
                    DataHolder.getInstance().setNight(false);
                else
                    DataHolder.getInstance().setNight(true);
                Intent i = new Intent(getContext(), Home.class);
                startActivity(i);
            }
        });
        changeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), SetAddress.class);
                startActivity(i);
            }
        });
        changesize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),SetSizes.class);
                startActivity(i);
            }
        });
        pastOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PastOrders.class);
                startActivity(i);
            }
        });
        changepw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),change_password.class);
                try {
                    System.out.println(network.jsono);
                    i.putExtra("password",password);
                }
                catch (Exception e){}
                startActivity(i);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new Network().execute("http://10.0.2.2:3000/logout?email=" + DataHolder.getInstance().getEmail()).get();
                    Intent i = new Intent(getContext(), MainActivity.class);
                    startActivity(i);
                }catch (Exception e){}
            }
        });
        return root;
    }
}