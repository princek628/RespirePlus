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
import com.famoussoft.libs.JSON.JSONArray;
import com.famoussoft.libs.JSON.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by peekay on 11/5/18.
 */

public class DeleteUser extends AppCompatActivity {

    EditText etNum;
    Button btnDel;
    private ProgressDialog mProgress;
    SharedPreferences sharedpreferences;
    private static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_user);

        etNum=(EditText)findViewById(R.id.Numbr);
        btnDel=(Button)findViewById(R.id.Del);
        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Deleting User...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s=etNum.getText().toString();
                if(s!=null && s.length()==10)
                    sendreq(s);
                else
                    Snackbar.make(findViewById(R.id.Numbr), "Enter a valid mobile number", Snackbar.LENGTH_LONG).show();

            }
        });

    }

    private void sendreq(final String mobile){
        mProgress.show();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String ip=sharedpreferences.getString("ip",null);
        //URLEncoder.encode(message)
        RequestQueue queue = Volley.newRequestQueue(DeleteUser.this);  // this = context
        String url = "http://"+ip+"/delusr?mobile="+mobile;
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        mProgress.dismiss();
                        try {
                            JSONObject jobj = new JSONObject(response);
                            int s=jobj.getInt("rowCount");
                            if(s!=0)
                            {
                                Toast.makeText(DeleteUser.this, "User Details Deleted Successfully...", Toast.LENGTH_LONG).show();
                                Intent chatIntent=new Intent(DeleteUser.this,Home.class);
                                startActivity(chatIntent);
                                finish();
                            }
                            else if(s==0)
                                Snackbar.make(findViewById(R.id.Del), "No user details found with entered number...", Snackbar.LENGTH_LONG).show();
                            else
                                Snackbar.make(findViewById(R.id.Del), response+"!! Try Again...", Snackbar.LENGTH_LONG).show();

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
                        Snackbar.make(findViewById(R.id.doctor_home), "Check your Internet Connection", Snackbar.LENGTH_LONG).show();
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
