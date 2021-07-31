package com.team02.boma;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class BMIChartAndTipsActivity extends AppCompatActivity implements MVPListener{

    // This Activity requires data from the Presenter,
    // so this variable is initialized to access such data.
    MVPPresenter presenter;

    // Stores the profile name passed from the activity that called this activity
    String profileName;

    // Used to link the Chart image to a new Activity
    // Designed if the user presses on the image to see it larger
    ImageButton chartImage;

    @Override
    protected void onResume() {
        super.onResume();

        // Get a list of all the profile names
        //  The list is returned in a new thread to ProfileNamesListener()
        presenter.view.RequestProfileNames(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmichart_and_tips);

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

        // Get the intent that started this activity
        Intent intent = getIntent();

        // Get the profile name that was passed to this intent
        profileName = intent.getStringExtra(MVPView.EXTRA_MESSAGE_PROFILE_NAME);

        // request the profile information from the MVP
        //  the result is passed to the ProfileDataListener() as a callback
        presenter.view.RequestProfileData(this, profileName);

        // Implementing ImageButton capabilities for the Chart image
        chartImage = (ImageButton) findViewById(R.id.imageButton);

        chartImage.setOnClickListener((v) -> {
            Intent largerChartImage = new Intent(BMIChartAndTipsActivity.this, LargerImage.class);
            startActivity(largerChartImage);
        });

        // Call the Recommendations method
        recommendations();
    }

    //this is the look up table for recommendations
    public void recommendations(){
        TextView tvIdeas =  findViewById(R.id.ideas);

        // Get the array of Strings from the strings.xml file
        String[] recommendations = getResources().getStringArray(R.array.improvement_suggestions);

        // Initialize a Randomizer
        int randomNumber = new Random().nextInt(recommendations.length);

        // Set the TextView to the recommendation
        tvIdeas.setText(recommendations[randomNumber]);

    }

    @Override
    public void ProfileNamesListener(List<String> profileNames) {

        if(profileNames.get(0) == null){
            return;
        }

        // Get the profile name that was passed to this intent
        this.profileName = profileNames.get(0);

        // request the profile information from the MVP
        //  the result is passed to the ProfileDataListener() as a callback
        presenter.view.RequestProfileData(this, profileName);

    }

    @Override
    public void ProfileDataListener(BMIProfile ProfileData) {
        // Since the message is in a TextView, run it on the UiThread
        runOnUiThread(() -> {
            TextView referenceBMI = findViewById(R.id.referenceBMI);
            String userBMI = "Your BMI is: " + String.format(Locale.getDefault(), "%.1f", ProfileData.lastBMI);
            referenceBMI.setText(userBMI);
        });
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