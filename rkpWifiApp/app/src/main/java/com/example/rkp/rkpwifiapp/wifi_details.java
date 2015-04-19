package com.example.rkp.rkpwifiapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by RKP on 21-03-2015.
 */
public class wifi_details extends Activity {

    Button button;
    WifiManager wifiManager;
    ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // applying the respective layout: activity_wifi_details
        setContentView(R.layout.activity_wifi_details);
        // adding listener for refresh button
        addListenerOnButton();
        // function call for acquiring the details for connected wifi network
        getDetails();
    }

    void getDetails(){

        TextView tv;

        wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        imageView = (ImageView) findViewById(R.id.ImageView1);

        if(wifiManager.getConnectionInfo() != null) {

            //Log.d("wifi",wifiManager.getConnectionInfo().toString());

            // Referencing appropriate view and displaying BSSID of connected wifi network
            tv = (TextView) findViewById(R.id.bssid);
            tv.setText(wifiManager.getConnectionInfo().getBSSID());

            // Referencing appropriate view and displaying SSID of connected wifi network
            tv = (TextView) findViewById(R.id.ssid);
            tv.setText(wifiManager.getConnectionInfo().getSSID());

            // Referencing appropriate view and displaying RSSI value of connected wifi network
            tv = (TextView) findViewById(R.id.rssi);
            int wifi_rssi = wifiManager.getConnectionInfo().getRssi();
            tv.setText(Integer.toString(wifi_rssi) + " dBm");

            // Referencing appropriate view and displaying link speed of connected wifi network
            tv = (TextView) findViewById(R.id.speed);
            tv.setText(Integer.toString(wifiManager.getConnectionInfo().getLinkSpeed()) + " mbps");

            // Referencing appropriate view and displaying MAC address of connected wifi network
            tv = (TextView) findViewById((R.id.MAC));
            tv.setText(wifiManager.getConnectionInfo().getMacAddress());

            // Referencing appropriate view for displaying the quality of connected wifi network
            tv = (TextView) findViewById(R.id.quality);

            /*
                IF rssi value if less than or equal to -100, quality of network is interpreted as zero.
                And greater than or equal to -50 is interpreted as 100% quality.
                So to calculate intermediate quality, we add 100 to RSSI value and twice the result
             */
            int quality;
            if(wifi_rssi <= -100)
                quality = 0;
            else if(wifi_rssi >= -50)
                quality = 100;
            else
                quality = 2 * (wifi_rssi + 100);

            /*
                Display appropriate wifi signal images based on quality of connected networks
                Red signal with single wave, when quality <= 40
                Yellow signal with two waves, when quality > 40 and quality <= 75
                Green signal with all three waves, when quality > 75
             */
            if(quality <= 40)
                imageView.setImageResource(R.drawable.wifi3);
            else if(quality <= 75)
                imageView.setImageResource(R.drawable.wifi2);
            else
                imageView.setImageResource(R.drawable.wifi1);

            // displaying the quality of connected wifi network
            tv.setText(Integer.toString(quality) + "%");

        }
    }

    public void addListenerOnButton() {
        final Context context = this;
        // Referencing appropriate button: Refresh button
        button = (Button) findViewById(R.id.button3);
        // setting the function to be called on refresh button click
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                getDetails();
            }
        });
    }

}

