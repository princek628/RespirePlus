package com.respireplus.respire;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Button btnLogin, btnSignup;
    SharedPreferences sharedpreferences;
    private static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Boolean data=sharedpreferences.getBoolean("login",false);
        Boolean add=sharedpreferences.getBoolean("additional",false);
        Boolean signup=sharedpreferences.getBoolean("signup",false);
        String ip=sharedpreferences.getString("ip",null);

        if(ip==null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.InvitationDialog);
            final EditText input = new EditText(MainActivity.this);      //Text Box for email input
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            lp.setMargins(5,5,5,5);
            input.setLayoutParams(lp);
            builder.setView(input);
            builder.setTitle("Connect to Server");
            builder.setCancelable(false);
            //Snackbar.make(, "Forgot Password will be available soon...",
              //      Snackbar.LENGTH_LONG).show();
            builder.setMessage("Enter your server IPv4: ");
            builder.setPositiveButton("Set IP", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if(!input.getText().toString().equals("")) {        //Checking for empty Input
                                //getPwd(input.getText().toString());  //method call for making password request
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("ip",input.getText().toString());
                                editor.commit();
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Empty IP field, Please try again!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
            builder.setNegativeButton("Cancel", null);
            builder.show();
        }


        if (data==true) {
            startActivity(new Intent(MainActivity.this, Home.class));
            finish();
        }
        else if(signup==true && add==false) {
            startActivity(new Intent(MainActivity.this, AdditionalDetails.class));
            finish();
        }
        else{
            setContentView(R.layout.activity_main);
        }
        //AWSMobileClient.getInstance().initialize(this).execute();

        btnLogin = (Button) findViewById(R.id.Login);
        btnSignup = (Button) findViewById(R.id.SignUp);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DoctorLogin.class));
            }
        });
    }
}
