package com.rmcgouran.mooddiary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class createMoodEntry extends AppCompatActivity {
    ImageButton veryHappyBtn;
    ImageButton happyBtn;
    ImageButton ehBtn;
    ImageButton sadBtn;
    ImageButton angryBtn;
    private int veryHappyTimes;
    private int happyTimes;
    private int okayTimes;
    private int sadTimes;
    private int angryTimes;
    private MaterialCardView veryHappyCard;
    private MaterialCardView happyCard;
    private MaterialCardView okayCard;
    private MaterialCardView sadCard;
    private MaterialCardView angryCard;
    private LinearLayout pageBG;
    private MaterialCardView titleCard;
    private TextView moodDes;
    private Button skipBtn;
    private LinearLayout veryHappyBG;
    private LinearLayout happyBG;
    private LinearLayout okayBG;
    private LinearLayout sadBG;
    private LinearLayout angryBG;
    private LinearLayout linearLay;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_a_mood);

        veryHappyBtn = findViewById(R.id.veryHappy_btn);
        happyBtn = findViewById(R.id.happy_btn);
        ehBtn = findViewById(R.id.eh_btn);
        sadBtn = findViewById(R.id.sad_btn);
        angryBtn = findViewById(R.id.angry_btn);

//initializing UI elements
        veryHappyCard = findViewById(R.id.homeveryHappyCard);
        happyCard = findViewById(R.id.hookayappyCard);
        okayCard = findViewById(R.id.homeokayCard);
        sadCard = findViewById(R.id.homeSadCard);
        angryCard = findViewById(R.id.homeAngryCard);
        pageBG = findViewById(R.id.full_background);
        titleCard = findViewById(R.id.homeTitleCard);
        titleCard.setVisibility(View.VISIBLE);
        moodDes = findViewById(R.id.mood_des);
        skipBtn = findViewById(R.id.skip_btn);
        veryHappyBG = findViewById(R.id.veryHappy_layout);
        happyBG = findViewById(R.id.happy_layout);
        okayBG = findViewById(R.id.okay_layout);
        sadBG = findViewById(R.id.sad_layout);
        angryBG = findViewById(R.id.angry_layout);

        //veryHappy Button push and dialog buttons


        veryHappyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                veryHappyTimes+=1;
                SharedPreferences prefs = getSharedPreferences("MY_PREFERENCES", Context.MODE_PRIVATE);
                int veryHappyCurrentValue = prefs.getInt("veryHappy", 0); // get existing value
                int veryHappyNewValue = veryHappyCurrentValue + (veryHappyTimes - (veryHappyTimes-1)); //add it to session value
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("Very Happy", veryHappyNewValue); // save updated value
                editor.apply();
                //The rest
                pageBG.setBackgroundColor(getResources().getColor(R.color.veryHappy));
                veryHappyCard.setClickable(false);
                happyCard.setClickable(false);
                okayCard.setClickable(false);
                sadCard.setClickable(false);
                angryCard.setClickable(false);
                titleCard.setVisibility(View.INVISIBLE);
                moodDes.setText(getResources().getString(R.string.veryHappy_text));



                veryHappyBG.setBackgroundColor(getResources().getColor(R.color.veryHappy));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    veryHappyBtn.setImageTintList(ColorStateList.valueOf(Color.WHITE));
                }

                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1.5f
                );
                veryHappyCard.setLayoutParams(param);
                happyBG.setBackgroundColor(getResources().getColor(R.color.veryHappy));
                okayBG.setBackgroundColor(getResources().getColor(R.color.veryHappy));
                sadBG.setBackgroundColor(getResources().getColor(R.color.veryHappy));
                angryBG.setBackgroundColor(getResources().getColor(R.color.veryHappy));
            }
        });

//Happy button push

        happyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                happyTimes+=1;
                SharedPreferences prefs = getSharedPreferences("MY_PREFERENCES", Context.MODE_PRIVATE);
                int happyCurrentValue = prefs.getInt("Happy", 0); // get existing value
                int happyNewValue = happyCurrentValue + (happyTimes - (happyTimes-1)); //add it to session value
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("Happy", happyNewValue); // save updated value
                editor.apply();

                //The rest
                pageBG.setBackgroundColor(getResources().getColor(R.color.happy));
                veryHappyCard.setClickable(false);
                happyCard.setClickable(false);
                okayCard.setClickable(false);
                sadCard.setClickable(false);
                angryCard.setClickable(false);
                titleCard.setVisibility(View.INVISIBLE);
                moodDes.setText(getResources().getString(R.string.happy_text));


                happyBG.setBackgroundColor(getResources().getColor(R.color.happy));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    happyBtn.setImageTintList(ColorStateList.valueOf(Color.WHITE));
                }

                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1.5f
                );
                happyCard.setLayoutParams(param);
                veryHappyBG.setBackgroundColor(getResources().getColor(R.color.happy));
                okayBG.setBackgroundColor(getResources().getColor(R.color.happy));
                sadBG.setBackgroundColor(getResources().getColor(R.color.happy));
                angryBG.setBackgroundColor(getResources().getColor(R.color.happy));
            }
        });

//okay button press

        okayCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okayTimes+=1;
                SharedPreferences prefs = getSharedPreferences("MY_PREFERENCES", Context.MODE_PRIVATE);
                int okayCurrentValue = prefs.getInt("okay", 0); // get existing value
                int okayNewValue = okayCurrentValue + (okayTimes - (okayTimes-1)); //add it to session value
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("okay", okayNewValue); // save updated value
                editor.apply();


                //The rest
                pageBG.setBackgroundColor(getResources().getColor(R.color.okay));
                veryHappyCard.setClickable(false);
                happyCard.setClickable(false);
                okayCard.setClickable(false);
                sadCard.setClickable(false);
                angryCard.setClickable(false);
                titleCard.setVisibility(View.INVISIBLE);
                moodDes.setText(getResources().getString(R.string.okay_text));


                okayBG.setBackgroundColor(getResources().getColor(R.color.okay));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ehBtn.setImageTintList(ColorStateList.valueOf(Color.WHITE));
                }

                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1.5f
                );
                okayCard.setLayoutParams(param);
                veryHappyBG.setBackgroundColor(getResources().getColor(R.color.okay));
                happyBG.setBackgroundColor(getResources().getColor(R.color.okay));
                sadBG.setBackgroundColor(getResources().getColor(R.color.okay));
                angryBG.setBackgroundColor(getResources().getColor(R.color.okay));

            }
        });

//Sad Button Press

        sadCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sadTimes+=1;
                SharedPreferences prefs = getSharedPreferences("MY_PREFERENCES", Context.MODE_PRIVATE);
                int sadCurrentValue = prefs.getInt("Sad", 0); // get existing value
                int sadNewValue = sadCurrentValue + (sadTimes - (sadTimes-1)); //add it to session value
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("Sad", sadNewValue); // save updated value
                editor.apply();


                //The rest
                pageBG.setBackgroundColor(getResources().getColor(R.color.sad));
                veryHappyCard.setClickable(false);
                happyCard.setClickable(false);
                okayCard.setClickable(false);
                sadCard.setClickable(false);
                angryCard.setClickable(false);
                titleCard.setVisibility(View.INVISIBLE);
                moodDes.setText(getResources().getString(R.string.sad_text));


                sadBG.setBackgroundColor(getResources().getColor(R.color.sad));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    sadBtn.setImageTintList(ColorStateList.valueOf(Color.WHITE));
                }

                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1.5f
                );
                sadCard.setLayoutParams(param);
                veryHappyBG.setBackgroundColor(getResources().getColor(R.color.sad));
                happyBG.setBackgroundColor(getResources().getColor(R.color.sad));
                okayBG.setBackgroundColor(getResources().getColor(R.color.sad));
                angryBG.setBackgroundColor(getResources().getColor(R.color.sad));
            }
        });

//Angry Button Press

        angryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                angryTimes+=1;
                SharedPreferences prefs = getSharedPreferences("MY_PREFERENCES", Context.MODE_PRIVATE);
                int angryCurrentValue = prefs.getInt("Angry", 0); // get existing value
                int angryNewValue = angryCurrentValue + (angryTimes - (angryTimes-1)); //add it to session value
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("Angry", angryNewValue); // save updated value
                editor.apply();

                //The rest
                pageBG.setBackgroundColor(getResources().getColor(R.color.angry));
                veryHappyCard.setClickable(false);
                happyCard.setClickable(false);
                okayCard.setClickable(false);
                sadCard.setClickable(false);
                angryCard.setClickable(false);
                titleCard.setVisibility(View.INVISIBLE);
                moodDes.setText(getResources().getString(R.string.angry_text));


                angryBG.setBackgroundColor(getResources().getColor(R.color.angry));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    angryBtn.setImageTintList(ColorStateList.valueOf(Color.WHITE));
                }

                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1.5f
                );
                angryCard.setLayoutParams(param);
                veryHappyBG.setBackgroundColor(getResources().getColor(R.color.angry));
                happyBG.setBackgroundColor(getResources().getColor(R.color.angry));
                okayBG.setBackgroundColor(getResources().getColor(R.color.angry));
                sadBG.setBackgroundColor(getResources().getColor(R.color.angry));

            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(createMoodEntry.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }//END OF ONCREATE




}

