package com.team02.boma;

/**
 * ViewToPresenter
 * Interface for the View to send data to the Presenter
 */

public interface ViewToPresenter {

    void RequestProfileNames();
    void RequestProfileData(String ProfileName);
    void CreateProfile(UserBMIData UserData);
    void DeleteProfile(String ProfileName);
    void RequestBMI(UserBMIData UserData);

}


