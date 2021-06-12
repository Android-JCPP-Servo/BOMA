package com.example.boma;

import java.lang.ref.WeakReference;

public class MVPView {

    // Store a handle to the Presenter for requesting and sending data
    MVPPresenter presenter;
    // Store a handle to the Activity // required for changing activities/intents
    WeakReference<MainActivity> activity;

    // To help with multithreading, a new object must be created with a handle to the Presenter
    public MVPView(MVPPresenter presenter, WeakReference<MainActivity> activity) {
        this.presenter = presenter;
        this.activity = activity;
    }

    /*
    // This shows how to display a different activity
    public void ShowMainActivity(){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }
     */
}
