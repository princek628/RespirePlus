package com.respireplus.respire;

/**
 * Created by peekay on 23/2/18.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HeartRateResult extends AppCompatActivity{

    private String user,Date;
    int HR,RR;
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    Date today = Calendar.getInstance().getTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heart_rate_result);

        Date = df.format(today);
        TextView RHR = (TextView) this.findViewById(R.id.HRR);
        ImageButton SHR = (ImageButton)this.findViewById(R.id.SendHR);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            HR = bundle.getInt("bpm");
            RR= bundle.getInt("rr");
            //user = bundle.getString("Usr");
            Log.d("DEBUG_TAG", "ccccc"+ user);
            RHR.setText("Heart Rate:"+String.valueOf(HR)+" bpm\nRespiratory Rate:"+String.valueOf(RR)+" per minute");
            Toast tt = Toast.makeText(getApplicationContext(),String.valueOf(RR),Toast.LENGTH_LONG);
        }

        SHR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Health Watcher");
                i.putExtra(Intent.EXTRA_TEXT   , user+"'s Heart Rate "+"\n"+" at "+ Date +" is :    "+HR);
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(HeartRateResult.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(HeartRateResult.this, Home.class);
     //   i.putExtra("Usr", user);
        startActivity(i);
        finish();
    }
}
