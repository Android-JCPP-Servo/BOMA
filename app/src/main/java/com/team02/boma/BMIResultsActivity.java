package com.team02.boma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

/***************************
 * NOTES/STEPS TO COMPLETION:
 * -Connect SaveNewInfoActivity to this Activity
 * -Connect UserBMIData to this Activity
 * -Connect bmi_results.xml to this Activity
 ***************************/

public class BMIResultsActivity extends AppCompatActivity implements MVPListener {

    // This Activity requires data from the Presenter,
    // so this variable is initialized to access such data.
    MVPPresenter presenter;

    // Stores the profile name passed from the activity that called this activity
    String profileName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmi_results);

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
    }

    // Function for viewing user profile in Welcome Back! page
    public void viewUserProfile(View view) { presenter.view.ShowWelcomeBackActivity(); }
    // Function for return to BOMA! homepage
    public void goToHome(View view) { presenter.view.ShowMainActivity(); }

    @Override
    public void ProfileNamesListener(List<String> profileNames) {

    }

    @Override
    public void ProfileDataListener(BMIProfile ProfileData) {

        // Since the message is in a TextView, run it on the UiThread
        // TODO: format the number to limit decimal places
        runOnUiThread(() -> {
            TextView textViewBMI = findViewById(R.id.textViewBMI);
            textViewBMI.setText(Float.toString(ProfileData.lastBMI));
        });

    }

    @Override
    public void UserBMIListener(UserBMIData UserData) {

    }

    @Override
    public void ProfileCreatedListener(boolean Success) {

    }
}
