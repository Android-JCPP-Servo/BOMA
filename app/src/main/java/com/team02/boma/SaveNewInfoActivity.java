package com.team02.boma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SaveNewInfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, MVPListener{

    // Every activity that needs access to the MVP structure needs a reference
    // to the global MVPPresenter
    MVPPresenter presenter;
    UserBMIData UserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_new_info);

        // set presenter if it is null
        if (presenter == null){
            presenter = new MVPPresenter(this.getApplication());
        }

        // clear any displayed error messages
        TextView textViewError = findViewById(R.id.textViewError);
        textViewError.setText("");

        Spinner spinner = findViewById(R.id.spinnerGender);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        // Set the listener for spinner selection
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(getApplicationContext(), "Gender Selected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * buttonSaveProfile
     * Called when the Save Profile button is clicked. This will validate the entered
     * data before processing
     * @param view // view of button
     */

    public void buttonSaveProfile(View view) {

        // Clear any prior error messages
        TextView textViewError = findViewById(R.id.textViewError);
        textViewError.setText("");

        // Get all the entered values for the new profile
        EditText editTextName = findViewById(R.id.editTextName);
        Spinner spinnerGender = findViewById(R.id.spinnerGender);
        EditText editTextFeet = findViewById(R.id.editTextFeet);
        EditText editTextInches = findViewById(R.id.editTextInches);
        EditText editTextWeight = findViewById(R.id.editTextWeight);
        EditText editTextAge = findViewById(R.id.editTextAge);

        // Get the Name and Gender Strings
        String Name = editTextName.getText().toString();
        String Gender = spinnerGender.getSelectedItem().toString();

        // set initial variables
        int Feet;
        float Weight;
        int Age;
        int Inches;

        // check for a non-null profile name
        if(Name.isEmpty()){
            textViewError.setText(getResources().getString(R.string.error_name));
            return;
        }

        // check for a non-null profile name
        if(Gender.isEmpty() || Gender.equals("(Select a Gender)")){
            textViewError.setText(getResources().getString(R.string.error_gender));
            return;
        }

        /**
         * Check for valid numbers
         */
        // Feet and Inches
        try {
            Feet = Integer.parseInt(editTextFeet.getText().toString());
        } catch (Exception e){
            // Show an error message to indicate an invalid number was used
            textViewError.setText(getResources().getString(R.string.error_number));
            return;
        }

        // if inches are blank, set to zero
        try{
            Inches = Integer.parseInt(editTextInches.getText().toString());
        } catch (Exception e){
            Inches = 0;
        }

        // Weight
        try {
            Weight = Float.parseFloat(editTextWeight.getText().toString());
        } catch (Exception e) {
            textViewError.setText(getResources().getString(R.string.error_weight));
            return;
        }

        // Age
        try {
            Age = Integer.parseInt(editTextAge.getText().toString());
        } catch (Exception e) {
            textViewError.setText(getResources().getString(R.string.error_age));
            return;
        }

        // Load up a UserBMIData object
        this.UserData = new UserBMIData();
        this.UserData.ProfileName = Name;
        this.UserData.Gender = Gender;
        this.UserData.Height = (Feet * 12) + Inches;
        this.UserData.Weight = Weight;
        this.UserData.age = Age;

        // Try and create the profile.
        // Use ProfileCreatedListener to check for results.

        presenter.view.CreateProfile(this, this.UserData);
    }

    /**
     * ProfileCreatedListener
     * When MVPView.CreateProfile() is called, this listener receives the status of Success
     * @param Success  // true if a profile was created
     */

    @Override
    public void ProfileCreatedListener(boolean Success) {
        if(Success){
            presenter.view.RequestBMI(this, this.UserData);

            //ToDo: we need to display an activity that shows the calculated BMI
            // Remember, run anything that changes the UI on the UI thread
            runOnUiThread(() -> presenter.view.ShowBMIResultsActivity());
        }

        // If the profile could not be created, display an error
        if(!Success) {

            // Since the message is in a TextView, run it on the UiThread
            runOnUiThread(() -> {
                TextView textViewError = findViewById(R.id.textViewError);
                textViewError.setText(getResources().getString(R.string.error_profile));
            });

        }
    }

    @Override
    public void ProfileNamesListener(List<String> profileNames) {

    }

    @Override
    public void ProfileDataListener(BMIProfile ProfileData) {

    }

    @Override
    public void UserBMIListener(UserBMIData UserData) {

    }


}