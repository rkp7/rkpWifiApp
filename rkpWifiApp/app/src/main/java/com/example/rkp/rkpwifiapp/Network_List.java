package com.example.rkp.rkpwifiapp;

import android.app.Activity;
import android.bluetooth.le.ScanRecord;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by RKP on 23-03-2015.
 */
public class Network_List extends Activity {

    WifiManager wifiManager;
    String networkDetails = "";
    TextView NetworkList;
    WifiScanner wifiReciever;
    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting appropriate layout: activity_network_list
        setContentView(R.layout.activity_network_list);
        // Referencing the text view for displaying wifi network details in range
        NetworkList = (TextView) findViewById(R.id.networkText);
        // Acquiring object of WifiManager class
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        // Acquiring object of WifiScanner class
        wifiReciever = new WifiScanner();
        // function call for scanning the wifi networks in range of the device
        wifiManager.startScan();
        // initializing the text view
        NetworkList.setText("");
        // adding listeners for buttons in the activity
        addListenerOnButton();
    }

    protected void onPause() {
        // Unregister the wifiReciever object
        unregisterReceiver(wifiReciever);
        super.onPause();
    }

    protected void onResume() {
        // Register the wifiReciever object
        registerReceiver(wifiReciever, new IntentFilter(
                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }

    public void addListenerOnButton() {

        final Context context = this;
        // Referencing appropriate button: Back button
        button = (Button) findViewById(R.id.button6);
        // call function to finish activity on back button click
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }


    class WifiScanner extends BroadcastReceiver{

        @Override
        // This function is called by Android system after completion of event for which broadcast receiver has received
        public void onReceive(Context context, Intent intent) {

            // initializing the text view
            NetworkList.setText("");
            // initializing the string which will store the network details
            networkDetails = "";
            // List of type ScanResult for storing the scanned wifi network references
            List<ScanResult> networksInRange = wifiManager.getScanResults();

            // in case no wifi networks are detected during scan
            if(networksInRange.size() == 0)
            {
                networkDetails = "No Wifi Networks in range!";
            }

            //Log.d("hello",Integer.toString(networksInRange.size()));

            // for each wifi network that has been detected
            for(int i = 0; i < networksInRange.size(); i++){
                /*
                calculate quality of network using RSSI value of wifi network
                IF rssi value if less than or equal to -100, quality of network is interpreted as zero.
                And greater than or equal to -50 is interpreted as 100% quality.
                So to calculate intermediate quality, we add 100 to RSSI value and twice the result
                */
                int quality;
                if((networksInRange.get(i)).level <= -100)
                    quality = 0;
                else if((networksInRange.get(i)).level >= -50)
                    quality = 100;
                else
                    quality = 2 * ((networksInRange.get(i)).level + 100);

                // Store the details of each wifi network in the form of a string
                networkDetails = networkDetails + ("Network " + i + ":\nSSID: " + (networksInRange.get(i)).SSID + "\nBSSID: " + (networksInRange.get(i)).BSSID + "\nRSSI: " + (Integer.toString((networksInRange.get(i)).level)) + "dbm\nQuality: " + (Integer.toString(quality)) + "%\n\n");
                //Log.d("hello new", networkDetails);
            }

            // Display the string containing all wifi network details in appropriate text view
            NetworkList.setText(networkDetails);
        }
    }
}


