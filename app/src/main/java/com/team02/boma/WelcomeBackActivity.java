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
        presenter.view.RequestProfileData(this, profileName);
    }

    @Override
    public void ProfileNamesListener(List<String> profileNames) {

    }

    @Override
    public void ProfileDataListener(BMIProfile ProfileData) {
        // Since the message is in a TextView, run it on the UiThread
        runOnUiThread(() -> {
            TextView displayName = findViewById(R.id.displayName);
            displayName.setText(ProfileData.name);
        });
    }

    @Override
    public void UserBMIListener(UserBMIData UserData) {


    }

    @Override
    public void ProfileCreatedListener(boolean Success) {

    }

    public void updateUserProfile(View view) {
        presenter.view.ShowUpdateProfileActivity();
    }
}