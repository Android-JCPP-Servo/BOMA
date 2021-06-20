package com.example.boma;

import android.view.View;

/**
 * ViewToPresenter
 * Interface for the View to send data to the Presenter
 */

public interface ViewToPresenter {

    void RequestProfileNames();
    void RequestProfileData(String ProfileName);
    void CreateProfile(String ProfileName);
    void DeleteProfile(String ProfileName);
    void RequestBMI(UserBMIData UserData);

}


