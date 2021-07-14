package com.team02.boma;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProgressGraphActivityTest extends AppCompatActivity implements MVPListener {

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

        graphDates = new ArrayList<>();

        // Initialize our Graph
        graphView = findViewById(R.id.bmiGraph);

        // Set title for Graph
        graphView.setTitle("BMI Progress Graph");

        // Set title text color
        graphView.setTitleColor(getColor(R.color.red_button));

        // Set title font size
        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        switch(screenSize) {
            // If screen size is X-Large, set text size to 40dp
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                graphView.setTitleTextSize(80);
                break;
            // If screen size is Large, set text size to 30dp
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                graphView.setTitleTextSize(70);
                break;
            // If screen size is Normal (Medium), set text size to 20dp
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                graphView.setTitleTextSize(60);
                break;
            // If screen size is Small, set text size to 10dp
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                graphView.setTitleTextSize(50);
                break;
            // No screen size was determined,
            //  so default text size is 25dp
            default:
                graphView.setTitleTextSize(45);
        }

        // Edit X- and Y-Axis Data and Intervals

        // Set Y-Axis
        graphView.getViewport().setMinY(10);
        graphView.getViewport().setMaxY(80);

        // Set X-Axis
    //    graphView.getViewport().setMinX(0);
    //    graphView.getViewport().setMaxX(6);
    //    graphView.getGridLabelRenderer().setNumHorizontalLabels(7);
    //    graphView.getViewport().setXAxisBoundsManual(true);

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
                        // the index is out of bounds return an empty string
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

        // clear the graphDates before adding to the list
        graphDates.clear();

        // Add data to our Graph
        // Display series data and points
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

        // go through all the BMIDataChunk entries
        for (BMIDataChunk data: ProfileData.data) {
            graphDates.add(x, data.day);
            y = Float.parseFloat(String.valueOf(data.bmi));
            series.appendData(new DataPoint(x, y), true, 100);
            x++;
        }

        // update the graph
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(10);
        series.setThickness(8);
        series.setColor(getColor(R.color.red_button));
        graphView.addSeries(series);
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