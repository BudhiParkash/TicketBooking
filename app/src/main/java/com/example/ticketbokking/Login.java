package com.example.ticketbokking;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {

    private static final String TAG = "Phone" ;
    private EditText mEnterdNumber;
    private Button mContinue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();


        mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = mEnterdNumber.getText().toString();
                if(TextUtils.isEmpty(number)){
                    mEnterdNumber.setError("Please Enter Phone Number");
                    mEnterdNumber.requestFocus();
                    return;
                }

                else if (number.matches("([0-9]{10})")){
                    Intent i = new Intent(Login.this,OtpVerfiy.class);
                    i.putExtra("num",number);
                    startActivity(i);

                }
            }
        });

    }






    private void initView() {
        mEnterdNumber = findViewById(R.id.enterdNumber);
        mContinue = findViewById(R.id.Continue);

    }
}