package com.rmcgouran.mooddiary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class splashActivity extends AppCompatActivity {

    private Animation topAnim, bottomAnim;
    private ImageView image;
    private TextView logo, slogan;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        logo = findViewById(R.id.splash_title);

        logo.setAnimation(bottomAnim);

        int SPLASH_SCREEN = 3400;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefs = getSharedPreferences("PASS_PREF", Context.MODE_PRIVATE);
                String thePIN = prefs.getString("Digits", "");
                if (!thePIN.equals("")) {
                    Intent intent = new Intent(splashActivity.this, signInActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(splashActivity.this, createMoodEntry.class);
                    startActivity(intent);
                }
                finish();
            }
        }, SPLASH_SCREEN);
    }
}
