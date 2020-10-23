package com.example.ticketbokking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Details extends AppCompatActivity {

    private LinearLayout mSsig2;
    private EditText mEmailenter;
    private EditText mUsername;
    private EditText mGender;
    private Button mBFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initView();
        mBFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail = mEmailenter.getText().toString();
                String username = mUsername.getText().toString();
                String gender = mGender.getText().toString();



                if(TextUtils.isEmpty(mail)){
                    mEmailenter.setError("Please Enter Email");
                    mEmailenter.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(username)){
                    mUsername.setError("Please Enter password");
                    mUsername.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(gender)){
                    mGender.setError("Please Enter password");
                    mGender.requestFocus();
                    return;
                }

                else{
                    String numb = getIntent().getStringExtra("num");
                    Intent i = new Intent(Details.this,MainActivity.class);
                    SharedPreferences.Editor editor = getSharedPreferences("ProfileData", MODE_PRIVATE).edit();
                    editor.putString("name", username);
                    editor.putString("email",mail );
                    editor.putString("gender",gender);
                    editor.putString("number", numb);
                    editor.apply();
                    startActivity(i);
                    finish();
                }



            }
        });

    }


    private void initView() {
        mSsig2 = findViewById(R.id.ssig2);
        mEmailenter = findViewById(R.id.emailenter);
        mUsername = findViewById(R.id.username);
        mGender = findViewById(R.id.gender);
        mBFinish = findViewById(R.id.bFinish);
    }
}