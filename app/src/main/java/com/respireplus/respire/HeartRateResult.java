package com.respireplus.respire;

/**
 * Created by peekay on 23/2/18.
 */
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
        Button btnSymp = (Button) this.findViewById(R.id.symptoms);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            HR = bundle.getInt("bpm");
            RR= bundle.getInt("rr");
            //user = bundle.getString("Usr");
            Log.d("DEBUG_TAG", "ccccc"+ user);
            RHR.setText("Heart Rate:"+String.valueOf(HR)+" bpm\nRespiratory Rate:"+String.valueOf(RR)+" per minute");
            //Toast tt = Toast.makeText(getApplicationContext(),String.valueOf(RR),Toast.LENGTH_LONG);
        }

        btnSymp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int TIME = 8000; //8000 ms (8 Seconds)

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        function(); //call function!
                    }
                }, TIME);
            }
        });
    }

    public void function(){

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
