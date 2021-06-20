package com.example.boma;

import android.content.Intent;

import java.lang.ref.WeakReference;
import java.util.List;

public class MVPView implements PresenterToView{

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

    // This shows how to display a different activity
    public void ShowSaveNewInfoActivity(){
        if(activity.get() != null)
        {
            Intent intent = new Intent(activity.get(), SaveNewInfoActivity.class);
           // SaveNewInfoActivity saveNewInfoActivity = intent.getClass();
            activity.get().startActivity(intent);
        }
    }

    @Override
    synchronized public void ProfileNamesFromPresenter(List<String> ProfileNames) {
        // include any code needed to notify a change for an Activity.
    }

    @Override
    synchronized public void ProfileDataFromPresenter(BMIProfile ProfileData) {
        // include any code needed to notify a change for an Activity.
    }

    @Override
    synchronized public void RequestedBMIFromPresenter(UserBMIData UserData) {
        // include any code needed to notify a change for an Activity.
    }
}
