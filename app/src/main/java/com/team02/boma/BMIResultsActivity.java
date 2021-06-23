package com.team02.boma;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/***************************
 * NOTES/STEPS TO COMPLETION:
 * -Connect SaveNewInfoActivity to this Activity
 * -Connect UserBMIData to this Activity
 * -Connect bmi_results.xml to this Activity
 ***************************/

public class BMIResultsActivity extends AppCompatActivity {

    // This Activity requires data from the Presenter,
    // so this variable is initialized to access such data.
    MVPPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmi_results);

        // Set presenter if it is null
        if (presenter == null) {
            presenter = new MVPPresenter(this.getApplication());
        }
    }

    // Function for viewing user profile in Welcome Back! page
    public void viewProfile(View view) { presenter.view.ShowWelcomeBackActivity(); }
    // Function for return to BOMA! homepage
    public void goHome(View view) { presenter.view.ShowMainActivity(); }
}
