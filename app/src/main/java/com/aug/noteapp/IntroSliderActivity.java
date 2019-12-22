package com.aug.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import android.widget.TextView;

public class IntroSliderActivity extends AppCompatActivity {
    private StorageSharedPreferences mStorageSharedPreferences;
    private int[] layouts;
    private ViewPager mViewPager;
    private Button mBtnNext;
    private TextView mTvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checking for first time launch - before calling setContentView()
        mStorageSharedPreferences = new StorageSharedPreferences(this);
        if (!mStorageSharedPreferences.isFirstTimeLaunch()) {
            launchSplashActivity();
            finish();
        }
        // Making status bar transparent
        changeStatusBarColor();

        setContentView(R.layout.activity_intro_slider);
        init();

        mViewPager.setAdapter(new IntroSliderViewPagerAdapter(this, layouts));
        mTvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSplashActivity();
            }
        });

        mBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page splash activity will be launched
                int current = mViewPager.getCurrentItem() + 1;
                if (current < layouts.length) {
                    // move to next screen
                    mViewPager.setCurrentItem(current);
                } else {
                    launchSplashActivity();
                }
            }
        });


    }

    private void init() {
        mViewPager = findViewById(R.id.vp_intro_slider_activity);
        mBtnNext = findViewById(R.id.btn_next_intro_slider_activity);
        mTvSkip = findViewById(R.id.tv_skip_intro_slider_activity);
        // layouts of all intro sliders
        layouts = new int[]{
                R.layout.first_layout_slider,
                R.layout.second_layout_slider};
        //add dots indicator to view pager
        TabLayout tabLayout = findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(mViewPager, true);
    }

    private void launchSplashActivity() {
        mStorageSharedPreferences.setFirstTimeLaunch(false);
        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }

    /**
     * Making status bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
