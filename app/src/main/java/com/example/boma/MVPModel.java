package com.example.boma;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MVPModel implements PresenterToModel, Runnable{

    // Store a handle to the Presenter for requesting and sending data
    MVPPresenter presenter;

    // Store a handle to the Activity // required for accessing data from default location
    WeakReference<MainActivity> activity;

    // BMIDataManager will be used to read and store user profile data
    public BMIDataManager bmiManager;

    // These member variables will be used instead of parameters to the member functions.
    //   This is needed because these functions will be running in a separate thread
    //   and passing parameters into a thread is a little tricky.
    String ProfileName;
    float Height;
    float Weight;
    String Gender;



    // To help with multithreading, a new object must be created with a handle to the Presenter
    public MVPModel(MVPPresenter presenter, WeakReference<MainActivity> activity) {
        this.presenter = presenter;
        this.activity = activity;
        bmiManager = new BMIDataManager(activity);

        this.ProfileName ="";
        this.Height = 0;
        this.Weight = 0;
        this.Gender = "";

    }

    /**
     * RequestProfileNames
     * Requests a list of all profile names
     * The list will be sent to the MVPPresenter ProfileNamesFromModel member function
     */
    @Override
    public void RequestProfileNames() {

        List<String> Names = new ArrayList<>();
        for (BMIProfile profile: bmiManager.allProfiles.profile) {
            Names.add(profile.name);
        }

        // send the profile names to the MVPPresenter
        presenter.ProfileNamesFromModel(Names);
    }

    /*
    For each of these member functions, parameters must be replaced with member variables.
    This is needed because threads cannot be created for a function with arguments/parameters.
     */

    /**
     * RequestProfileData
     * Uses the member variable String ProfileName as a parameter
     * Retrieves all saved data associated with the profile name.
     * Calls ProfileDataFromModel(List<BMIProfile> ProfileData) to send the profile data
     */
    @Override
    public void RequestProfileData() {
        // Search the list for the requested profile
        for (BMIProfile profile: bmiManager.allProfiles.profile) {
            // if the profile name is found, send the BMIProfile data to the Presenter
            if(profile.name.equals(this.ProfileName)){
                presenter.ProfileDataFromModel(profile);
            }
        }
    }

    /**
     * CreateProfile
     * Uses the member variable String ProfileName as a parameter
     * Creates a new profile name
     */
    /*
    todo: Create a member function for the MVPModel to recieve any error messages
     */
    @Override
    public void CreateProfile() {

        // search the list to see if the profile already exists
        if(!bmiManager.allProfiles.profile.isEmpty()) {
            for (BMIProfile profile : bmiManager.allProfiles.profile) {
                // if the profile name is found, send the BMIProfile data to the Presenter
                if (profile.name.equals(this.ProfileName)) {
                    // Profile already exists. Exit function
                    return;
                }
            }
        }

        // The profile name needs to be created and added to the bmiManager
        BMIProfile newProfile = new BMIProfile();
        newProfile.name = this.ProfileName;
        bmiManager.allProfiles.profile.add(newProfile);

    }

    /**
     * CreateProfile
     * Uses the member variable String ProfileName as a parameter
     * Delete a profile from the data
     */
    /*
    todo: Create a member function for the MVPModel to recieve any error messages
     */
    @Override
    public void DeleteProfile() {
        // search the list to see if the profile already exists
        for (BMIProfile profile: bmiManager.allProfiles.profile) {
            // if the profile name is found, send the BMIProfile data to the Presenter
            if(profile.name.equals(this.ProfileName)){
                // Profile exists. Delete profile and exit function
                bmiManager.allProfiles.profile.remove(profile);
                return;
            }
        }
    }

    /**
     * RequestBMI
     * Uses the member variables float Height, float Weight as parameters
     * Calculates BMI and sends the data to the Presenter
     */
    @Override
    public void RequestBMI() {
        /*todo: calc BMI*/
        /*todo: save to profile; only save one entry per day*/
        /*todo: set last used profile*/



    }


    // This method should not be called directly.
    //  All threads are executed on specific member functions.
    @Override
    public void run() {

    }


    /**
     * LoadProfileData
     * Loads all the stored data from the PreferenceManager into the bmiManager
     */
    protected void LoadProfileData(){
        // Load the profile data from the default preferences.
        bmiManager.LoadData();
    }

    /**
     * SaveProfileData
     * Saves all the stored data from the the bmiManager into the PreferenceManager.
     */
    protected void SaveProfileData(){
        // Save the profile data to the default preferences.
        bmiManager.SaveData();
    }

}
