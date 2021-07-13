package com.team02.boma;

/**
 * ViewToPresenter
 * Interface for the View to send data to the Presenter
 */

public interface ViewToPresenter {

    /**
     * RequestProfileNames()
     * Send a request to the Presenter for a list of profile names
     */
    void RequestProfileNames();

    /**
     * RequestProfileData(String ProfileName)
     * Send a request to the Presenter for a all the profile data from a a profile name.
     * @param ProfileName   // Name of profile for requested data.
     */
    void RequestProfileData(String ProfileName);

    /**
     * CreateProfile(UserBMIData UserData)
     * Send a request to the Presenter to create a new profile
     * @param UserData  // User data to create a new profile
     */
    void CreateProfile(UserBMIData UserData);

    /**
     * DeleteProfile(String ProfileName)
     * Send a request to the Presenter to delete a profile
     * @param ProfileName   // Name of profile to delete
     */
    void DeleteProfile(String ProfileName);

    /**
     * RequestBMI(UserBMIData UserData)
     * Send a request to the Presenter to calculate the BMI
     * @param UserData  // UserBMIData object with profile information for BMI calculation
     */
    void RequestBMI(UserBMIData UserData);

}
