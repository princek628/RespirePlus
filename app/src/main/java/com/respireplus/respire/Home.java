package com.respireplus.respire;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

/**
 * Created by peekay on 3/2/18.*
 **/

public class Home extends AppCompatActivity {
    Button btnMeasure, btnEmergency;
    Toolbar tlbr;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Toolbar tb;

        tlbr = (Toolbar) findViewById(R.id.tb);
        setSupportActionBar(tlbr);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnMeasure = (Button)findViewById(R.id.Measure);
        btnEmergency = (Button) findViewById(R.id.Emergency);

        btnMeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, HeartRateProcess.class));
                finish();
            }
        });

        btnEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, Emergency.class));
            }
        });
    }
    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Home.this, Login.class);
        //   i.putExtra("Usr", user);
        startActivity(i);
        finish();
    }*/
}
