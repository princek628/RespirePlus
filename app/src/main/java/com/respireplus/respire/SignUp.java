package com.respireplus.respire;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by peekay on 2/2/18.
 */

public class SignUp extends AppCompatActivity {

    Button btnLogin, btnSignup;
    TextInputEditText etMobile, etPwd, etName, etMail;
    TextView tvPolicy;
    Toolbar tlbr;
    private ProgressDialog mProgress;
    SharedPreferences sharedpreferences;
    private static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        tlbr = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tlbr);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnLogin = (Button) findViewById(R.id.Login);
        btnSignup = (Button) findViewById(R.id.SignUp);
        etMail = (TextInputEditText) findViewById(R.id.Email);
        etMobile = (TextInputEditText) findViewById(R.id.MobileNo);
        etName = (TextInputEditText) findViewById(R.id.Name);
        etPwd = (TextInputEditText) findViewById(R.id.Password);
        tvPolicy = (TextView) findViewById(R.id.tnc);
        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Signing you up...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, AdditionalDetails.class));
                finish();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=etMail.getText().toString();
                String mobile=etMobile.getText().toString();
                String name=etName.getText().toString();
                String pwd=etPwd.getText().toString();
                if(mobile.length()!=10){
                    Snackbar.make(findViewById(R.id.SignUp), "Enter only 10 digit mobile number, +91 prefix is not required!!", Snackbar.LENGTH_LONG).show();
                } else {
                    signup(mobile, pwd, name, email);
                }
            }
        });

    }

    private void signup(final String mobile,final String pwd,final String name,final String email){
        mProgress.show();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String ip=sharedpreferences.getString("ip",null);
        //URLEncoder.encode(message)
        RequestQueue queue = Volley.newRequestQueue(SignUp.this);  // this = context
        String url = "http://"+ip+"/create-user?mobile="+mobile+"&password="+pwd+"&email="+email+"&name="+ URLEncoder.encode(name);
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
                                Toast.makeText(SignUp.this, "SignUp Successful.. Enter Additional Details to continue", Toast.LENGTH_SHORT).show();
                                Intent chatIntent=new Intent(SignUp.this,AdditionalDetails.class);
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
                        Snackbar.make(findViewById(R.id.SignUp), "Check your Internet Connection", Snackbar.LENGTH_LONG).show();
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
