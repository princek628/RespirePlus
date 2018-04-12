package com.respireplus.respire;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by peekay on 3/2/18.
 */

public class ForgotPassword extends AppCompatActivity {

    EditText etMobile;
    Button btnGetPwd;
    private ProgressDialog mProgress;
    SharedPreferences sharedpreferences;
    private static final String MyPREFERENCES = "MyPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        etMobile=(EditText)findViewById(R.id.Name);
        btnGetPwd=(Button) findViewById(R.id.GetPassword);
        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Signing you up...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        btnGetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num = etMobile.getText().toString();
                if(num!=null &&num.length()==10){
                    getpwd(num);
                }
                else
                    Toast.makeText(ForgotPassword.this, "Please enter a valid mobile number", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getpwd(final String mobile){
        mProgress.show();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String ip=sharedpreferences.getString("ip",null);
        RequestQueue queue = Volley.newRequestQueue(ForgotPassword.this);  // this = context
        String url = "http://"+ip+"/forgotpwd?mobile="+mobile;
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
                                Toast.makeText(ForgotPassword.this, "Password sent to registered mobile number, Please Login!!", Toast.LENGTH_SHORT).show();
                                Intent chatIntent=new Intent(ForgotPassword.this,Login.class);
                                startActivity(chatIntent);
                                finish();
                            }
                            else {
                                Snackbar.make(findViewById(R.id.ForgotPwd), response+"!! Try Again...", Snackbar.LENGTH_LONG).show();
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
                        Snackbar.make(findViewById(R.id.ForgotPwd), "Check your Internet Connection", Snackbar.LENGTH_LONG).show();
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
