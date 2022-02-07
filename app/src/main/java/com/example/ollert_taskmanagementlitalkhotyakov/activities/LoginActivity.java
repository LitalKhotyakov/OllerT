package com.example.ollert_taskmanagementlitalkhotyakov.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ollert_taskmanagementlitalkhotyakov.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout form_EDT_FullName;
    private TextInputLayout form_EDT_UserName;
    private TextInputLayout form_EDT_Email;
    private TextInputLayout form_EDT_phoneNumber;
    private MaterialButton form_BTN_continue;

    private FirebaseFirestore fStore;



    private boolean isOTP = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initForm();


        form_BTN_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fStore = FirebaseFirestore.getInstance();

                String FullName = form_EDT_FullName.getEditText().getText().toString();
                String UserName = form_EDT_UserName.getEditText().getText().toString();
                String Email = form_EDT_Email.getEditText().getText().toString();
                String phoneNumber = form_EDT_phoneNumber.getEditText().getText().toString();

                Intent intent = new Intent(getApplicationContext() , VerifyPhoneNumberActivity.class);
                intent.putExtra("phoneNumber",phoneNumber);
                startActivity(intent);

                DocumentReference documentReference = fStore.collection("users").document(phoneNumber);
            }

        });

        if (isOTP){
            Intent intent = new Intent(this, MainActivity2.class);
            startActivity(intent);
        }
    }

    private void initForm() {
        form_EDT_FullName = findViewById(R.id.form_EDT_FullName);
        form_EDT_UserName = findViewById(R.id.form_EDT_UserName);
        form_EDT_Email = findViewById(R.id.form_EDT_Email);
        form_EDT_phoneNumber = findViewById(R.id.form_EDT_phoneNumber);
        form_BTN_continue = findViewById(R.id.form_BTN_continue);

        form_EDT_Email.getEditText().setText("khyk96@gmail.com");
        form_EDT_FullName.getEditText().setText("Lital Khotyakov");
        form_EDT_UserName.getEditText().setText("Litalk");
        form_EDT_phoneNumber.getEditText().setText("+972502439799");
    }

    private Boolean validateUsername() {
        String val = form_EDT_UserName.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            form_EDT_UserName.setError("Field cannot be empty ");
            return false;
        } else if (val.length() >= 15) {
            form_EDT_UserName.setError("Username too long");
            return false;
        } else {
            form_EDT_UserName.setError(null);
            form_EDT_UserName.setErrorEnabled(false);
            return true;
        }
    }
}