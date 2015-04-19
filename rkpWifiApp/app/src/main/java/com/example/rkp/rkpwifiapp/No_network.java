package com.example.rkp.rkpwifiapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by RKP on 21-03-2015.
 */
public class No_network extends Activity{

    Button button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting appropriate layout: activity_no_network
        setContentView(R.layout.activity_no_network);
        // adding listeners for buttons in the activity
        addListenerOnButton();
    }

    public void addListenerOnButton() {

        final Context context = this;
        // Reference appropriate button: Back button
        button = (Button) findViewById(R.id.button2);
        // Call function to finish activity on pressing back button
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }
}
