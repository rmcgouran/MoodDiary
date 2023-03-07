package com.rmcgouran.mooddiary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static android.content.ContentValues.TAG;

public class emotionDiary extends AppCompatActivity {
    //Pie chart stuff
    int ela;
    int div;
    int alim;
    int lux;
    int poup;
    private PieChart pieChart;
    private float[] yData;
    private String[] xData;
    int allMoods;

    private static final String MoodParam0 = "MoodParam0";
    private static final String MoodMoodParam0 = "MoodParam1";

    private GraphView graphView;
    private int veryHappyTimes;
    private int happyTimes;
    private int okayTimes;
    private int sadTimes;
    private int angryTimes;
    private int veryHappyNewValue;
    private int happyNewValue;
    private int okayNewValue;
    private int sadNewValue;
    private int angryNewValue;
    private ImageButton toHomeBtn;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mood_tracker);
        graphView = findViewById(R.id.graph_id);
        pieChart = findViewById(R.id.chart_id);
        SharedPreferences preferences = getSharedPreferences("MY_PREFERENCES", Context.MODE_PRIVATE);

        // Calculate the percentage of each mood category and store the values in yData, with the corresponding mood labels in xData.
        ela = preferences.getInt("Very Happy", veryHappyNewValue);
        div = preferences.getInt("Happy", happyNewValue);
        alim = preferences.getInt("Okay", okayNewValue);
        lux = preferences.getInt("Sad", sadNewValue);
        poup = preferences.getInt("Angry", angryNewValue);
        allMoods = ela+div+alim+lux+poup;
        float b = (div*100)/(float) allMoods;
        float c = (alim*100)/(float) allMoods;
        float d = (lux*100)/(float) allMoods;
        float e = (poup*100)/(float) allMoods;
        float a = (ela*100)/(float) allMoods;

        yData = new float[]{a,b,c,d,e};
        xData = new String[]{"Very Happy", "Happy", "Okay", "Sad", "Angry"};

        // Create bar graph series to represent each emotion value
        // The x-values of each series represent the corresponding emotion,
        // and the y-values represent the number of times that emotion has been selected
        BarGraphSeries series1 = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(1, preferences.getInt("Very Happy", veryHappyNewValue))
        });
        BarGraphSeries series2 = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(2, preferences.getInt("Happy", happyNewValue))
        });
        BarGraphSeries series3 = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(3, preferences.getInt("okay", okayNewValue))
        });
        BarGraphSeries series4 = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(4, preferences.getInt("Sad", sadNewValue))
        });
        BarGraphSeries series5 = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(5, preferences.getInt("Angry", angryNewValue))
        });

        BarGraphSeries series6 = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(0,0),
                new DataPoint(6, 0)
        });

        graphView.addSeries(series1);
        graphView.addSeries(series2);
        graphView.addSeries(series3);
        graphView.addSeries(series4);
        graphView.addSeries(series5);
        graphView.addSeries(series6);

        final int VERY_HAPPY_COLOR = Color.rgb(239,8,192);
        final int HAPPY_COLOR = Color.rgb(4,208,79);
        final int OKAY_COLOR = Color.rgb(188,111,9);
        final int SAD_COLOR = Color.rgb(51,127,242);
        final int ANGRY_COLOR = Color.rgb(251,0,17);

        // Set the styling properties for the graph view, including the color of the bars and grid lines, the label formatter, and the viewport bounds.
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphView);
        staticLabelsFormatter.setHorizontalLabels(new String[] {"","Very Happy", "Happy", "okay", "Sad", "Angry",""});
        graphView.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graphView.getGridLabelRenderer().setHighlightZeroLines(true);
        graphView.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graphView.getViewport().setBackgroundColor(Color.TRANSPARENT);
        graphView.getGridLabelRenderer().setGridColor(Color.WHITE);

        series1.setColor(VERY_HAPPY_COLOR);
        series1.setSpacing(5);
        series1.setAnimated(true);
        series2.setColor(HAPPY_COLOR);
        series2.setSpacing(5);
        series2.setAnimated(true);
        series3.setColor(OKAY_COLOR);
        series3.setSpacing(5);
        series3.setAnimated(true);
        series4.setColor(SAD_COLOR);
        series4.setSpacing(5);
        series4.setAnimated(true);
        series5.setColor(ANGRY_COLOR);
        series5.setSpacing(5);
        series5.setAnimated(true);
        series6.setSpacing(15);

        graphView.getGridLabelRenderer().setLabelsSpace(-1);
        graphView.getGraphContentHeight();
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        graphView.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);

        // Loop through the array of BarGraphSeries and set values on top properties.
        BarGraphSeries[] seriesArray = {series1, series2, series3, series4, series5};

        for (BarGraphSeries series : seriesArray) {
            series.setValuesOnTopSize(30);
            series.setDrawValuesOnTop(true);
            series.setValuesOnTopColor(Color.WHITE);
        }

        series6.setDrawValuesOnTop(false);

        // Set up and customize the pie chart
        pieChart.setRotationEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setCenterTextColor(Color.WHITE);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Mood Tracker" + "\n"+ "(In %)");
        pieChart.setCenterTextSize(10);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setBackgroundColor(Color.TRANSPARENT);

// Add the data set to the pie chart
        addDataSet();

// Set up the "back to home" button to navigate to homeActivity
        toHomeBtn = findViewById(R.id.to_home_button);
        toHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(emotionDiary.this, homeActivity.class);
                startActivity(intent);
            }
        });


    }


    private void addDataSet() {
        // Log message to show that the method has started
        Log.d(TAG, "addDataSet started");

        // Initialise array lists for pie chart data
        ArrayList<PieEntry> yEntries = new ArrayList<>();
        ArrayList<String> xEntries = new ArrayList<>();

        // Loop through yData array to add pie chart data
        for (int i = 0; i < yData.length; i++) {
            yEntries.add(new PieEntry(yData[i], i));
        }

        // Loop through xData array to add pie chart labels
        for (int i = 1; i < xData.length; i++) {
            xEntries.add((xData[i]));
        }

        // Create pie chart data set
        PieDataSet pieDataSet = new PieDataSet(yEntries, "");

        // Set spacing between pie chart slices and text size for values
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(14);

        // Add colors to pie chart slices
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(239, 8, 192));
        colors.add(Color.rgb(4, 208, 79));
        colors.add(Color.rgb(188, 111, 9));
        colors.add(Color.rgb(51, 127, 242));
        colors.add(Color.rgb(251, 0, 17));
        pieDataSet.setColors(colors);

        // Add legend to pie chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);

        // Create pie chart data object and set it to the pie chart
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);

        // Set pie chart to use percent values and redraw the chart
        pieChart.setUsePercentValues(true);
        pieChart.invalidate();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
