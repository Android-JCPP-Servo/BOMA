package com.team02.boma;

import android.app.Application;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MVPModel implements PresenterToModel, Runnable{

    // Store a handle to the Presenter for requesting and sending data
    MVPPresenter presenter;

    // Store a handle to the Activity // required for accessing data from default location
    WeakReference<Application> application;

    // BMIDataManager will be used to read and store user profile data
    public BMIDataManager bmiManager;

    // These member variables will be used instead of parameters to the member functions.
    //   This is needed because these functions will be running in a separate thread
    //   and passing parameters into a thread is a little tricky.
    String ProfileName;

    // userData will be used to pass age, weight, height, gender,
    // and profile name into methods/threads
    UserBMIData userData;

    // To help with multithreading, a new object must be created with a handle to the Presenter
    public MVPModel(MVPPresenter presenter, WeakReference<Application> application) {
        this.presenter = presenter;
        this.application = application;
        bmiManager = new BMIDataManager(application);

        this.ProfileName ="";
        this.userData = new UserBMIData();

        // be sure to load the saved data
        this.LoadProfileData();
    }

    /**
     * RequestProfileNames
     * Requests a list of all profile names
     * The list will be sent to the MVPPresenter ProfileNamesFromModel member function
     */
    @Override
    public void RequestProfileNames() {

        List<String> Names = new ArrayList<>();
        List<String> SortedNames = new ArrayList<>();
        String lastProfileName = "Default";

        // Get the last used profile name
        if(bmiManager.allProfiles.LastLoadedProfile != null){
            lastProfileName = bmiManager.allProfiles.LastLoadedProfile;
        }

        // Load the profile names into the list.
        // Exclude the last used profile name
        for (BMIProfile profile: bmiManager.allProfiles.profile) {
            if(!profile.name.equals(lastProfileName)){
                Names.add(profile.name);
            }
        }

        // Sort the list of Strings
        Collections.sort(Names);

        //Set the last used profile name to the front of the sorted list
        SortedNames.add(lastProfileName);
        // append the newly sorted list to the SortedNames list
        SortedNames.addAll(Names);

        // Send the sorted profile names to the MVPPresenter
        // The first name was the last used profile name
        presenter.ProfileNamesFromModel(SortedNames);
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
                return;
            }
        }

    }

    /**
     * CreateProfile
     * Uses the member variable String ProfileName as a parameter
     * Creates a new profile name
     */
    /*
    todo: Create a member function for the MVPModel to receive any error messages
     */
    @Override
    public void CreateProfile() {

        String profileName = this.userData.ProfileName;

        // search the list to see if the profile already exists
        if(!bmiManager.allProfiles.profile.isEmpty()) {
            for (BMIProfile profile : bmiManager.allProfiles.profile) {
                // if the profile name is found, send the BMIProfile data to the Presenter
                if (profile.name.equals(profileName)) {
                    // notify the presenter that the profile already exists
                    presenter.ProfileCreatedFromModel(false);

                    // Profile already exists. Exit function
                    return;
                }
            }
        }

        // The profile name needs to be created and added to the bmiManager
        BMIProfile newProfile = new BMIProfile();
        newProfile.name = profileName;

        bmiManager.allProfiles.profile.add(newProfile);

        // notify the presenter that the profile has been created
        presenter.ProfileCreatedFromModel(true);
    }

    /**
     * UpdateProfile
     * Uses the member variable String ProfileName as a parameter
     * Updates an existing profile of user's choice
     */
    /*
    @Override
    public void UpdateProfile() {
        // TODO: Call the last loaded profile information for height (i.e. feet & inches), weight, and age
        presenter.view.RequestBMI();
    }

     */

    /**
     * CreateProfile
     * Uses the member variable String ProfileName as a parameter
     * Delete a profile from the data
     */
    /*
    todo: Create a member function for the MVPModel to receive any error messages
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

        // calculate the BMI based on pounds and inches
        this.userData.BMI = this.userData.Weight / this.userData.Height / this.userData.Height * 703;

        // Get the date for the sample. Clear the milliseconds, seconds, minutes, and hours.
        //  For storing and viewing BMI data, There should be one stored sample per day.
        //  By adjusting the time stamp to the day, it will be easier to find multiple entries
        // Clear the milliseconds
        long date = (int)(new Date().getTime()) / 1000;
        date *= 1000;

        // Get the time and clear the hours, minutes, and seconds.
        this.userData.date = new Date(date);
        this.userData.date.setHours(0);
        this.userData.date.setMinutes(0);
        this.userData.date.setSeconds(0);

        // Create the user profile // it may already be created.
        this.CreateProfile();

        // Locate the profile object
        for (BMIProfile profile: bmiManager.allProfiles.profile){
            if(profile.name.equals(this.userData.ProfileName)){
                // Profile exists.
                //Check for a valid BMIDataChunk
                if(profile.data == null){
                    profile.data = new ArrayList<>();
                }

                //is there at least one item in the BMIDataChunk list?
                if(!profile.data.isEmpty()){
                    // the list has some data; Check for a duplicate daily entry at the end of the list
                    if(profile.data.get(profile.data.size() - 1).day.getTime() == this.userData.date.getTime()){
                        // a duplicate was found; delete it
                        profile.data.remove(profile.data.size() - 1);
                    }
                }

                // add the data to the profile
                BMIDataChunk dataChunk = new BMIDataChunk();
                dataChunk.day = this.userData.date;
                dataChunk.bmi = this.userData.BMI;
                dataChunk.inches = this.userData.Height;
                dataChunk.weight = this.userData.Weight;
                dataChunk.age = this.userData.age;

                profile.data.add(dataChunk);

                // Set the last entered values for the user
                profile.gender = this.userData.Gender;
                profile.age = this.userData.age;
                profile.lastBMI = this.userData.BMI;
                profile.lastHeight = this.userData.Height;
                profile.lastWeight = this.userData.Weight;

                // Enter the last used profile
                this.bmiManager.allProfiles.LastLoadedProfile = this.userData.ProfileName;

                // Save the data to the PreferencesManager
                this.SaveProfileData();

                // Send the data to the Presenter
                presenter.RequestedBMIFromModel(this.userData);

                return;
            }
        }
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
