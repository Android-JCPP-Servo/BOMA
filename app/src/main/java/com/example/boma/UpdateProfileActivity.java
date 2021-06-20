package com.example.boma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class UpdateProfileActivity extends AppCompatActivity {

    // Every activity that needs access to the MVP structure needs a reference
    // to the global MVPPresenter
    MVPPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);

        // access global variables
        Application globals =(Application)getApplication();
        // set presenter to the global presenter
        if (presenter == null){
            presenter = globals.getPresenter();
        }

    }
}