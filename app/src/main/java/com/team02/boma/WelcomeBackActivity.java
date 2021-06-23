package com.team02.boma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class WelcomeBackActivity extends AppCompatActivity {

    // Every activity that needs access to the MVP structure needs a reference
    // to the global MVPPresenter
    MVPPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_back);

        // set presenter if it is null
        if (presenter == null){
            presenter = new MVPPresenter(this.getApplication());
        }
    }

    public void updateUserProfile(View view) {
        presenter.view.ShowUpdateProfileActivity();
    }
}