package com.rmcgouran.mooddiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class moodActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private EditText inputFeelings;
    private EditText inputTitle;
    private static final String[] paths = {"Text", "Voice"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mood);

        inputFeelings = findViewById(R.id.inputFeelings);
        Spinner choosemoodSpinner = findViewById(R.id.mood_spinner);
        inputTitle = findViewById(R.id.input_title);

        getIntent();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(moodActivity.this,
                R.layout.simple_spinner_item_custom, paths);

        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_custom);
        choosemoodSpinner.setAdapter(adapter);
        choosemoodSpinner.setOnItemSelectedListener(this);
    }

    // Implements the spinner's click event listener
    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        if (position == 0) {
            Toast.makeText(moodActivity.this, "You are currently on the Text Page", Toast.LENGTH_SHORT).show();
        } else if (position == 1) {
            Intent intent = new Intent(this, voicePage.class);
            startActivity(intent);
        }
    }

    // Implements the spinner's no selection event listener
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public void save2(View v) {
        String textFile = inputTitle.getText().toString();
        String text = inputFeelings.getText().toString();

        if (inputTitle.getText().toString().equals("")) {
            Toast.makeText(this, "You have to write a title for your entry to be saved.", Toast.LENGTH_LONG).show();
            new AlertDialog.Builder(moodActivity.this)
                    .setTitle("Title")
                    .setMessage("Please enter a title for your text entry.")
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            try {
                inputTitle.getText().clear();
                inputFeelings.getText().clear();
                Toast.makeText(this, "Saved to " + getFilesDir() + "/TextEntries/" + textFile, Toast.LENGTH_LONG).show();
                String rootpath = getApplication().getFilesDir().getAbsolutePath() + "/TextEntries/";
                File root1 = new File(rootpath);
                if (!root1.exists()) {
                    root1.mkdirs();
                }
                FileWriter out = new FileWriter(new File(rootpath, textFile));
                out.write(text);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Navigates to the Main Activity screen
    public void MainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Implements the view click event listener
    @Override
    public void onClick(View v) {}
}
