package com.team02.boma;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WelcomeBackActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, MVPListener {

    // Every activity that needs a MVPPresenter object
    MVPPresenter presenter;

    // Spinner adapter
    // ArrayAdapter<String> spinnerAdapter;
    SpinnerAdapter spinnerAdapter;

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

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.status_bar));

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
        List<String> list = new ArrayList<>(profileNames);

        // create an adapter for the the profile name spinner
        spinnerAdapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, list);

        // update the Spinner in the UI thread
        runOnUiThread(()->{
            // Set the spinner adapter to the spinner
            spinner.setAdapter(spinnerAdapter);

            // Set the listener for spinner selection
            spinner.setOnItemSelectedListener(this);
            }
        );

    }

    /**
     * Setting Spinner font size based on screen size
     * Referenced from: https://stackoverflow.com/questions/4989817/set-the-textsize-to-a-text-in-spinner-in-android-programatically/4990137#4990137
     */
    // Set private class for SpinnerAdapter, initialized previously as Global Variable
    private class SpinnerAdapter extends ArrayAdapter<String> {
        // Initialize context and call ProfileName array
        Context context;
        String[] user_name;

        public SpinnerAdapter(final Context context, final int textViewResourceId, final List<String> objects) {
            super(context, textViewResourceId, objects);
            this.context = context;
            this.user_name = objects.toArray(new String[0]);
        }

        // Establish font size, color, and dimensions of Dropdown View
        @Override
        public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
            // If the View is null, call the Spinner list to set proper dimensions
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            }
            // Begin adjusting Spinner dimensions, font, and color
            TextView tv = convertView.findViewById(android.R.id.text1);
            tv.setText(user_name[position]);
            tv.setTextColor(Color.BLACK);
            tv.setBackgroundColor(Color.WHITE);
            tv.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            // Get screen size for every device - used to help define Spinner font size
            // Referenced from: https://stackoverflow.com/questions/11252067/how-do-i-get-the-screensize-programmatically-in-android#:~:text=Determine%20Screen%20Size%20%3A
            int screenSize = getResources().getConfiguration().screenLayout &Configuration.SCREENLAYOUT_SIZE_MASK;
            switch(screenSize) {
                // If screen size is X-Large, set text size to 40dp
                case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                    tv.setTextSize(40);
                    break;
                // If screen size is Large, set text size to 30dp
                case Configuration.SCREENLAYOUT_SIZE_LARGE:
                    tv.setTextSize(30);
                    break;
                // If screen size is Normal (Medium), set text size to 20dp
                case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                    tv.setTextSize(20);
                    break;
                // If screen size is Small, set text size to 10dp
                case Configuration.SCREENLAYOUT_SIZE_SMALL:
                    tv.setTextSize(10);
                    break;
                // No screen size was determined,
                //  so default text size is 25dp
                default:
                    tv.setTextSize(25);
            }
            // Return the new View for the Spinner
            return convertView;
        }

        // This method follows the getDropDownView method - similar to Inheritance
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            }
            TextView tv = convertView.findViewById(android.R.id.text1);
            tv.setText(user_name[position]);
            tv.setTextColor(Color.BLACK);
            tv.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            int screenSize = getResources().getConfiguration().screenLayout &Configuration.SCREENLAYOUT_SIZE_MASK;
            switch(screenSize) {
                case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                    tv.setTextSize(40);
                    break;
                case Configuration.SCREENLAYOUT_SIZE_LARGE:
                    tv.setTextSize(30);
                    break;
                case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                    tv.setTextSize(20);
                    break;
                case Configuration.SCREENLAYOUT_SIZE_SMALL:
                    tv.setTextSize(10);
                    break;
                default:
                    tv.setTextSize(25);
            }
            return convertView;
        }
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
        height += "' ";
        height += Integer.toString(inches);
        height += "\"";
        String finalHeight = height; // without the copy, the compiler displayed an error.

        // Since the message is in a TextView, run it on the UiThread

        runOnUiThread(() -> {
            tvGender.setText(ProfileData.gender);
            tvHeight.setText(finalHeight);
            // Changed from Integer to Float because some weight scales measure with decimal points
            tvWeight.setText(String.format(Locale.getDefault(),"%.1f lbs.", ProfileData.lastWeight));
            tvAge.setText(String.format(Locale.getDefault(),"%d", ProfileData.age));
            // Set BMI Display to one decimal place
            String updateBMI = String.format(Locale.getDefault(),"%.1f", ProfileData.lastBMI);
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
        if(adapterView.getChildAt(0) == null){
            return;
        }

        // A profile name was selected. Get the profile data for display
        // Change Spinner size and color
        // Referenced from: https://stackoverflow.com/questions/9476665/how-to-change-spinner-text-size-and-text-color
        ((TextView)adapterView.getChildAt(0)).setTextColor(Color.rgb(255, 255, 255));
        this.profileName = (String)adapterView.getSelectedItem();
        presenter.view.RequestProfileData(this, this.profileName);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}