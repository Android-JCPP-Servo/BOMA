package com.team02.boma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

public class UpdateProfileActivity extends AppCompatActivity implements MVPListener{

    // Every activity that needs access to the MVP structure needs a reference
    // to the global MVPPresenter
    MVPPresenter presenter;
    String profileName;
    UserBMIData updatedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);

        // set presenter if it is null
        if (presenter == null){
            presenter = new MVPPresenter(this.getApplication());
        }

        TextView textErrorUpdated = findViewById(R.id.textViewErrorUpdated);
        textErrorUpdated.setText("");

        //// allocate a UserBMIData object
        this.updatedData = new UserBMIData();

        // Get the intent that started this activity
        Intent intent = getIntent();

        // Get the profile name that was passed to this intent
        this.profileName = intent.getStringExtra(MVPView.EXTRA_MESSAGE_PROFILE_NAME);

        // request the profile information from the MVP
        //  the result is passed to the ProfileDataListener() as a callback
        presenter.view.RequestProfileData(this, profileName);

        // Modify the EditText and Spinner objects within the update_profile.xml file
        //  Referenced from: https://github.com/macbeth-byui/ConstraintLayoutDemo/blob/main/app/src/main/java/t/macbeth/constraintlayoutdemo/MainActivity.java
        ConstraintLayout layout = findViewById(R.id.update_profile_layout);
        layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            // Establish the Global Listener
            @Override
            public void onGlobalLayout() {
                // Initialize the EditText objects
                EditText updatedFeet = findViewById(R.id.updatedFeet);
                EditText updatedInches = findViewById(R.id.updatedInches);
                EditText updatedWeight = findViewById(R.id.updatedWeight);
                EditText updatedAge = findViewById(R.id.updatedAge);

                // Establish new font size for EditText objects - set font size to half the object height
                updatedFeet.setTextSize(TypedValue.COMPLEX_UNIT_PX, Math.round(updatedFeet.getMeasuredHeight()*0.5));
                updatedInches.setTextSize(TypedValue.COMPLEX_UNIT_PX, Math.round(updatedInches.getMeasuredHeight()*0.5));
                updatedWeight.setTextSize(TypedValue.COMPLEX_UNIT_PX, Math.round(updatedWeight.getMeasuredHeight()*0.5));
                updatedAge.setTextSize(TypedValue.COMPLEX_UNIT_PX, Math.round(updatedAge.getMeasuredHeight()*0.5));

                // Perform this action once, so remove the listener
                layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

    }
    public void saveUpdatedProfile(View view) {

        // Set initial text value for error message
        TextView textErrorUpdated = findViewById(R.id.textViewErrorUpdated);
        textErrorUpdated.setText("");

        // Get all updated values from the user
        TextView updatedFeet = findViewById(R.id.updatedFeet);
        TextView updatedInches = findViewById(R.id.updatedInches);
        TextView updatedWeight = findViewById(R.id.updatedWeight);
        TextView updatedAge = findViewById(R.id.updatedAge);

        // Set initial variables
        int Feet;
        int Inches;
        float Weight;
        int Age;

        /*
         * Check for valid numbers
         */
        // Feet and Inches
        try {
            Feet = Integer.parseInt(updatedFeet.getText().toString());
        } catch (Exception e) {
            textErrorUpdated.setText(getResources().getString(R.string.error_number));
            return;
        }

        // if inches are blank, set to zero
        try{
            Inches = Integer.parseInt(updatedInches.getText().toString());
        } catch (Exception e){
            Inches = 0;
        }

        // Weight
        try {
            Weight = Float.parseFloat(updatedWeight.getText().toString());
        } catch (Exception e) {
            textErrorUpdated.setText(getResources().getString(R.string.error_weight));
            return;
        }

        // Age
        try {
            Age = Integer.parseInt(updatedAge.getText().toString());
        } catch (Exception e) {
            textErrorUpdated.setText(getResources().getString(R.string.error_age));
            return;
        }

        // Load up a UserBMIData object
        this.updatedData.Height = (Feet * 12) + Inches;
        this.updatedData.Weight = Weight;
        this.updatedData.age = Age;

        presenter.view.UpdateProfile(this, this.updatedData);

        // Display a message indicated the user's profile was updated
        Toast.makeText(UpdateProfileActivity.this, "BMI Profile Updated", Toast.LENGTH_LONG).show();

        presenter.view.ShowBMIResultsActivity(profileName);
    }

    @Override
    public void ProfileNamesListener(List<String> profileNames) {

    }

    @Override
    public void ProfileDataListener(BMIProfile ProfileData) {

        // Get the prior profile data to populate un updated data structure
        this.updatedData.ProfileName = ProfileData.name;
        this.updatedData.Height = ProfileData.lastHeight;
        this.updatedData.Weight = ProfileData.lastWeight;
        this.updatedData.Gender = ProfileData.gender;
        this.updatedData.age = ProfileData.age;
        this.updatedData.BMI = ProfileData.lastBMI;

        // Get the resource IDs
        TextView tvFeet =  findViewById(R.id.updatedFeet);
        TextView tvInches =  findViewById(R.id.updatedInches);
        TextView tvWeight =  findViewById(R.id.updatedWeight);
        TextView tvAge =  findViewById(R.id.updatedAge);

        // find the feet and inches
        int feet = (int)(ProfileData.lastHeight / 12);
        int inches = (int)(ProfileData.lastHeight % 12);

        // set the TextView info
        tvFeet.setText(Integer.toString(feet));
        tvInches.setText(Integer.toString(inches));
        // Changed from Integer to Float because some weight scales measure with decimal points
        tvWeight.setText(Float.toString(ProfileData.lastWeight));
        //tvWeight.setText(Integer.toString((int)ProfileData.lastWeight));
        tvAge.setText(Integer.toString(ProfileData.age));

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