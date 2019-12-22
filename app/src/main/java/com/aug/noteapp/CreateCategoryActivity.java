package com.aug.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class CreateCategoryActivity extends AppCompatActivity implements ColorsBottomSheetDialogFragment.ItemClickListener {
    private DatabaseReference mReferenceCategory;
    private FirebaseAuth mAuth;
    private View mViewCategory;
    private EditText mEtNameCategory;
    private int backgroundCategorySelected = ColorsBottomSheetDialogFragment.COLOR_PURPLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);
        init();
    }

    private void init() {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Initialize Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mReferenceCategory = database.getReference("Category");

        mViewCategory = findViewById(R.id.view_category_create_category_activity);
        mEtNameCategory = findViewById(R.id.et_name_category_create_category_activity);

    }

    public void openColorsBottomSheetDialog(View view) {
        ColorsBottomSheetDialogFragment.newInstance().show(getSupportFragmentManager(), ColorsBottomSheetDialogFragment.TAG);
    }

    /**
     * @param nameCategory the name category entered by the user
     * @return is nameCategory is null or not
     */
    private boolean validation(String nameCategory) {
        boolean validate = true;
        if (nameCategory.isEmpty()) {
            validate = false;
            mEtNameCategory.setError(getString(R.string.empty_filed));
        }
        return validate;
    }

    @Override
    public void onItemClick(int color) {
        backgroundCategorySelected = color;
        Utils.setBackgroundCategory(mViewCategory, backgroundCategorySelected);
    }

    public void closeActivity(View view) {
        finish();
    }

    public void saveCategory(View view) {
        String userId = mAuth.getUid();
        String nameCategory = mEtNameCategory.getText().toString();
        long createAt = new Date().getTime();
        long updateAt = new Date().getTime();
        if (validation(nameCategory))
            createNewCategory(userId, nameCategory, backgroundCategorySelected, createAt, updateAt);
    }

    private void createNewCategory(String userId, String nameCategory, int backgroundCategorySelected, long createAt, long updateAt) {
        Utils.showLoading(this);
        mReferenceCategory.child(userId).push().setValue(new Category(nameCategory, backgroundCategorySelected, createAt, updateAt))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Utils.hideLoading();
                        // Write was successful!
                        Toast.makeText(CreateCategoryActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Utils.hideLoading();
                        // Write failed
                        Utils.showToast(CreateCategoryActivity.this, R.string.database_failed);
                    }
                });
        ;
    }
}
