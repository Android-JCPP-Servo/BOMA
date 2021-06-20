package com.team02.boma;

public interface PresenterToModel {


    /*
    For each of these interfaces, parameters must be removed.
    This is needed because threads cannot be created for a function with arguments/parameters.
    The implementation will need to use member variables instead of parameters.
     */

    void RequestProfileNames();
    void RequestProfileData();
    void CreateProfile();
    void DeleteProfile();
    void RequestBMI();
    /*
    void CreateProfile(String ProfileName);
    void DeleteProfile(String ProfileName);
    void RequestBMI(float Height, float weight);

     */
}


