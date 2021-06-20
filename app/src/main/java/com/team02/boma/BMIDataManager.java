package com.team02.boma;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class BMIDataManager{

    // activity == reference to an activity to receive status messages when saving and loading data
    private final WeakReference<MainActivity> activity;

    // List of profiles. Each profile will contain a list of BMIDataChunks
    public BMIAllProfiles allProfiles;

    // Constructor needs the MainActivity to send saving and loading status messages.
    public BMIDataManager(WeakReference<MainActivity> activity) {
        this.activity = activity;
        allProfiles = new BMIAllProfiles();
    }

    public void SaveData(){

        // Get the activity
        MainActivity mainActivity = activity.get();

        // If the main activity is null, we cannot save the data. exit function
        if(mainActivity == null) return;

        // Serialize the data to a string/json using gson
        Gson gson = new Gson();
        String serializedData = gson.toJson(allProfiles);
        System.out.println(serializedData);

        //Get a shared preferences file handle
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mainActivity);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("BOMA! Save", serializedData);

        // write all the data to the shared preferences file
        editor.apply();
    }

    public void LoadData(){

        //**** Show a toast ****
        // Get the activity
        MainActivity mainActivity = activity.get();

        //Do we have a valid MainActivity? it's needed to load data
        if(mainActivity == null) return;

        //Get a shared preferences file handle
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(mainActivity);
        String serializedData = sharedPref.getString(mainActivity.getApplicationContext().getResources().getString(R.string.save_string), "");

        // Serialize the data to a string/json using gson
        Gson gson = new Gson();
        allProfiles = gson.fromJson(serializedData, BMIAllProfiles.class);
        System.out.println(serializedData);

        // Make sure there is a valid structure for the data
        if(allProfiles == null){
            allProfiles = new BMIAllProfiles();
        }
        if(allProfiles.profile == null){
            allProfiles.profile = new ArrayList<>();
        }
    }
    

}
