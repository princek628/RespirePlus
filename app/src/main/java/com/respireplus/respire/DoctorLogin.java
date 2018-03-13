package com.respireplus.respire;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by peekay on 12/3/18.
 */

public class DoctorLogin extends AppCompatActivity{

    Button btnLogin;
    TextView tvForgot;
    EditText etDocId, etPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_login);

        btnLogin = (Button) findViewById(R.id.Login);
        tvForgot = (TextView) findViewById(R.id.ForgotPwd);
        etDocId = (EditText) findViewById(R.id.DocId);
        etPwd = (EditText) findViewById(R.id.Password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorLogin.this, DoctorHome.class));
                finish();
            }
        });

        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

        private void showDialog(){

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Forgot Password - Doctors")
                .setMessage("Please check your registered E-mail ID and use the same password which was used for SignUp\nDoctor's password reset feature is unavailable at the moment!!! ")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }
}


