package com.rmcgouran.mooddiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class homeActivity extends AppCompatActivity {
    private Button tomood;
    private Button toRecord;
    private Button toTracker;
    private Button toDiary;
    private Button toSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content view to the home_page layout
        setContentView(R.layout.home_page);

        // Get any intent that may have been passed to this activity
        Intent intent = getIntent();

        // Find the views for each button on the home page
        tomood = findViewById(R.id.home_btn_1);
        toRecord = findViewById(R.id.home_btn_2);
        toTracker = findViewById(R.id.home_btn_4);
        toDiary = findViewById(R.id.home_btn_3);
        toSettings = findViewById(R.id.home_btn_5);

        // Set the onClickListeners for each button on the home page
        tomood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the moodActivity
                Intent intent =new Intent(homeActivity.this, moodActivity.class);

                // Start the activity
                startActivity(intent);
            }
        });

        toRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the createMoodEntry activity
                Intent intent =new Intent(homeActivity.this, createMoodEntry.class);

                // Start the activity
                startActivity(intent);
            }
        });

        toDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the MainActivity (which is presumably the diary)
                Intent intent =new Intent(homeActivity.this, MainActivity.class);

                // Start the activity
                startActivity(intent);
            }
        });

        toTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the emotionDiary activity
                Intent intent =new Intent(homeActivity.this, emotionDiary.class);

                // Start the activity
                startActivity(intent);
            }
        });

        toSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to open the settingsActivity
                Intent intent =new Intent(homeActivity.this, settingsActivity.class);

                // Start the activity
                startActivity(intent);
            }
        });
    }
}
