package com.rmcgouran.mooddiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.rmcgouran.mooddiary.adapters.ViewPagerAdapter;
import com.rmcgouran.mooddiary.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private TabItem tab_diary;
    private TabItem tab_about_me;
    private FloatingActionButton fab;
    private ViewPager viewPager;
    private TabLayout tabs;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        fab = findViewById(R.id.fab);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(dashboardFragment.getInstance(), "STATS");
        viewPagerAdapter.addFragment(voiceFragment.getInstance(), "Voice Notes");
        viewPagerAdapter.addFragment(notesFragment.getInstance(), "Notes");

        viewPager.setAdapter(viewPagerAdapter);
        tabs.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    fab.setImageResource(R.drawable.plus);
                    fab.show();
                } else {
                    fab.hide();
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    public void nextPage(View view) {
        Intent intent = new Intent(this, moodActivity.class);
        startActivity(intent);
    }

    public void settingsPage(View view) {
        Intent intent = new Intent(this, settingsActivity.class);
        startActivity(intent);
    }
}
