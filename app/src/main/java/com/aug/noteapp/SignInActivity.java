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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText mEtEmail;
    private EditText mEtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();
    }

    private void init() {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mEtEmail = findViewById(R.id.et_email_sign_in_activity);
        mEtPassword = findViewById(R.id.et_password_sign_in_activity);
    }

    public void signIn(View view) {
        String email = mEtEmail.getText().toString();
        String password = mEtPassword.getText().toString();
        if (validation(email, password)) {
            signInAccount(email, password);
        }
    }

    /**
     * @param email    the email entered by the user
     * @param password the password entered by the user
     * @return is email and password is valid or not
     */
    private boolean validation(String email, String password) {
        boolean validate = true;
        if (email.isEmpty()) {
            validate = false;
            mEtEmail.setError(getString(R.string.empty_filed));
        }
        if (password.isEmpty()) {
            validate = false;
            mEtPassword.setError(getString(R.string.empty_filed));
        }
        return validate;
    }

    private void signInAccount(String email, String password) {
        //show progress bar
        Utils.showLoading(this);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Utils.hideLoading();
                        if (task.isSuccessful()) {
                            // Sign in success
                            Toast.makeText(SignInActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails
                            Utils.showToast(SignInActivity.this, R.string.authentication_failed);
                        }
                    }
                });
    }

    public void moveToSignUpActivity(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    public void moveToForgetPasswordActivity(View view) {
        startActivity(new Intent(this, ForgetPasswordActivity.class));
    }

    public void closeActivity(View view) {
        finish();
    }
}
