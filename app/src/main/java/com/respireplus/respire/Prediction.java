package com.respireplus.respire;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by peekay on 19/4/18.
 */

public class Prediction extends AppCompatActivity {

    Button btnBack;
    TextView tvRslt;
    String s;
    SharedPreferences sharedpreferences;
    private static final String MyPREFERENCES = "MyPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prediction);

        btnBack=(Button)findViewById(R.id.back);
        tvRslt=(TextView)findViewById(R.id.prslt);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String rslt=sharedpreferences.getString("result",null);
        if(rslt.equals("["+"\""+"[0]"+"\""+"]"))
            s="No Heart and Respiratory Diseases";
        else if(rslt.equals("["+"\""+"[1]"+"\""+"]"))
            s="You may have Some Respiratory Problems.. Please contact your doctor immediately";

        tvRslt.setText("Prediction Result :"+s);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Prediction.this, Home.class));
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Prediction.this, Home.class);
        //   i.putExtra("Usr", user);
        startActivity(i);
        finish();
    }
}
