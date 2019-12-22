package com.aug.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText mEtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        init();
    }

    private void init() {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mEtEmail = findViewById(R.id.et_email_forget_password_activity);
    }

    public void recoverPassword(View view) {
        String email = mEtEmail.getText().toString();
        if (validation(email)) {
            resetPassword(email);
        }
    }

    /**
     * @param email the email entered by the user
     * @return is email is valid or not
     */
    private boolean validation(String email) {
        boolean validate = true;
        if (email.isEmpty()) {
            validate = false;
            mEtEmail.setError(getString(R.string.empty_filed));
        }
        return validate;
    }

    private void resetPassword(String email) {
        //show progress bar
        Utils.showLoading(this);
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //hide progress bar
                        Utils.hideLoading();
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgetPasswordActivity.this, "Email sent.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ForgetPasswordActivity.this, CheckYourEmailActivity.class));
                            finish();
                        } else {
                            Toast.makeText(ForgetPasswordActivity.this, "Check the email is entered", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void returnToSignInActivity(View view) {
        finish();
    }

}