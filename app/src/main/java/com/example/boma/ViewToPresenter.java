package com.example.boma;

public interface ViewToPresenter {


    /*
    For each of these interfaces, parameters must be removed.
    This is needed because threads cannot be created for a function with arguments/parameters.
    The implementation will need to use member variables instead of parameters.
     */

    void RequestProfileNames();
    void RequestProfileData(String ProfileName);
    void CreateProfile(String ProfileName);
    void DeleteProfile(String ProfileName);
    void RequestBMI(UserBMIData UserData);
    /*
    void CreateProfile(String ProfileName);
    void DeleteProfile(String ProfileName);
    void RequestBMI(float Height, float weight);

     */
}


