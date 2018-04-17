package com.respireplus.respire;

/**
 * Created by peekay on 23/2/18.
 */
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HeartRateResult extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private String user,Date,cp,thal;
    private int HR,RR,sno,sno2;
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    Date today = Calendar.getInstance().getTime();
    Spinner spinner=(Spinner) findViewById(R.id.ChestPain);
    Spinner spinner2=(Spinner) findViewById(R.id.Thal);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heart_rate_result);

        Date = df.format(today);
        TextView RHR = (TextView) this.findViewById(R.id.HRR);
        Button btnSymp = (Button) this.findViewById(R.id.symptoms);

        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add(0,"Select Option");
        categories.add(1,"Mild Pain");
        categories.add(2,"Severe Pain");
        categories.add(3,"Persistent Pain");
        categories.add(4,"No Chest Pain");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            HR = bundle.getInt("bpm");
            RR= bundle.getInt("rr");
            //user = bundle.getString("Usr");
            Log.d("DEBUG_TAG", "ccccc"+ user);
            RHR.setText("Heart Rate:"+String.valueOf(HR)+" bpm\nRespiratory Rate:"+String.valueOf(RR)+" per minute");
            //Toast tt = Toast.makeText(getApplicationContext(),String.valueOf(RR),Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch(parent.getId()) {
            case R.id.ChestPain:
                String item = parent.getItemAtPosition(position).toString();
                cp=item;
                sno=position;
                break;
             case R.id.Thal:
                 String item2 = parent.getItemAtPosition(position).toString();
                 thal=item2;
                 sno2=position;
                 break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Snackbar.make(findViewById(R.id.Thal), "You Selected Nothing", Snackbar.LENGTH_LONG).show();
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
