package com.team02.boma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

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

        // Call the Recommendations method
        recommendations();
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
            String userBMI = String.format("%.1f", ProfileData.lastBMI);
            textViewBMI.setText(userBMI);
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

    //this is the look up table for recommendations
    public void recommendations(){
        TextView tvIdeas =  findViewById(R.id.ideas);

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "Drinking water");
        map.put(2, "Jogging for 10 minutes");
        map.put(3, "Learning to do a front flip");
        map.put(4, "Selling your soul for weight loss");
        map.put(5, "Violence is my last option");
        map.put(6, "Just feeling sad for yourself");
        map.put(7, "Research & hire a personal trainer");
        map.put(8, "Pokemon Go");
        map.put(9, "Becoming a walking postman");
        map.put(10, "Experimental Laxatives");
        map.put(11, "Jumping-Jacks for 10 minutes");
        map.put(12, "Push ups for 10 minutes");
        map.put(13, "Finding a sport you enjoy doing");
        map.put(14, "Plotting revenge by getting buff");
        map.put(15, "Chicken Training");
        map.put(16, "Participating in a local charity marathon");
        map.put(17, "Engaging in politics, specifically with someone against you");
        map.put(18, "Going on a mission");
        map.put(19, "Not ordering from that fast food place...you know the one");
        map.put(20, "Jogging for 10 minutes");
        // System.out.println(map.get(1));

        // Initialize a Randomizer
        List<String> recommendationList = new ArrayList<>(map.values());
        int randomString = new Random().nextInt(recommendationList.size());
        String randomSuggestion = recommendationList.get(randomString);

        tvIdeas.setText(randomSuggestion);

    }
}