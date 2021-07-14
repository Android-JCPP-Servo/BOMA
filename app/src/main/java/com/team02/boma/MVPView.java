package com.team02.boma;

import android.app.Application;
import android.content.Intent;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * MVPView is used to communicate and interface with the program activities.
 * These methods will propagate information to the MVPPresenter.
 * Information from the MVPPresenter is pushed to various receiving methods whe the data is ready.
 */
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
     * @param ProfileName // Name of profile for activity information
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
     * @param ProfileName // Name of profile for activity information
     */
    public void ShowUpdateProfileActivity(String ProfileName){
        if(application.get() != null)
        {
            Intent intent = new Intent(application.get(), UpdateProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // store the profile name to for the UpdateProfileActivity
            intent.putExtra(EXTRA_MESSAGE_PROFILE_NAME, ProfileName);
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

    /**
     * ShowBMIReferenceAndChartActivity
     * Used to display the activity_bmichart_and_tips activity
     * @param ProfileName // Name of profile for activity information
     */
    public void ShowBMIReferenceAndChartActivity(String ProfileName) {
        if(application.get() != null) {
            Intent intent = new Intent(application.get(), BMIChartAndTipsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // store the profile name to for the UpdateProfileActivity
            intent.putExtra(EXTRA_MESSAGE_PROFILE_NAME, ProfileName);
            application.get().startActivity(intent);
        }
    }

    /**
     * ShowProgressGraphActivity
     * Used to display the activity_progress_graph activity
     * @param ProfileName   // Name of profile for activity information
     */
    public void ShowProgressGraphActivity(String ProfileName) {
        if(application.get() != null) {
            //Intent intent = new Intent(application.get(), ProgressGraphActivity.class);
            /*TODO: this is just a test*/
            Intent intent = new Intent(application.get(), ProgressGraphActivityTest.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // store the profile name to for the UpdateProfileActivity
            intent.putExtra(EXTRA_MESSAGE_PROFILE_NAME, ProfileName);
            application.get().startActivity(intent);
        }
    }


    /*
    These methods send data to the presenter
     */

    /**
     * RequestProfileNames(MVPListener listener)
     * Propagates a request to the MVPModel to send a list of all the stored profile names.
     * The list is sent to the MVPListener.ProfileNamesListener(ProfileNames) interface
     * @param listener // handle to a receiving object when information is ready.
     */
    public void RequestProfileNames(MVPListener listener){
        // store the listener to notify an activity when data is ready
        this.listener = listener;

        // ask the presenter to get the profile names
        presenter.RequestProfileNames();
    }

    /**
     * RequestProfileData(MVPListener listener, String ProfileName)
     * Propagates a request to the MVPModel to send a list of all the saved data from a profile.
     * The list is sent to the MVPListener.ProfileDataListener(ProfileData) interface
     * @param listener // handle to a receiving object when information is ready.
     * @param ProfileName // Profile name associated with the requested data
     */
    public void RequestProfileData(MVPListener listener, String ProfileName){
        // store the listener to notify an activity when data is ready
        this.listener = listener;

        //ask the presenter to get the profile data
        presenter.RequestProfileData(ProfileName);
    }

    /**
     * CreateProfile(MVPListener listener, UserBMIData UserData)
     * Propagates a request to the MVPModel to create a user profile.
     * This method can be replaced with RequestBMI() because RequestBMI() will create a
     * profile if it doesn't exist.
     * A true or false status message is sent to the
     * MVPListener.ProfileCreatedListener(Success) interface
     * @param listener  // handle to a receiving object when information is ready.
     * @param UserData  // UserBMIData object with information about the new profile
     */
    public void CreateProfile(MVPListener listener, UserBMIData UserData){
        // store the listener to notify an activity when data is ready
        this.listener = listener;

        //ask the presenter to create a profile
        presenter.CreateProfile(UserData);
    }

    /**
     * DeleteProfile(MVPListener listener, String ProfileName)
     * Propagates a request to the MVPModel to delete a user profile.
     * A true or false status message is sent to the
     * MVPListener.ProfileDeletedListener(Success) interface
     * @param listener  // handle to a receiving object when information is ready.
     * @param ProfileName   // Profile to delete from the BMIDataManager
     */
    public void DeleteProfile(MVPListener listener, String ProfileName){
        // store the listener to notify an activity when the profile is deleted
        this.listener = listener;

        //ask the presenter to delete a profile
        presenter.DeleteProfile(ProfileName);
    }

    /**
     * RequestBMI(MVPListener listener, UserBMIData UserData)
     * Propagates a request to the MVPModel to calculate the BMI.
     * If the profile doesn't exist, it is created.
     * The profile data is saved to the preference manager and the user profile
     * information is updated.
     * @param listener  // handle to a receiving object when information is ready.
     * @param UserData  // UserBMIData object with information about the profile
     */
    public void RequestBMI(MVPListener listener, UserBMIData UserData){
        // store the listener to notify an activity when data is ready
        this.listener = listener;

        //ask the presenter to get the BMI info
        presenter.RequestBMI(UserData);
    }

    /**
     * UpdateProfile(MVPListener listener, UserBMIData updatedData)
     * This is an alternative to RequestBMI()
     * This method calls RequestBMI()
     * RequestBMI() will update any needed parameters for the associated user profile
     * @param listener  // handle to a receiving object when information is ready.
     * @param updatedData // UserBMIData object with updated profile info
     */
    public void UpdateProfile(MVPListener listener, UserBMIData updatedData) {
        // store the listener to notify an activity when data is ready
        presenter.view.RequestBMI(listener, updatedData);
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

        // Send the BMI data to the activity
        this.listener.UserBMIListener(UserData);
    }

    @Override
    public void ProfileCreatedFromPresenter(boolean Success) {
        // exit if the listener is null
        if(this.listener == null) return;

        // Send the profile creation result to the activity
        this.listener.ProfileCreatedListener(Success);
    }

    @Override
    public void ProfileDeletedFromPresenter(boolean Success) {
        // exit if the listener is null
        if(this.listener == null) return;
        // Send the profile creation result to the activity
        this.listener.ProfileDeletedListener(Success);
    }

}
