package com.rmcgouran.mooddiary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class recordMoodActivity extends Fragment {

    private static final String MoodParam0 = "MoodParam0";
    private static final String MoodMoodParam0 = "MoodParam1";

    private String moodParam0;
    private String moodParam1;

    public recordMoodActivity() {}

    public static recordMoodActivity newInstance(String MoodParam0, String MoodParam1) {
        recordMoodActivity fragment = new recordMoodActivity();
        Bundle args = new Bundle();
        args.putString(MoodParam0, MoodParam0);
        args.putString(MoodMoodParam0, MoodParam1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            moodParam0 = getArguments().getString(MoodParam0);
            moodParam1 = getArguments().getString(MoodMoodParam0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_record, container, false);
    }
}
