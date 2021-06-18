package com.example.boma;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**************************
 * NOTE: Need to move Spinner
 * to file used for saving new
 * user profile.
 **************************/

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    MVPPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Create an MVPPresenter if it is needed
        //  Pass a handle to the MainActivity
        if(presenter == null){
            presenter = new MVPPresenter(this);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), "Gender Selected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}