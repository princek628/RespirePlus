package com.respireplus.respire;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by peekay on 3/2/18.
 */

public class AdditionalDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    RadioGroup radioGroup,radioGroup2;
    EditText etDate, etHeight, etWeight,etChol;
    Button btnSubmit;
    private String gndr,history,mobile,fbs,thal;
    private int sno,fbsid,sno2,restecg,gndrid;
    private ProgressDialog mProgress;
    SharedPreferences sharedpreferences;
    private static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.additional_details);

        etDate = (EditText)findViewById(R.id.Age);
        etHeight = (EditText)findViewById(R.id.Height);
        etWeight = (EditText)findViewById(R.id.Weight);
        etChol = (EditText)findViewById(R.id.Chol);
        btnSubmit = (Button) findViewById(R.id.Submit);
        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Saving Additional Details...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        Spinner spinner=(Spinner) findViewById(R.id.List);
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add(0,"Select Option");
        categories.add(1,"Normal BP");
        categories.add(2,"High BP");
        categories.add(3,"Low BP");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        Spinner spinner2=(Spinner) findViewById(R.id.Thal);
        spinner2.setOnItemSelectedListener(this);
        List<String> categories2 = new ArrayList<String>();
        categories2.add(0,"Select Option");
        categories2.add(1,"Normal");
        categories2.add(2,"Genetic");
        categories2.add(3,"Non Genetic");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories2);
        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner2.setAdapter(dataAdapter2);

        radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
        radioGroup2 = (RadioGroup)findViewById(R.id.RadioGroup2);
        radioGroup.clearCheck();
        radioGroup2.clearCheck();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                RadioButton rb= radioGroup.findViewById(i);
                gndr=rb.getText().toString();
                if(gndr.equals("Male"))
                    gndrid=1;
                else if(gndr.equals("Female"))
                    gndrid=0;
            }
        });

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup2, @IdRes int i) {
                RadioButton rb= radioGroup2.findViewById(i);
                fbs=rb.getText().toString();
                if(fbs.equals("Normal"))
                    fbsid=0;
                else fbsid = 1;
                //Toast.makeText(getApplicationContext(), fbs+" "+i, Toast.LENGTH_LONG).show();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String t= etDate.getText().toString();
                int date=Integer.parseInt(t);
                t=etHeight.getText().toString();
                int height=Integer.parseInt(t);
                t=etWeight.getText().toString();
                int weight=Integer.parseInt(t);
                t=etChol.getText().toString();
                int chol=Integer.parseInt(t);

                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                mobile=sharedpreferences.getString("mobile",null);
                if (mobile!=null) {

                    additional(date,height,weight,chol);
                }
                else{
                    Toast.makeText(AdditionalDetails.this, "Please SignUp first, then enter Additional Details", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()) {
            case R.id.List:
                String item = parent.getItemAtPosition(position).toString();
                history=item;
                sno=position;
                if(sno==1)
                    sno=0;
                else if(sno==2 || sno==3)
                    sno=2;
                else
                    Snackbar.make(findViewById(R.id.Thal), "You Selected Nothing", Snackbar.LENGTH_LONG).show();
                break;
            case R.id.Thal:
                String item2 = parent.getItemAtPosition(position).toString();
                thal=item2;
                sno2=position;
                if(thal.equals("Normal"))
                    sno2=3;
                else if(thal.equals("Genetic"))
                    sno=6;
                else if(thal.equals("Non Genetic"))
                    sno=7;
                else
                    Snackbar.make(findViewById(R.id.Thal), "You Selected Nothing", Snackbar.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Snackbar.make(findViewById(R.id.Thal), "You Selected Nothing", Snackbar.LENGTH_LONG).show();
    }

    private void additional(final int age,final int height,final int weight, final int chol){
        mProgress.show();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String ip=sharedpreferences.getString("ip",null);
        RequestQueue queue = Volley.newRequestQueue(AdditionalDetails.this);  // this = context
        String url = "http://"+ip+"/add-details?mobile="+mobile+"&gender="+gndrid+"&age="+age+"&height="+height+"&weight="+weight+"&history="+sno+"&chol="+chol+"&fbs="+fbsid+"&thal="+sno2+"&restecg="+sno;
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
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putBoolean("additional",true);
                                editor.commit();
                                Toast.makeText(AdditionalDetails.this, "Details successfully saved.. Please Login", Toast.LENGTH_SHORT).show();
                                Intent chatIntent=new Intent(AdditionalDetails.this,Login.class);
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
                        Toast.makeText(getApplicationContext(), "Check your Internet Connection!!", Toast.LENGTH_LONG).show();
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
