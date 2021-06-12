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

    // To help with multithreading, a new object must be created with a handle to the Presenter
    public MVPModel(MVPPresenter presenter, WeakReference<MainActivity> activity) {
        this.presenter = presenter;
        this.activity = activity;
        bmiManager = new BMIDataManager(activity);
    }

    @Override
    public void RequestProfileNames() {

        List<String> Names = new ArrayList<>();
        for (BMIProfile profile: bmiManager.allProfiles.profile) {
            Names.add(profile.name);
        }

        // send the profile names to the MVPPresenter
        presenter.SetProfileNames(Names);
    }

    /*
    For each of these member functions, parameters must be replaced with member variables.
    This is needed because threads cannot be created for a function with arguments/parameters.
     */

    @Override
    public void RequestProfileData(String ProfileName) {

    }

    @Override
    public void CreateProfile(String ProfileName) {

    }

    @Override
    public void DeleteProfile(String ProfileName) {

    }

    @Override
    public void RequestBMI(float Height, float weight) {

    }

    @Override
    // This method should not be called directly.
    //  All threads are executed on specific member functions.
    public void run() {

    }

    public void LoadProfileData(){
        // Load the profile data from the default preferences.
        bmiManager.LoadData();
    }

    public void SaveProfileData(){
        // Save the profile data to the default preferences.
        bmiManager.SaveData();
    }

}
