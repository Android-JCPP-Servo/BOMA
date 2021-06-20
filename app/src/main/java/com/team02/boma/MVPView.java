package com.team02.boma;

import android.content.Intent;

import java.lang.ref.WeakReference;
import java.util.List;

public class MVPView implements PresenterToView{

    // Store a handle to the Presenter for requesting and sending data
    MVPPresenter presenter;
    // Store a handle to the Activity // required for changing activities/intents
    WeakReference<MainActivity> activity;
    // Store a view as a listener
    private MVPListener listener;

    // To help with multithreading, a new object must be created with a handle to the Presenter
    public MVPView(MVPPresenter presenter, WeakReference<MainActivity> activity) {
        this.presenter = presenter;
        this.activity = activity;
    }

    /*
    These methods display Activities
     */

    /**
     * ShowSaveNewInfoActivity
     * Used to display the save_new_info activity
     */
    public void ShowSaveNewInfoActivity(){
        if(activity.get() != null)
        {
            Intent intent = new Intent(activity.get(), SaveNewInfoActivity.class);
            activity.get().startActivity(intent);
        }
    }

    /**
     * ShowUpdateProfileActivity
     * Used to display the save_new_info activity
     */
    public void ShowUpdateProfileActivity(){
        if(activity.get() != null)
        {
            Intent intent = new Intent(activity.get(), UpdateProfileActivity.class);
            activity.get().startActivity(intent);
        }
    }

    /**
     * ShowWelcomeBackActivity
     * Used to display the save_new_info activity
     */
    public void ShowWelcomeBackActivity(){
        if(activity.get() != null)
        {
            Intent intent = new Intent(activity.get(), WelcomeBackActivity.class);
            activity.get().startActivity(intent);
        }
    }


    /*
    These methods send data to the presenter
     */


    public void RequestProfileNames(MVPListener listener){
        // store the listener to notify an activity when data is ready
        this.listener = listener;

        // ask the presenter to get the profile names
        presenter.RequestProfileNames();
    }

    public void RequestProfileData(MVPListener listener, String ProfileName){
        // store the listener to notify an activity when data is ready
        this.listener = listener;

        //ask the presenter to get the profile data
        presenter.RequestProfileData(ProfileName);
    }

    public void CreateProfile(String ProfileName){
        //ask the presenter to create a profile
        presenter.CreateProfile(ProfileName);
    }

    public void DeleteProfile(String ProfileName){
        //ask the presenter to delete a profile
        presenter.DeleteProfile(ProfileName);
    }

    public void RequestBMI(MVPListener listener, UserBMIData UserData){
        // store the listener to notify an activity when data is ready
        this.listener = listener;

        //ask the presenter to get the BMI info
        presenter.RequestBMI(UserData);
    }

    /*
    These methods receive data from the presenter and send the data to the Activities
    with a listener.
     */

    @Override
    synchronized public void ProfileNamesFromPresenter(List<String> ProfileNames) {
        // exit if the listener is null
        if(this.listener == null) return;

        // Send the profile names to the activity
        this.listener.ProfileNamesListener(ProfileNames);
    }

    @Override
    synchronized public void ProfileDataFromPresenter(BMIProfile ProfileData) {
        // exit if the listener is null
        if(this.listener == null) return;

        // Send the profile data to the activity
        this.listener.ProfileDataListener(ProfileData);
    }

    @Override
    synchronized public void RequestedBMIFromPresenter(UserBMIData UserData) {
        // exit if the listener is null
        if(this.listener == null) return;

        // Send the profile data to the activity
        this.listener.UserBMIListener(UserData);
    }
}
