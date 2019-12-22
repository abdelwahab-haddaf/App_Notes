package com.aug.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity implements CategoryWithFilterAdapter.InteractionCategoryListener {
    private static final String TAG = "CategoriesActivity";
    private DatabaseReference mReferenceCategory;
    private FirebaseAuth mAuth;
    private RecyclerView mRvCategories;
    private EditText mEtSearch;
    private RecyclerView.LayoutManager mLayoutManager;
    private CategoryWithFilterAdapter mCategoryWithFilterAdapter;
    ArrayList<Category> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        init();
        mRvCategories.setLayoutManager(mLayoutManager);
        mRvCategories.setAdapter(mCategoryWithFilterAdapter);
        String userId = mAuth.getUid();
        mReferenceCategory.child(userId).addValueEventListener(getCategoriesFromDB());

        mEtSearch.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        mCategoryWithFilterAdapter.getFilter().filter(s);
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
        mReferenceCategory = database.getReference("Category");

        mRvCategories = findViewById(R.id.rv_all_category_categories_activity);
        mEtSearch = findViewById(R.id.et_search_categories_activity);
        mCategoryWithFilterAdapter = new CategoryWithFilterAdapter(this, new ArrayList<Category>(), this);
        mLayoutManager = new GridLayoutManager(this, 3);
    }

    private ValueEventListener getCategoriesFromDB() {
        ValueEventListener categoryListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categories.clear();
                mCategoryWithFilterAdapter.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String idCategory = snapshot.getKey();
                    Category category = snapshot.getValue(Category.class);
                    category.setIdCategory(idCategory);
                    categories.add(category);
                }
                mCategoryWithFilterAdapter.setCategoryList(categories);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Category failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        return categoryListener;
    }

    public void closeActivity(View view) {
        finish();
    }

    public void openCreateCategoryActivity(View view) {
        startActivity(new Intent(this, CreateCategoryActivity.class));
    }

    @Override
    public void openNotesCategory(String idCategory, int bgCategory) {
        Intent intent = new Intent(this, NotesCategoryActivity.class);
        intent.putExtra(NotesCategoryActivity.KEY_ID_CATEGORY, idCategory);
        intent.putExtra(NotesCategoryActivity.KEY_COLOR_CATEGORY, bgCategory);
        startActivity(intent);
    }
}
