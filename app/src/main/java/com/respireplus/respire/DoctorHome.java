package com.respireplus.respire;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.famoussoft.libs.JSON.JSONArray;
import com.famoussoft.libs.JSON.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by peekay on 12/3/18.
 */

public class DoctorHome extends AppCompatActivity {

    EditText etNum;
    Button btnSrch;
    TextView tvDisplay;
    private ProgressDialog mProgress;
    SharedPreferences sharedpreferences;
    private static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_home);

        etNum=(EditText)findViewById(R.id.pnum);
        btnSrch=(Button)findViewById(R.id.srch);
        //tvDisplay=(TextView)findViewById(R.id.srchrslt);
        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Searching...");
        mProgress.setMessage("Please wait...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        btnSrch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=etNum.getText().toString();
                if(s==null || s.length()!=10)
                    Snackbar.make(findViewById(R.id.history), "Enter a valid mobile number", Snackbar.LENGTH_LONG).show();
                else
                    search(s);

            }
        });

    }

    private void search(final String mobile){
        mProgress.show();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String ip=sharedpreferences.getString("ip",null);
        //URLEncoder.encode(message)
        RequestQueue queue = Volley.newRequestQueue(DoctorHome.this);  // this = context
        String url = "http://"+ip+"/doc-srch?mobile="+mobile;
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        mProgress.dismiss();
                        try {
                            JSONObject jobj = new JSONObject(response);
                            String print="";
                            int row=jobj.getInt("rowCount");
                            JSONArray jarray=new JSONArray(jobj.getJSONArray("rows").toString());
                            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.history);
                            mainLayout.removeAllViews();
                            for(int i=0;i<row;i++)
                            {
                                LinearLayout ll = new LinearLayout(getApplicationContext());
                                ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT));
                                ll.setOrientation(LinearLayout.VERTICAL);
                                ll.setPadding(10, 10, 10, 10);

                                final LinearLayout sll = new LinearLayout(getApplicationContext());
                                sll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                sll.setPadding(15, 15, 5, 15);
                                sll.setOrientation(LinearLayout.VERTICAL);

                                TextView tvTitle = new TextView(getApplicationContext());
                                tvTitle.setLayoutParams(new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                tvTitle.setTextColor(Color.BLACK);
                                tvTitle.setGravity(Gravity.CENTER);


                                JSONObject jobj2=new JSONObject(jarray.getJSONObject(i).toString());
                                int cp=jobj2.getInt("cp");
                                int thalach=jobj2.getInt("thalach");
                                String rslt=jobj2.getString("result");
                                print=print+i+"\t\t\t\t\t"+cp+"\t\t\t\t\t\t\t"+thalach+"\t\t\t\t\t\t\t\t\t"+rslt+"\n";

                                tvTitle.setText(Html.fromHtml(i+"\t\t\t\t\t"+cp+"\t\t\t\t\t\t\t"+thalach+"\t\t\t\t\t\t\t\t\t"+rslt));
                                sll.addView(tvTitle);

                                ll.addView(sll);
                                mainLayout.addView(ll);
                            }

                            //tvDisplay.setText("SN ChestPain HeartRate Prediction\n"+print);

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
                        Snackbar.make(findViewById(R.id.history), "Check your Internet Connection", Snackbar.LENGTH_LONG).show();
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
