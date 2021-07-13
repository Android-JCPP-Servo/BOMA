package com.team02.boma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class BMIChartAndTipsActivity extends AppCompatActivity implements MVPListener{

    // This Activity requires data from the Presenter,
    // so this variable is initialized to access such data.
    MVPPresenter presenter;

    // Stores the profile name passed from the activity that called this activity
    String profileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmichart_and_tips);

        // Set presenter if it is null
        if (presenter == null) {
            presenter = new MVPPresenter(this.getApplication());
        }

        // Get the intent that started this activity
        Intent intent = getIntent();

        // Get the profile name that was passed to this intent
        profileName = intent.getStringExtra(MVPView.EXTRA_MESSAGE_PROFILE_NAME);

        // request the profile information from the MVP
        //  the result is passed to the ProfileDataListener() as a callback
        presenter.view.RequestProfileData(this, profileName);

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