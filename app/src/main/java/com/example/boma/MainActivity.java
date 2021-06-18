package com.example.boma;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**************************
 * NOTE: Need to move Spinner
 * to file used for saving new
 * user profile.
 **************************/

public class MainActivity extends AppCompatActivity {
    MVPPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // access global variables
        Application globals =(Application)getApplication();

        // Create a global MVPPresenter if it is needed
        //  Pass a handle to the MainActivity
        if (globals.getPresenter() == null) {
            this.presenter = new MVPPresenter(this);
            globals.setMainActivity(this);
        }
        // set presenter to the global presenter
        if (presenter == null){
            presenter = globals.getPresenter();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Code to display a different page
    public void showProfile(View view) {
        presenter.view.ShowSaveNewInfoActivity();
    }
}