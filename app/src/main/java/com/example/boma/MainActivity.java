package com.example.boma;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    MVPPresenter presenter;
    AutoCompleteTextView autocomplete;
    String[] gender = { "Male", "Female", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Create an MVPPresenter if it is needed
        //  Pass a handle to the MainActivity
        if(presenter == null){
            presenter = new MVPPresenter(this);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autocomplete = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, gender);

        autocomplete.setThreshold(2);
        autocomplete.setAdapter(adapter);
    }
}