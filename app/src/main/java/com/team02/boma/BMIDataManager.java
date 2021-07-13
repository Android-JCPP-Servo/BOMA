package com.team02.boma;

import android.app.Application;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * BMIDataManager is used to construct, save, and load all the profile data
 * MVPModel will be responsible for inserting and removing data from this class/object
 */
public class BMIDataManager{

    // activity == reference to an activity to receive status messages when saving and loading data
    private final WeakReference<Application> application;

    // List of profiles. Each profile will contain a list of BMIDataChunks
    public BMIAllProfiles allProfiles;

    // Constructor needs the MainActivity to send saving and loading status messages.
    public BMIDataManager(WeakReference<Application> app) {
        this.application = app;
        allProfiles = new BMIAllProfiles();
    }

    /**
     * SaveData() will serialize the allProfiles member variable and save it to the
     * shared preferences file
     */
    public void SaveData(){
        // get the application context.
        Application app = this.application.get();
        //Do we have a valid context? it's needed to load data
        if(app == null) return;

        // Serialize the data to a string/json using gson
        Gson gson = new Gson();
        String serializedData = gson.toJson(allProfiles);

        //This is used to test the loaded data structure
        // System.out.println(serializedData);

        //Get a shared preferences file handle
        SharedPreferences sharedPref = app.getSharedPreferences("com.team02.boma_preferences", Application.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("BOMA_Save_Data", serializedData);

        // write all the data to the shared preferences file
        editor.apply();
    }

    /**
     * LoadData() will get the serialize data from the shared preferences file and load the
     * allProfiles member variable.
     */
    public void LoadData(){

        // get the application context.
        Application app = this.application.get();
        //Do we have a valid context? it's needed to load data
        if(app == null) return;

        //Get a shared preferences file handle
        SharedPreferences sharedPref = app.getSharedPreferences("com.team02.boma_preferences", Application.MODE_PRIVATE);
        String serializedData = sharedPref.getString("BOMA_Save_Data", "");

        // Serialize the data to a string/json using gson
        Gson gson = new Gson();
        allProfiles = gson.fromJson(serializedData, BMIAllProfiles.class);

        //This is used to test the loaded data structure
        // System.out.println(serializedData);

        // Make sure there is a valid structure for the data
        if(allProfiles == null){
            allProfiles = new BMIAllProfiles();
        }
        if(allProfiles.profile == null){
            allProfiles.profile = new ArrayList<>();
        }
    }
    
}
