package com.aug.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotesCategoryActivity extends AppCompatActivity {
    private static final String TAG = "NotesCategoryActivity";
    public static final String KEY_ID_CATEGORY = "key_id_category";
    public static final String KEY_COLOR_CATEGORY = "key_color_category";
    private String idCategory = null;
    private int bgCategory;
    private View mViewColor;
    private DatabaseReference mReferenceNote;
    private FirebaseAuth mAuth;
    private RecyclerView mRvNotes;
    private EditText mEtSearch;
    private ImageView mImgEmptyNote;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mFbAddNewNote;
    private NoteWithFilterAdapter mNoteWithFilterAdapter;
    ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_category);
        init();
        if (getIntent() != null) {
            if (getIntent().hasExtra(KEY_ID_CATEGORY)) {
                idCategory = getIntent().getStringExtra(KEY_ID_CATEGORY);
                mFbAddNewNote.show();
            }
            bgCategory = getIntent().getIntExtra(KEY_COLOR_CATEGORY, ColorsBottomSheetDialogFragment.COLOR_PURPLE);
        }
        Utils.setBackgroundRectangle(mViewColor, bgCategory);

        mRvNotes.setLayoutManager(mLayoutManager);
        mRvNotes.setAdapter(mNoteWithFilterAdapter);
        String userId = mAuth.getUid();
        mReferenceNote.child(userId).addValueEventListener(getNotesFromDB());

        mEtSearch.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mNoteWithFilterAdapter.getFilter().filter(s);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                }
        );

    }


    private void init() {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Initialize Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mReferenceNote = database.getReference("Note");
        mFbAddNewNote = findViewById(R.id.fb_add_new_note);
        mViewColor = findViewById(R.id.view_color_notes_category_activity);
        mRvNotes = findViewById(R.id.rv_all_notes_category_activity);
        mEtSearch = findViewById(R.id.et_search_notes_category_activity);
        mImgEmptyNote = findViewById(R.id.img_empty_note_rv_notes_category_activity);
        mNoteWithFilterAdapter = new NoteWithFilterAdapter(this, new ArrayList<Note>());
        mLayoutManager = new LinearLayoutManager(this);

    }

    private ValueEventListener getNotesFromDB() {
        ValueEventListener notesListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                notes.clear();
                mNoteWithFilterAdapter.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String idNote = snapshot.getKey();
                    Note note = snapshot.getValue(Note.class);
                    note.setIdNote(idNote);
                    if (idCategory != null) {
                        if (note.getIdCategory().equals(idCategory))
                            notes.add(note);
                    } else {
                        notes.add(note);
                    }
                }
                showOrHideImgEmptyNotes();
                mNoteWithFilterAdapter.setNoteList(notes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Category failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        return notesListener;
    }

    private void showOrHideImgEmptyNotes() {
        if (notes.size() > 0) {
            mImgEmptyNote.setVisibility(View.GONE);
        } else {
            mImgEmptyNote.setVisibility(View.VISIBLE);
        }
    }

    public void openCreateNoteActivity(View view) {
        Intent intent = new Intent(this, CreateNoteActivity.class);
        intent.putExtra(CreateNoteActivity.KEY_ID_CATEGORY, idCategory);
        startActivity(intent);

    }
}
