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

import pl.droidsonroids.gif.GifImageView;

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


        //tempDialString is used for the dial output text
        String tempDialString = getString(R.string.dial_underweight);

        //tempDialColor is used for the dial output text color
        // defaults to underweight color
        int tempDialColor = getResources().getColor(R.color.dial_underweight);

        // Set the angle of rotation for the animated gif based on BMI
        //  These are underweight values
        float tempAngle = 36.0f;
        float offset = ProfileData.lastBMI / 18.5f * 72f;

        // Set Normal weight values
        if(ProfileData.lastBMI >= 18.5f){
            tempAngle = 324f;
            offset = (ProfileData.lastBMI - 18.5f) / 6.5f * 72f;
            tempDialString = getString(R.string.dial_normal);
            tempDialColor = getResources().getColor(R.color.dial_normal);
        }

        // Set Overweight values
        if(ProfileData.lastBMI >= 25.0f){
            tempAngle = 252f;
            offset = (ProfileData.lastBMI - 25.0f) / 5f * 72f;
            tempDialString = getString(R.string.dial_overweight);
            tempDialColor = getResources().getColor(R.color.dial_overweight);
        }

        // Set Obese values
        if(ProfileData.lastBMI >= 30.0f){
            tempAngle = 180f;
            offset = (ProfileData.lastBMI - 30.0f) / 10f * 72f;
            tempDialString = getString(R.string.dial_obese);
            tempDialColor = getResources().getColor(R.color.dial_obese);
        }

        // Set Morbidly Obese values
        if(ProfileData.lastBMI >= 40.0f){
            tempAngle = 72f;
            offset = 0;
            tempDialString = getString(R.string.dial_morbidly_obese);
            tempDialColor = getResources().getColor(R.color.dial_morbidly_obese);
        }

        // set final variables for use in separate threads
        final float angle = tempAngle - offset;
        final String DialString = tempDialString;
        final int DialColor = tempDialColor;

        // Since the message is in a TextView, run it on the UiThread
        runOnUiThread(() -> {
            TextView textViewBMI = findViewById(R.id.textViewBMI);
            String userBMI = String.format("%.1f", ProfileData.lastBMI);
            textViewBMI.setText(userBMI);
            // Rotate dial image
            GifImageView imageView = (GifImageView) findViewById(R.id.image_dial);
            imageView.setRotation(angle);

            // set the rotation of the spacer to 0. This will change the Z-order
            imageView = (GifImageView) findViewById(R.id.image_spacer);
            imageView.setRotation(0);

            // set the rotation of the needle to 0. This will change the Z-order
            imageView = (GifImageView) findViewById(R.id.image_needle);
            imageView.setRotation(0);
        });

        // create a new thread and execute it after a five second delay
        Thread thread=new Thread(()->{
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Set the dial indicator text and color on the UI thread
            runOnUiThread(() ->{
                TextView tvMeter = findViewById(R.id.textViewMeter);
                tvMeter.setTextColor(DialColor);
                tvMeter.setText(DialString);
            });

        });

        // Start the new thread to set the dial text and text color
        thread.start();


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