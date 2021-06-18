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

        // Create an MVPPresenter if it is needed
        //  Pass a handle to the MainActivity
        if (presenter == null) {
            presenter = new MVPPresenter(this);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Code to display a different page
    public void showProfile(View view) {
        presenter.view.ShowSaveNewInfoActivity();
    }
}