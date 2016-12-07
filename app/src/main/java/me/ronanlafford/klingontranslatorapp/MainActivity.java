package me.ronanlafford.klingontranslatorapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Custom button to move to the Translation activity
        Button b = (Button) findViewById(R.id.button4);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TranslationActivity.class);
                startActivity(intent);
            }
        });

        // Floating Action Buttons
        FloatingActionButton myFab1 = (FloatingActionButton) findViewById(R.id.floatingActionButton8);
        FloatingActionButton myFab2 = (FloatingActionButton) findViewById(R.id.floatingActionButton9);
        FloatingActionButton myFab3 = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        //Open sms messaging set to Starfleet as the recipient
        myFab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iSMS = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:STARFLEET"));
                iSMS.putExtra("sms_body", "Hello Command, We have an urgent message for you!");
                startActivity(iSMS);
            }
        });

        //Open the Dial Pad to make a call
        myFab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iDial = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:12345678"));
                startActivity(iDial);
            }
        });

        //Floating Action Button to move to the Database activity to view the records
        myFab3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DatabaseActivity.class);
                startActivity(intent);
            }
        });


    }


}
