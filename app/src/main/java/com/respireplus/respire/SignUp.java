package com.respireplus.respire;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by peekay on 2/2/18.
 */

public class SignUp extends AppCompatActivity {

    Button btnLogin, btnSignup;
    EditText etMobile, etPwd, etName, etMail;
    TextView tvPolicy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        btnLogin = (Button) findViewById(R.id.Login);
        btnSignup = (Button) findViewById(R.id.SignUp);
        etMail = (EditText) findViewById(R.id.Email);
        etMobile = (EditText) findViewById(R.id.MobileNo);
        etName = (EditText) findViewById(R.id.Name);
        etPwd = (EditText) findViewById(R.id.Password);
        tvPolicy = (TextView) findViewById(R.id.tnc);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, Login.class));
                finish();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, AdditionalDetails.class));
                finish();
            }
        });



    }
}
