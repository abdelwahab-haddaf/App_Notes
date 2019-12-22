package com.aug.noteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

public class CreateNoteActivity extends AppCompatActivity implements ColorsBottomSheetDialogFragment.ItemClickListener {
    private static final String TAG = "CreateNoteActivity";
    public static final String KEY_ID_CATEGORY = "key_id_category";
    private int backgroundNoteSelected = ColorsBottomSheetDialogFragment.COLOR_PURPLE;
    private String categoryId;
    private DatabaseReference mReferenceNote;
    private FirebaseAuth mAuth;
    private ConstraintLayout mConstraintLayout;
    private EditText mEtTitleNote;
    private EditText mEtContentNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        if (getIntent() != null) {
            categoryId = getIntent().getStringExtra(KEY_ID_CATEGORY);
        }
        init();
        Utils.setBackgroundRectangle(mConstraintLayout, backgroundNoteSelected);
    }

    public void openColorsBottomSheetDialog(View view) {
        ColorsBottomSheetDialogFragment.newInstance().show(getSupportFragmentManager(), ColorsBottomSheetDialogFragment.TAG);
    }

    private void init() {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Initialize Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mReferenceNote = database.getReference("Note");

        mConstraintLayout = findViewById(R.id.container_create_note_activity);
        mEtTitleNote = findViewById(R.id.et_title_create_note_activity);
        mEtContentNote = findViewById(R.id.et_content_create_note_activity);
    }

    public void saveNote(View view) {
        String userId = mAuth.getUid();
        String title = mEtTitleNote.getText().toString();
        long createAt = new Date().getTime();
        long updateAt = new Date().getTime();
        String content = mEtContentNote.getText().toString();
        if (validation(title, content)) {
            createNewNote(userId, categoryId, title, content, backgroundNoteSelected, createAt, updateAt);
        }
    }

    private void createNewNote(String userId, String idCategory, String titleNote, String contentTitle,
                               int backgroundNoteSelected, long createAt, long updateAt) {
        Utils.showLoading(this);
        mReferenceNote.child(userId).push().setValue(new Note(idCategory, titleNote, contentTitle, backgroundNoteSelected, createAt, updateAt))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Utils.hideLoading();
                        // Write was successful!
                        Toast.makeText(CreateNoteActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Utils.hideLoading();
                        // Write failed
                        Utils.showToast(CreateNoteActivity.this, R.string.database_failed);
                    }
                });
    }

    @Override
    public void onItemClick(int color) {
        backgroundNoteSelected = color;
        Utils.setBackgroundRectangle(mConstraintLayout, backgroundNoteSelected);
    }

    /**
     * @param title   the title entered by the user
     * @param content the content entered by the user
     * @return is title and content is empty or not
     */
    private boolean validation(String title, String content) {
        boolean validate = true;
        if (title.isEmpty()) {
            validate = false;
            mEtTitleNote.setError(getString(R.string.empty_filed));
        }
        if (content.isEmpty()) {
            validate = false;
            mEtContentNote.setError(getString(R.string.empty_filed));
        }
        return validate;
    }

    public void closeActivity(View view) {
        finish();
    }

}
