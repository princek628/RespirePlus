package com.respireplus.respire;

/**
 * Created by peekay on 23/2/18.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HeartRateResult extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private String user,Date,cp,thal,mobile;
    private int HR,RR,sno,sno2;
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    Date today = Calendar.getInstance().getTime();
    private ProgressDialog mProgress;
    SharedPreferences sharedpreferences;
    private static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heart_rate_result);

        Date = df.format(today);
        TextView RHR = (TextView) this.findViewById(R.id.HRR);
        Button btnSymp = (Button) this.findViewById(R.id.symptoms);
        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Signing you up...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        Spinner spinner=(Spinner) findViewById(R.id.ChestPain);
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

        btnSymp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                mobile=sharedpreferences.getString("mobile",null);
                if (mobile!=null) {

                    predict();
                }
                else{
                    Toast.makeText(HeartRateResult.this, "Please SignUp first, then enter Additional Details", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch(parent.getId()) {
            case R.id.ChestPain:
                String item = parent.getItemAtPosition(position).toString();
                cp=item;
                sno=position;
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

    private void predict(){
        mProgress.show();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String ip=sharedpreferences.getString("ip",null);
        //URLEncoder.encode(message)
        RequestQueue queue = Volley.newRequestQueue(HeartRateResult.this);  // this = context
        String url = "http://"+ip+"/predict?mobile="+mobile+"&cp="+sno+"&thalach="+String.valueOf(HR);
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        mProgress.dismiss();
                        try {
                            String s= new String(response);
                            if(s.equals("true"))
                            {
                                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("mobile",mobile);
                                editor.putBoolean("signup",true);
                                editor.commit();
                                Toast.makeText(HeartRateResult.this, "SignUp Successful.. Enter Additional Details to continue", Toast.LENGTH_SHORT).show();
                                Intent chatIntent=new Intent(HeartRateResult.this,AdditionalDetails.class);
                                startActivity(chatIntent);
                                finish();
                            }
                            else {
                                Snackbar.make(findViewById(R.id.SignUp), response+"!! Try Again...", Snackbar.LENGTH_LONG).show();
                            }
                        }catch (Exception e){
                            System.out.println(e.getMessage().toString());
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgress.dismiss();
                        Snackbar.make(findViewById(R.id.HRR), "Check your Internet Connection", Snackbar.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                //params to login url
                Map<String, String>  params = new HashMap<String, String>();
                return params;
            }
        };
        queue.add(postRequest);
    }
}
