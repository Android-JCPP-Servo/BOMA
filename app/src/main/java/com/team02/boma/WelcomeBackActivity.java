package com.team02.boma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class WelcomeBackActivity extends AppCompatActivity implements MVPListener {

    // Every activity that needs access to the MVP structure needs a reference
    // to the global MVPPresenter
    MVPPresenter presenter;
    String profileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_back);

        // set presenter if it is null
        if (presenter == null){
            presenter = new MVPPresenter(this.getApplication());
        }

        // Get the Intent that started this Activity
        Intent intent = getIntent();

        // Get the ProfileName that was passed to this Activity
        profileName = intent.getStringExtra(MVPView.EXTRA_MESSAGE_PROFILE_NAME);

        // Request the profile information from the MVP
        //  The result is passed to the ProfileDataListener() as a callback
        presenter.view.RequestProfileData(this, presenter.model.bmiManager.allProfiles.LastLoadedProfile);
    }

    @Override
    public void ProfileNamesListener(List<String> profileNames) {

    }

    @Override
    public void ProfileDataListener(BMIProfile ProfileData) {
        // Since the message is in a TextView, run it on the UiThread
        runOnUiThread(() -> {
            // Display User's Name
            TextView displayName = findViewById(R.id.displayName);
            displayName.setText(ProfileData.name);
            // Display User's Gender
            TextView displayGender = findViewById(R.id.displayGender);
            displayGender.setText(ProfileData.gender);
            // Display User's Height
            TextView displayHeight = findViewById(R.id.displayHeight);
            displayHeight.setText(Float.toString(ProfileData.lastHeight));
            // Display User's Weight
            TextView displayWeight = findViewById(R.id.displayWeight);
            displayWeight.setText(Float.toString(ProfileData.lastWeight));
            // Display User's Age
            TextView displayAge = findViewById(R.id.displayAge);
            displayAge.setText(Integer.toString(ProfileData.age));
        });
    }

    @Override
    public void UserBMIListener(UserBMIData UserData) {


    }

    @Override
    public void ProfileCreatedListener(boolean Success) {

    }

    public void updateUserProfile(View view) {
        presenter.view.ShowUpdateProfileActivity(profileName);
    }
}