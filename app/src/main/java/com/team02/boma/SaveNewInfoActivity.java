package com.team02.boma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class SaveNewInfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, MVPListener{

    // Every activity needs a MVPPresenter object
    MVPPresenter presenter;
    UserBMIData UserData;

    // Establish Gender Array
    String[] genderList = { "(Select a Gender)", "Male", "Female", "Other" };

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
        SpinnerAdapter adapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, genderList);
        // ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        // Set the listener for spinner selection
        spinner.setOnItemSelectedListener(this);

        // Modify the EditText and Spinner objects within the save_new_info.xml layout
        //  Referenced from: https://github.com/macbeth-byui/ConstraintLayoutDemo/blob/main/app/src/main/java/t/macbeth/constraintlayoutdemo/MainActivity.java
        ConstraintLayout layout = findViewById(R.id.new_profile_layout);
        layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            // Establish the Global Listener
            @Override
            public void onGlobalLayout() {
                // Initialize the EditText objects
                EditText editTextName = findViewById(R.id.editTextName);
                EditText editTextFeet = findViewById(R.id.editTextFeet);
                EditText editTextInches = findViewById(R.id.editTextInches);
                EditText editTextWeight = findViewById(R.id.editTextWeight);
                EditText editTextAge = findViewById(R.id.editTextAge);

                // Establish new font size for EditText objects - set font size to half the object height
                editTextName.setTextSize(TypedValue.COMPLEX_UNIT_PX, Math.round(editTextName.getMeasuredHeight()*0.5));
                editTextFeet.setTextSize(TypedValue.COMPLEX_UNIT_PX, Math.round(editTextFeet.getMeasuredHeight()*0.5));
                editTextInches.setTextSize(TypedValue.COMPLEX_UNIT_PX, Math.round(editTextInches.getMeasuredHeight()*0.5));
                editTextWeight.setTextSize(TypedValue.COMPLEX_UNIT_PX, Math.round(editTextWeight.getMeasuredHeight()*0.5));
                editTextAge.setTextSize(TypedValue.COMPLEX_UNIT_PX, Math.round(editTextAge.getMeasuredHeight()*0.5));

                // Perform this action once, so remove the listener
                layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private class SpinnerAdapter extends ArrayAdapter<String> {
        Context context;
        String[] genders = new String[] {};

        public SpinnerAdapter(final Context context, final int textViewResourceId, final String[] objects) {
            super(context, textViewResourceId, objects);
            this.context = context;
            this.genders = objects;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
            }
            TextView tv = convertView.findViewById(android.R.id.text1);
            tv.setText(genders[position]);
            tv.setTextColor(Color.BLACK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tv.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            }
            tv.setTextSize(25);
            return convertView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
            }
            TextView tv = convertView.findViewById(android.R.id.text1);
            tv.setText(genders[position]);
            tv.setTextColor(Color.BLACK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tv.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            }
            tv.setTextSize(25);
            return convertView;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

        // Call the Spinner to adjust primary display size
        Spinner spinner = findViewById(R.id.spinnerGender);

        // There is an occasional crash when the activity has ended, but the spinner calls onItemSelected
        // check for a null adapterView and exit
        if(adapterView.getChildAt(0) == null){
            return;
        }

        // Change Spinner size and color
        // Referenced from: https://stackoverflow.com/questions/9476665/how-to-change-spinner-text-size-and-text-color
        ((TextView)adapterView.getChildAt(0)).setTextColor(Color.rgb(255, 255, 255));
        // Text size adjustment could be done better,
        // but this is the best way I could find so far
        // in auto-fitting the Spinner text size.
        ((TextView)adapterView.getChildAt(0)).setTextSize(Math.round(spinner.getMeasuredHeight()*0.225));
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

        /*
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

        // Display a message indicated the user's profile was created
        Toast.makeText(SaveNewInfoActivity.this, "BMI Profile Created", Toast.LENGTH_LONG).show();
    }

    /**
     * ProfileCreatedListener
     * When MVPView.CreateProfile() is called, this listener receives the status of Success
     * @param Success  // true if a profile was created
     */

    @Override
    public void ProfileCreatedListener(boolean Success) {
        if(Success){

            // A profile has been created. Process the first BMI calculations
            presenter.view.RequestBMI(this, this.UserData);

            //Display an activity that shows the calculated BMI
            // Remember, run anything that changes the UI on the UI thread
            runOnUiThread(() -> presenter.view.ShowBMIResultsActivity(this.UserData.ProfileName));
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
    public void ProfileDeletedListener(boolean Success) {

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