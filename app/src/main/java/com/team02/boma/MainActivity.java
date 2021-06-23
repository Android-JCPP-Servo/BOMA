package com.team02.boma;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**************************
 * NOTE: Need to move Spinner
 * to file used for saving new
 * user profile.
 **************************/

public class MainActivity extends AppCompatActivity {

    // Every activity that needs access to the MVP structure needs a reference
    // to the global MVPPresenter
    MVPPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // set presenter if it is null
        if (presenter == null){
            presenter = new MVPPresenter(this.getApplication());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Code to display a different page
    public void showProfile(View view) {
        presenter.view.ShowSaveNewInfoActivity();
    }
}