package com.team02.boma;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;

public class ProgressGraphActivity extends AppCompatActivity implements MVPListener {

    // Every activity that needs access to the MVP structure needs a reference
    // to the global MVPPresenter
    MVPPresenter presenter;

    // Create a variable for our Graph
    GraphView graphView;

    // Call data from calculator
    String profileName;

    // Initialize X and Y Axes values
    int x = 0;
    float y = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_graph);

        // Set presenter if it is null
        if (presenter == null) {
            presenter = new MVPPresenter(this.getApplication());
        }

        // Initialize our Graph
        graphView = findViewById(R.id.bmiGraph);

        // Set title for Graph
        graphView.setTitle("BMI Progress Graph");

        // Set title text color
        graphView.setTitleColor(R.color.red_button);

        // Set title font size
        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        switch(screenSize) {
            // If screen size is X-Large, set text size to 40dp
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                graphView.setTitleTextSize(40);
                break;
            // If screen size is Large, set text size to 30dp
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                graphView.setTitleTextSize(30);
                break;
            // If screen size is Normal (Medium), set text size to 20dp
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                graphView.setTitleTextSize(20);
                break;
            // If screen size is Small, set text size to 10dp
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                graphView.setTitleTextSize(10);
                break;
            // No screen size was determined,
            //  so default text size is 25dp
            default:
                graphView.setTitleTextSize(25);
        }

        // Edit X- and Y-Axis Data and Intervals
        graphView.getGridLabelRenderer().setVerticalAxisTitle("BMI Progress");
        graphView.getGridLabelRenderer().setHorizontalAxisTitle("Date");
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(10);

        // Increment X-Axis by 1
        x += 1;

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

        // Add data to our Graph
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

        // go through all the BMIDataChunk entries
        for (BMIDataChunk data: ProfileData.data) {
            y = Float.parseFloat(String.valueOf(data.bmi));
            series.appendData(new DataPoint(x, y), true, 100);
            this.x++;
        }

        // update the graph
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