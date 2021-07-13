package com.team02.boma;

import android.app.Application;

import java.lang.ref.WeakReference;
import java.util.List;

/* The MVPPresenter manages communication with the MVPModel and MVPView
*  MVPPresenter is responsible for creating and running threads to the model
*    and view as needed.
 */
public class MVPPresenter implements ModelToPresenter, ViewToPresenter{

    public MVPModel model;
    public MVPView view;
    public WeakReference<Application> application;

    /**
     * MVPPresenter
     * This constructor will set the data for the Presenter, Model, and View to communicate
     * with each other.
     * @param application   // activity is a handle to the MainActivity
     */
    public MVPPresenter(Application application) {
        // create a weak reference for the main activity
        this.application= new WeakReference<>(application);

        // Create a MVPModal object and pass the Presenter handle
        model = new MVPModel(this, this.application);

        // create a MVPView object and pass the Presenter and MainActivity handle
        //  The MainActivity handle is needed to display other activities/layouts
        view = new MVPView(this, this.application);

        //tell the MVPModel to load any saved data
        Thread thread=new Thread(model::LoadProfileData);
        // Start the new thread to request profile names
        thread.start();
    }

    // ModelToPresenter interface

    /*
    Synchronized functions will be called from other threads
    */

    /**
     * ProfileNamesFromModel
     * The Model can send Profile names to the Presenter with this function
     * ProfileNames is a List of Strings containing each profile name
     * @param ProfileNames // Name of the user profile
     */
    @Override
    synchronized public void ProfileNamesFromModel(List<String> ProfileNames){

        // send the list of profile names to the view
        view.ProfileNamesFromPresenter(ProfileNames);
        /* // Testing: This will display the profile names
        for (String Name: ProfileNames) {
            System.out.println(String.format("========%s========", Name));
        }
        */
    }

    @Override
    synchronized public void ProfileDataFromModel(BMIProfile ProfileData){

        // Send the profile data to the view
        view.ProfileDataFromPresenter(ProfileData);

    }

    @Override
    synchronized public void RequestedBMIFromModel(UserBMIData UserData){

        // Send the BMI data to the view
        view.RequestedBMIFromPresenter(UserData);
    }

    @Override
    public void ProfileCreatedFromModel(boolean Success) {
        view.ProfileCreatedFromPresenter(Success);
    }

    @Override
    public void ProfileDeletedFromModel(boolean Success) {
        view.ProfileDeletedFromPresenter(Success);
    }


    // ViewToPresenter interface

    /**
     * RequestProfileNames
     * When called, the Presenter will request a List of profile names from the model
     * For multithreading, the model will return the List of profile names to the
     * ProfileNamesFromModel() member function
     */

    @Override
    public void RequestProfileNames() {
        // Create a new thread for the Model object request
        Thread thread=new Thread(model::RequestProfileNames);
        // Start the new thread to request profile names
        thread.start();
    }

    @Override
    public void RequestProfileData(String ProfileName) {
        // Sleep to slow down multiple concurrent calls to this function
        // This will avoid a race condition for model.ProfileName = ProfileName;
        // Normal app UI usage should never encounter this problem.
        try{
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create a new thread for the Model object request
        Thread thread=new Thread(model::RequestProfileData);
        model.ProfileName = ProfileName;
        // Start the new thread to request profile names
        thread.start();
    }

    @Override
    public void CreateProfile(UserBMIData UserData) {
        // Sleep to slow down multiple concurrent calls to this function
        // This will avoid a race condition for model.ProfileName = ProfileName;
        // Normal app UI usage should never encounter this problem.
        try{
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create a new thread for the Model object request
        Thread thread=new Thread(model::CreateProfile);
        model.userData = UserData;
        // Start the new thread to request profile names
        thread.start();
    }


    @Override
    public void DeleteProfile(String ProfileName) {
        // Create a new thread for the Model object request
        Thread thread=new Thread(model::DeleteProfile);
        model.ProfileName = ProfileName;
        // Start the new thread to request profile names
        thread.start();
    }

    @Override
    public void RequestBMI(UserBMIData UserData) {
        // Sleep to slow down multiple concurrent calls to this function
        // This will avoid a race condition for
        // model member variables
        // Normal app UI usage should never encounter this problem.
        try{
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create a new thread for the Model object request
        Thread thread=new Thread(model::RequestBMI);

        // Set the parameters for the function
        model.userData = UserData;

        // Start the new thread to request BMI calculation
        thread.start();
    }
}
