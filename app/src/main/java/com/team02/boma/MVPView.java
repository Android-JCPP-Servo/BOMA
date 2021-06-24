package com.team02.boma;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import java.lang.ref.WeakReference;
import java.util.List;

public class MVPView implements PresenterToView{

    // Used to pass a profile name to an intent
    public static final String EXTRA_MESSAGE_PROFILE_NAME = "com.team02.boma.PROFILE_NAME";

    // Store a handle to the Presenter for requesting and sending data
    MVPPresenter presenter;

    // Store a handle to the Application // required for saving and loading data
    WeakReference<Application> application;
    // Store a view as a listener
    private MVPListener listener;

    // To help with multithreading, a new object must be created with a handle to the Presenter
    public MVPView(MVPPresenter presenter, WeakReference<Application> application) {
        this.presenter = presenter;
        this.application = application;
    }

    /*
    These methods display Activities
     */

    /**
     * ShowMainActivity
     * Used when the User wants to go to App Home Screen.
     */
    public void ShowMainActivity() {
        if (application.get() != null) {
            Intent intent = new Intent(application.get(), MainActivity.class);
            // Just in case an error arises...
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            application.get().startActivity(intent);
        }
    }

    /**
     * ShowSaveNewInfoActivity
     * Used to display the save_new_info activity
     */
    public void ShowSaveNewInfoActivity(){

        if(application.get() != null)
        {
            Intent intent = new Intent(application.get(), SaveNewInfoActivity.class);
            // Need to set this flag; otherwise, a flag error will result
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            application.get().startActivity(intent);
        }
    }

    /**
     * ShowBMIResultsActivity
     * Used to display BMI data to bmi_results activity
     */
    public void ShowBMIResultsActivity(String ProfileName) {
        if (application.get() != null) {
            Intent intent = new Intent(application.get(), BMIResultsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // store the profile name to get the BMI data for the BMIResultsActivity
            intent.putExtra(EXTRA_MESSAGE_PROFILE_NAME, ProfileName);
            application.get().startActivity(intent);
        }
    }

    /**
     * ShowUpdateProfileActivity
     * Used to display the save_new_info activity
     */
    public void ShowUpdateProfileActivity(){
        if(application.get() != null)
        {
            Intent intent = new Intent(application.get(), UpdateProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            application.get().startActivity(intent);
        }
    }

    /**
     * ShowWelcomeBackActivity
     * Used to display the save_new_info activity
     */
    public void ShowWelcomeBackActivity(){
        if(application.get() != null)
        {
            Intent intent = new Intent(application.get(), WelcomeBackActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            application.get().startActivity(intent);
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

    public void CreateProfile(MVPListener listener, UserBMIData UserData){
        // store the listener to notify an activity when data is ready
        this.listener = listener;

        //ask the presenter to create a profile
        presenter.CreateProfile(UserData);
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
        // set the listener to null once it has been used.
        this.listener = null;
    }

    @Override
    synchronized public void ProfileDataFromPresenter(BMIProfile ProfileData) {
        // exit if the listener is null
        if(this.listener == null) return;

        // Send the profile data to the activity
        this.listener.ProfileDataListener(ProfileData);
        // set the listener to null once it has been used.
        this.listener = null;
    }

    @Override
    synchronized public void RequestedBMIFromPresenter(UserBMIData UserData) {
        // exit if the listener is null
        if(this.listener == null) return;

        // Send the BMI data to the activity
        this.listener.UserBMIListener(UserData);
        // set the listener to null once it has been used.
        this.listener = null;
    }

    @Override
    public void ProfileCreatedFromPresenter(boolean Success) {
        // exit if the listener is null
        if(this.listener == null) return;

        // Send the profile creation result to the activity
        this.listener.ProfileCreatedListener(Success);
        // set the listener to null once it has been used.
        this.listener = null;
    }
}
