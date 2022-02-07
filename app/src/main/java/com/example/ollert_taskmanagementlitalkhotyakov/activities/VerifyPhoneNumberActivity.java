package com.example.ollert_taskmanagementlitalkhotyakov.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ollert_taskmanagementlitalkhotyakov.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneNumberActivity extends AppCompatActivity {

    private TextInputLayout form_EDT_EnterOTP;
    private MaterialButton form_BTN_verify;
    private MaterialTextView resendCode;
    private ProgressBar progress_bar;
    private FirebaseAuth firebaseAuth;
    private String verificationCodeBySystem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        form_EDT_EnterOTP = findViewById(R.id.form_EDT_EnterOTP);
        form_BTN_verify = findViewById(R.id.form_BTN_verify);
        resendCode = findViewById(R.id.resendCode);
        progress_bar = findViewById(R.id.progress_bar);
        progress_bar.setVisibility(View.GONE);


        String phoneNumber = getIntent().getStringExtra("phoneNumber");

        firebaseAuth = FirebaseAuth.getInstance();
        sendVverificationCodeToUser(phoneNumber);

        form_BTN_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = form_EDT_EnterOTP.getEditText().getText().toString();
                if (code.isEmpty() || code.length() < 6) {
                    form_EDT_EnterOTP.setError("Wrong OTP...");
                    form_EDT_EnterOTP.requestFocus();
                    return;
                }
                progress_bar.setVisibility(View.VISIBLE);
                VerifyCode(code);
            }
        });

        resendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = form_EDT_EnterOTP.getEditText().getText().toString();
                if (code.isEmpty()) {
                    Toast.makeText(VerifyPhoneNumberActivity.this, "please enter your phone number", Toast.LENGTH_SHORT).show();
                } else {
                    sendVverificationCodeToUser(phoneNumber);
                }
            }
        });
    }


    private void sendVverificationCodeToUser(String phoneNumber) {
//        firebaseAuth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodeBySystem = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                progress_bar.setVisibility(View.VISIBLE);
                VerifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerifyPhoneNumberActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void VerifyCode(String codeByUser) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, codeByUser);
        singInTheUserByCredentials(credential);
    }

    private void singInTheUserByCredentials(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyPhoneNumberActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                            //TODO:CHEK THE CLASS
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Toast.makeText(VerifyPhoneNumberActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
