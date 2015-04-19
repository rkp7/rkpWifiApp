package com.example.rkp.rkpwifiapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {

    // We require two button objects
    Button connectedNetworkButton,networkListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting appropriate layout: activity_main
        setContentView(R.layout.activity_main);
        // adding listeners for buttons in the activity
        addListenerOnButton();
    }

    // function is called on pressing any button
    public void addListenerOnButton() {

        final Context context = this;

        // Reference the Connected Network button
        connectedNetworkButton = (Button) findViewById(R.id.button1);
        // function which is executed on pressing the Connected Network button
        connectedNetworkButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get object of ConnectivityManager class
                ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                // Reference object of NetworkInfo for Wifi Network
                NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                // Check if device is connected to a wifi network
                if (mWifi.isConnected()) {
                    // Start the activity to display details of connected wifi network
                    Intent intent = new Intent(context, wifi_details.class);
                    startActivity(intent);
                }
                else{
                    // Start the activity to display error message that device is not connected to any wifi network
                    Intent intent = new Intent(context, No_network.class);
                    startActivity(intent);
                }
            }
        });

        // Reference the Network List button
        networkListButton = (Button) findViewById(R.id.button4);
        // function which is executed on pressing the Network List button
        networkListButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get object of WifiManager class
                WifiManager wifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
                // check is Wifi is enabled
                if (wifi.isWifiEnabled()){
                    // start activity for displaying network details of all wifi networks in range
                    Intent intent = new Intent(context, Network_List.class);
                    startActivity(intent);
                }
                else{
                    // display a toast for enabling wifi
                    Toast.makeText(getApplicationContext(), "Wifi is Disabled. Enabling Wifi!", Toast.LENGTH_LONG).show();
                    // enabling the wifi setting
                    wifi.setWifiEnabled(true);
                    // start activity for displaying details of wifi networks in range
                    Intent intent = new Intent(context, Network_List.class);
                    startActivity(intent);
                }
            }
        });
    }
}
