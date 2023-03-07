package com.rmcgouran.mooddiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class notesActivity extends AppCompatActivity {
    private ImageButton returnToNotes;
    private TextView readText;
    private Button loadNotes;
    private String readFile;
    private String fileText;
    private static final String FILE_NAME = "moodDiary.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_entry);
        Intent intent = getIntent();
        fileText = intent.getStringExtra(Intent.EXTRA_TEXT);

        // Initialise UI elements
        returnToNotes = findViewById(R.id.back_to_diary);
        readText = findViewById(R.id.read_text);
        loadNotes = findViewById(R.id.load_diary_entry);
    }

    public void load(View view) {
        FileInputStream fis = null;
        try {
            fis = openFileInput(fileText);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            readText.setText(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void backToTextList(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
