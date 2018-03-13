package com.respireplus.respire;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by peekay on 3/2/18.
 */

public class ForgotPassword extends AppCompatActivity {

    EditText etMobile;
    Button btnGetPwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);



    }
}
