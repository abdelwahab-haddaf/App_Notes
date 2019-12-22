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

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText mEtEmail;
    private EditText mEtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
    }

    private void init() {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mEtEmail = findViewById(R.id.et_email_sign_up_activity);
        mEtPassword = findViewById(R.id.et_password_sign_up_activity);
    }

    public void signUp(View view) {
        String email = mEtEmail.getText().toString();
        String password = mEtPassword.getText().toString();
        if (validation(email, password)) {
            createAccount(email, password);
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
        } else if (!Utils.isEmailValid(email)) {
            validate = false;
            mEtEmail.setError(getString(R.string.email_address_not_valid));
        }
        if (password.isEmpty()) {
            validate = false;
            mEtPassword.setError(getString(R.string.empty_filed));
        } else if (password.length() < 8) {
            validate = false;
            mEtPassword.setError(getString(R.string.password_short));
        }
        return validate;
    }

    private void createAccount(String email, String password) {
        //show progress bar
        Utils.showLoading(this);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Utils.hideLoading();
                        if (task.isSuccessful()) {
                            // Sign in success
                            Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Utils.showToast(SignUpActivity.this, R.string.authentication_failed);
                        }
                    }
                });
    }

    public void moveToSignInActivity(View view) {
        startActivity(new Intent(this, SignInActivity.class));
    }

    public void closeActivity(View view) {
        finish();
    }
}
