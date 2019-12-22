package com.aug.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.InteractionCategoryListener {
    private static final String TAG = "MainActivity";
    private DatabaseReference mReferenceCategory;
    private DatabaseReference mReferenceNote;
    private FirebaseAuth mAuth;
    private RecyclerView mRvCategories;
    private RecyclerView mRvNotes;
    private RecyclerView.LayoutManager mLayoutManagerH;
    private RecyclerView.LayoutManager mLayoutManagerV;
    private CategoryAdapter mCategoryAdapter;
    private ImageView mImgEmptyNote;
    private NoteWithFilterAdapter mNoteWithFilterAdapter;
    ArrayList<Category> categories = new ArrayList<>();
    ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mRvCategories.setLayoutManager(mLayoutManagerH);
        mRvCategories.setAdapter(mCategoryAdapter);
        String userId = mAuth.getUid();
        mReferenceCategory.child(userId).addValueEventListener(getCategoriesFromDB());
        mRvNotes.setLayoutManager(mLayoutManagerV);
        mRvNotes.setAdapter(mNoteWithFilterAdapter);
        mReferenceNote.child(userId).addValueEventListener(getNotesFromDB());
    }

    private void init() {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Initialize Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mReferenceCategory = database.getReference("Category");
        mReferenceNote = database.getReference("Note");
        mRvCategories = findViewById(R.id.rv_categories_main_activity);
        mCategoryAdapter = new CategoryAdapter(this, new ArrayList<Category>(), this);
        mLayoutManagerH = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvNotes = findViewById(R.id.rv_notes_main_activity);
        mLayoutManagerV = new LinearLayoutManager(this);
        mNoteWithFilterAdapter = new NoteWithFilterAdapter(this, new ArrayList<Note>());

        mImgEmptyNote = findViewById(R.id.img_empty_note_rv_main_activity);
    }

    private ValueEventListener getCategoriesFromDB() {
        ValueEventListener categoryListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categories.clear();
                mCategoryAdapter.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String idCategory = snapshot.getKey();
                    Category category = snapshot.getValue(Category.class);
                    category.setIdCategory(idCategory);
                    categories.add(category);
                }
                mCategoryAdapter.setCategoryList(categories);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Category failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        return categoryListener;
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
                    notes.add(note);
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

    public void openCategoriesActivity(View view) {
        startActivity(new Intent(this, CategoriesActivity.class));
    }

    @Override
    public void openCreateCategoryActivity() {
        startActivity(new Intent(this, CreateCategoryActivity.class));
    }

    @Override
    public void openNotesCategory(String idCategory, int bgCategory) {
        Intent intent = new Intent(this, NotesCategoryActivity.class);
        intent.putExtra(NotesCategoryActivity.KEY_ID_CATEGORY, idCategory);
        intent.putExtra(NotesCategoryActivity.KEY_COLOR_CATEGORY, bgCategory);
        startActivity(intent);
    }

    public void openNotesCategoriesActivity(View view) {
        startActivity(new Intent(this, NotesCategoryActivity.class));
    }

    public void logout(View view) {
        mAuth.signOut();
        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }
}
