package com.example.boma;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/* The MVPPresenter manages communication with the MVPModel and MVPView
*  MVPPresenter is responsible for creating and running threads to the model
*    and view as needed.
 */
public class MVPPresenter implements ModelToPresenter{

    public MVPModel model;
    public MVPView view;
    private final WeakReference<MainActivity> activity;

    /**
     * MVPPresenter
     * This constructor will set the data for the Presenter, Model, and View to communicate
     * with each other.
     * @param activity   // activity is a handle to the MainActivity
     */
    public MVPPresenter(MainActivity activity) {
        // create a weak reference for the main activity
        this.activity = new WeakReference<>(activity);

        // Create a MVPModal object and pass the Presenter handle
        model = new MVPModel(this, this.activity);

        // create a MVPView object and pass the Presenter and MainActivity handle
        //  The MainActivity handle is needed to display other activities/layouts
        view = new MVPView(this, this.activity);

        //tell the MVPModel to load any saved data
        Thread thread=new Thread(model::LoadProfileData);
        // Start the new thread to request profile names
        thread.start();

    }

    /**
     * RequestProfileNames
     * When called, the Presenter will request a List of profile names from the model
     * For multithreading, the model will return the List of profile names to the
     * ProfileNamesFromModel() member function
     */
    public void RequestProfileNames(){
        // Create a new thread for the Model object request
        Thread thread=new Thread(model::RequestProfileNames);
        // Start the new thread to request profile names
        thread.start();
    }


    public void CreateProfile(String ProfileName){

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
        model.ProfileName = ProfileName;
        // Start the new thread to request profile names
        thread.start();
    }


    public void RequestBMI(UserBMIData UserData){
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
        model.ProfileName = UserData.ProfileName;
        model.Gender = UserData.Gender;
        model.Height = UserData.Height;
        model.Weight = UserData.Weight;

        // Start the new thread to request BMI calculation
        thread.start();
    }


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

        /* // Testing: This will display the profile names
        for (String Name: ProfileNames) {
            System.out.println(String.format("========%s========", Name));
        }
        */

    }

    @Override
    synchronized public void ProfileDataFromModel(BMIProfile ProfileData){


    }

    @Override
    synchronized public void RequestedBMIFromModel(UserBMIData UserData){


    }



}
