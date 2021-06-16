package com.example.boma;

import java.lang.ref.WeakReference;
import java.util.List;

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



    /***
    Synchronized functions will be called from other threads
    ***/

    /**
     * ProfileNamesFromModel
     * The Model can send Profile names to the Presenter with this function
     * ProfileNames is a List of Strings containing each profile name
     * @param ProfileNames
     */
    synchronized public void ProfileNamesFromModel(List<String> ProfileNames){

    }

    synchronized public void ProfileDataFromModel(BMIProfile ProfileData){

    }
}
