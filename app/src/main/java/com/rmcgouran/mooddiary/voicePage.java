package com.rmcgouran.mooddiary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class voicePage extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{
    private ImageButton recordBtn;
    private Chronometer timer;
    private TextView filenameText;
    private Spinner chooseMoodSpinner2;
    private EditText inputTitle;
    private static final String[] paths = {"Voice", "Text"};
    private Boolean isRecording = false;
    private String recordPermission = Manifest.permission.RECORD_AUDIO;
    private int PERMISSION_CODE = 21;
    private MediaRecorder mediaRecorder;
    private String recordFile;
    private String recordPath;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mood_voice);


        // Initialising
        recordBtn = (ImageButton) findViewById(R.id.record_btn);
        timer = (Chronometer) findViewById(R.id.record_timer);
        filenameText = (TextView) findViewById(R.id.record_filename);
        chooseMoodSpinner2 = (Spinner) findViewById(R.id.mood_spinner_voice);
        inputTitle = (EditText) findViewById(R.id.input_title_voice);

        recordBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.record_btn:
                        if (isRecording) {
                            //Stop
                            stopRecording();
                            recordBtn.setImageResource(R.drawable.baseline_mic_off_black_48);
                            recordBtn.setBackgroundResource(R.drawable.round_button);
                            isRecording = false;
                        } else{
                            //Start
                            if (checkPermission()) {
                                startRecording();
                                recordBtn.setImageResource(R.drawable.baseline_mic_black_48);
                                recordBtn.setBackgroundResource(R.drawable.record_round_btn);
                                isRecording = true;
                            }
                        }
                        break;
                }
            }

        });


        Intent intent = getIntent();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(voicePage.this,
                R.layout.simple_spinner_item_custom, paths);

        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
        chooseMoodSpinner2.setAdapter(adapter);
        chooseMoodSpinner2.setOnItemSelectedListener(this);

    }


    // Stop Recording method
    private void stopRecording() {
        timer.stop();
        recordBtn.setBackgroundColor(getResources().getColor(R.color.stopRecording));

        inputTitle.getText().clear();
        filenameText.setText("Stopped Recording-File Saved : " + recordFile);

        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;

    }


    // Start Recording Method
    private void startRecording() {
        // Starting timer
        recordFile = inputTitle.getText().toString();
        if (TextUtils.isEmpty(recordFile)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_hh_mm", Locale.US);
            Date now = new Date();
            recordFile = "VD." + formatter.format(now) + ".3gp";
        }

        recordBtn.setBackgroundColor(getResources().getColor(R.color.startRecording));
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();

        // Setting file path and name
        recordPath = getApplication().getExternalFilesDir("/").getAbsolutePath();
        filenameText.setText("Recording-File Name : " + recordFile);

        // Starting media recorder
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(recordPath + "/" + recordFile);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSION_CODE);
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        // Empty method body
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            Toast.makeText(this, "You are currently on the voice Page", Toast.LENGTH_SHORT).show();
        } else if (position == 1) {
            Intent intent = new Intent(this, moodActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Empty method body
    }

    public void MainActivity2(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
