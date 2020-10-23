package com.example.ticketbokking;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpVerfiy extends AppCompatActivity {

    private EditText mOtpEnter;
    private Button mBContinue2;
    private TextView mOtpcountdown;
    
    String num , phoneN , verificationId;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verfiy);
        initView();

        mAuth = FirebaseAuth.getInstance();

        phoneN = getIntent().getStringExtra("num");
        num = "+91"+phoneN;
        sendVerificationCode(num);

        mBContinue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otps = mOtpEnter.getText().toString().trim();

                if(TextUtils.isEmpty(otps) || otps.length()!= 6){
                    mOtpEnter.setError("Please Enter Otp");
                    mOtpEnter.requestFocus();
                    return;
                }
                else {
                    verifyCode(otps);

                }
            }
        });
    }

    private void verifyCode(String otps) {
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otps);
            signInWithCredential(credential);
        }catch (Exception e){
            Toast toast = Toast.makeText(getApplicationContext(), "Verification Code is wrong, try again", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
    }
    //Sign in with phone no. using Firebase
    public void signInWithCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent i = new Intent(OtpVerfiy.this, Details.class);
                            i.putExtra("num",phoneN);
                            startActivity(i);
                            finish();

                        }
                        else{
                            Toast.makeText(OtpVerfiy.this,"Please Enter Correct Otp" , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String num) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                num,
                60, //validation of ssec
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }
    //Verification process
    public PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OtpVerfiy.this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }
    };

        private void initView() {
        mOtpEnter = findViewById(R.id.otpEnter);
        mBContinue2 = findViewById(R.id.bContinue2);
        mOtpcountdown = findViewById(R.id.otpcountdown);
    }
}