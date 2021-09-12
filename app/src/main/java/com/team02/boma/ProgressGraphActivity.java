package com.team02.boma;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProgressGraphActivity extends AppCompatActivity implements MVPListener {

    // Every activity that needs access to the MVP structure needs a reference
    // to the global MVPPresenter
    MVPPresenter presenter;

    // Create a variable for our Graph
    GraphView graphView;

    // Store a array of Dates for the graph
    ArrayList<Date> graphDates;

    // Call data from calculator
    String profileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_graph);

        // Set presenter if it is null
        if (presenter == null) {
            presenter = new MVPPresenter(this.getApplication());
        }

        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#BD0101"));

        // Set BackgroundDrawable
        assert actionBar != null;
        actionBar.setBackgroundDrawable(colorDrawable);

        // Define and Design Status Bar
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.status_bar));

        graphDates = new ArrayList<>();

        // Initialize our Graph
        graphView = findViewById(R.id.bmiGraph);

        // Set title for Graph
        graphView.setTitle("BMI Progress Graph");

        // Set title text color
        graphView.setTitleColor(getColor(R.color.red_button));

        // Drawing Graph
        graphView.getGridLabelRenderer().setGridColor(Color.BLACK);
        graphView.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.BLACK);
        graphView.getGridLabelRenderer().setHorizontalLabelsColor(Color.BLACK);
        int screenSize = getResources().getConfiguration().screenLayout &Configuration.SCREENLAYOUT_SIZE_MASK;
        switch(screenSize) {
            // If screen size is X-Large, set text size to 40dp
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                graphView.getGridLabelRenderer().setLabelHorizontalHeight(100);
                break;
            // If screen size is Large, set text size to 30dp
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                graphView.getGridLabelRenderer().setLabelHorizontalHeight(200);
                break;
            // If screen size is Normal (Medium), set text size to 20dp
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                graphView.getGridLabelRenderer().setLabelHorizontalHeight(300);
                break;
            // If screen size is Small, set text size to 10dp
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                graphView.getGridLabelRenderer().setLabelHorizontalHeight(400);
                break;
            // No screen size was determined,
            //  so default text size is 25dp
            default:
                graphView.getGridLabelRenderer().setLabelHorizontalHeight(150);
        }
        graphView.getGridLabelRenderer().setHorizontalLabelsAngle(45);
        graphView.getGridLabelRenderer().setVerticalAxisTitleColor(Color.BLACK);
        graphView.getGridLabelRenderer().setVerticalLabelsColor(Color.BLACK);
        // Setting maximum number of x-axis grid lines manually
        // Referenced from: https://www.youtube.com/watch?v=Lnm6YG8Ub50
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(31);
        // Ability to view from Month View to Year View
        // Referenced from: https://stackoverflow.com/questions/66385014/how-to-fix-the-limit-of-x-axis-with-graphview
        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScrollable(true);

        // Edit X- and Y-Axis Data and Intervals

        // Set Y-Axis
        graphView.getViewport().setMinY(10);
        graphView.getViewport().setMaxY(80);

        // Set X-Axis
        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                String output;
                if (isValueX) {
                    try {
                        Date date = graphDates.get((int)value);
                        //noinspection deprecation
                        output = String.format(Locale.getDefault(), " %d/%d ", date.getMonth() +1, date.getDate());
                    }catch (IndexOutOfBoundsException e){
                        // The index is out of bounds return an empty string
                        output = "";
                    }
                    return output;

                } else {
                    return super.formatLabel(value, false);
                }

            }
        });

        // Get the intent that started this activity
        Intent intent = getIntent();

        // Get the profile name that was passed to this intent
        this.profileName = intent.getStringExtra(MVPView.EXTRA_MESSAGE_PROFILE_NAME);

        // Call data from presenter
        presenter.view.RequestProfileData(this, profileName);
    }

    @Override
    public void ProfileNamesListener(List<String> profileNames) {

    }

    @Override
    public void ProfileDataListener(BMIProfile ProfileData) {

        // Initialize X and Y Axes values
        int x = 0;
        float y;
        float yGoal;

        // clear the graphDates before adding to the list
        graphDates.clear();

        // Add data to our Graph
        // Display series data and points
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        LineGraphSeries<DataPoint> goalSeries = new LineGraphSeries<>();

        // go through all the BMIDataChunk entries
        for (BMIDataChunk data: ProfileData.data) {
            graphDates.add(x, data.day);
            y = Float.parseFloat(String.valueOf(data.bmi));
            yGoal = Float.parseFloat(String.valueOf(data.goalBmi));
            series.appendData(new DataPoint(x, y), true, 100);
            goalSeries.appendData(new DataPoint(x, yGoal), true, 100);
            x++;
        }

        // update the graph
        // Current User BMI Series
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);
        series.setColor(getColor(R.color.red_button));
        series.setTitle("Weight");
        // USer Goal BMI Series
        goalSeries.setDrawDataPoints(true);
        goalSeries.setDataPointsRadius(10);
        goalSeries.setThickness(8);
        goalSeries.setColor(getColor(android.R.color.holo_blue_dark));
        goalSeries.setBackgroundColor(Color.argb(80, 51, 182, 229));
        goalSeries.setDrawBackground(true);
        goalSeries.setTitle("Goal Weight");
        graphView.addSeries(series);
        graphView.addSeries(goalSeries);
        graphView.getLegendRenderer().setVisible(true);
        graphView.getLegendRenderer().setTextColor(Color.BLACK);
        graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graphView.getLegendRenderer().setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public void UserBMIListener(UserBMIData UserData) {

    }

    @Override
    public void ProfileCreatedListener(boolean Success) {

    }

    @Override
    public void ProfileDeletedListener(boolean Success) {

    }
}