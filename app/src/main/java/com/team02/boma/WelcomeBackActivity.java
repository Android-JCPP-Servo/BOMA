package com.team02.boma;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WelcomeBackActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, MVPListener {

    // Every activity that needs a MVPPresenter object
    MVPPresenter presenter;

    // Spinner adapter
    ArrayAdapter<String> spinnerAdapter;

    // Be sure to include all profile data
    String profileName;

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
        setContentView(R.layout.welcome_back);

        // set presenter if it is null
        if (presenter == null){
            presenter = new MVPPresenter(this.getApplication());
        }

        // Get a list of all the profile names
        //  The list is returned in a new thread to ProfileNamesListener()
        presenter.view.RequestProfileNames(this);
    }

    // Delete an unwanted or old profile
    public void buttonDeleteProfile(View view) {
        presenter.view.DeleteProfile(this, profileName);
    }

    @Override
    public void ProfileNamesListener(List<String> profileNames) {

        // if there are no profiles, load the page to create a new profile
        if(profileNames.size() == 0){
            presenter.view.ShowSaveNewInfoActivity();
        }

        // Set up the spinner for the profile names
        Spinner spinner = findViewById(R.id.spinnerProfileName);

        // create a new list for the spinner adapter
        List<String> list = new ArrayList<>();
        list.addAll(profileNames);

        // create an adapter for the the profile name spinner
        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // update the Spinner in the UI thread
        runOnUiThread(()->{
            // Set the spinner adapter to the spinner
            spinner.setAdapter(spinnerAdapter);

            // Set the listener for spinner selection
            spinner.setOnItemSelectedListener(this);
            }
        );

    }

    // When a profile is selected from a spinner, this method receives the profile data
    @Override
    public void ProfileDataListener(BMIProfile ProfileData) {
        // Get the resource IDs
        TextView tvGender =  findViewById(R.id.displayGender);
        TextView tvHeight =  findViewById(R.id.displayHeight);
        TextView tvWeight =  findViewById(R.id.displayWeight);
        TextView tvAge =  findViewById(R.id.displayAge);
        TextView tvBMI =  findViewById(R.id.displayBMI);

        // construct the height string
        int feet = (int)(ProfileData.lastHeight / 12);
        int inches = (int)(ProfileData.lastHeight % 12);
        String height = Integer.toString(feet);
        height += "\' ";
        height += Integer.toString(inches);
        height += "\"";
        String finalHeight = height; // without the copy, the compiler displayed an error.

        // Since the message is in a TextView, run it on the UiThread

        runOnUiThread(() -> {
            tvGender.setText(ProfileData.gender);
            tvHeight.setText(finalHeight);
            // Changed from Integer to Float because some weight scales measure with decimal points
            tvWeight.setText(Float.toString(ProfileData.lastWeight) + " lbs.");
            tvAge.setText(Integer.toString(ProfileData.age));
            // Set BMI Display to one decimal place
            String updateBMI = String.format("%.1f", ProfileData.lastBMI);
            tvBMI.setText(updateBMI);
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
        // Get a list of all the profile names
        //  The list is returned in a new thread to ProfileNamesListener()
        presenter.view.RequestProfileNames(this);
    }

    public void updateUserProfile(View view) {
        // set the selected profile name into the intent messages
        presenter.view.ShowUpdateProfileActivity(this.profileName);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        // There is an occasional crash when the activity has ended, but the spinner calls onItemSelected
        // check for a null adapterView and exit
        if((TextView)adapterView.getChildAt(0) == null){
            return;
        }

        // A profile name was selected. Get the profile data for display
        // Change Spinner size and color
        // Referenced from: https://stackoverflow.com/questions/9476665/how-to-change-spinner-text-size-and-text-color
        ((TextView)adapterView.getChildAt(0)).setTextColor(Color.rgb(255, 255, 255));
        ((TextView)adapterView.getChildAt(0)).setTextSize(18);
        this.profileName = (String)adapterView.getSelectedItem();
        presenter.view.RequestProfileData(this, this.profileName);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}