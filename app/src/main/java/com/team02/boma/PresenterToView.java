package com.team02.boma;

import java.util.List;

/**
 * PresenterToView
 * Interface for the Presenter to send data to the View
 */
public interface PresenterToView {

    /**
     * ProfileNamesFromPresenter(List<String> ProfileNames)
     * A List of profile names is pushed to this interface from the Presenter
     * when a list of profile names is requested from RequestProfileNames()
     * @param ProfileNames // A list of all the profile names
     */
    void ProfileNamesFromPresenter(List<String> ProfileNames);

    /**
     * A BMIProfile object is pushed to this interface from the Presenter
     * when profile data is requested from RequestProfileData(String ProfileName)
     * @param ProfileData // a BMIProfile object with all the profile information.
     */
    void ProfileDataFromPresenter(BMIProfile ProfileData);

    /**
     * RequestedBMIFromPresenter(UserBMIData UserData)
     * A UserBMIData object is pushed to this interface from the Presenter
     * when a BMI calculation is requested from RequestBMI(UserBMIData UserData)
     * @param UserData  // a UserBMIData object with all the profile information.
     */
    void RequestedBMIFromPresenter(UserBMIData UserData);

    /**
     * ProfileCreatedFromPresenter(boolean Success)
     * This receives a status message when a profile is created.
     * @param Success // true or false on creation of a profile.
     */
    void ProfileCreatedFromPresenter(boolean Success);

    /**
     * ProfileDeletedFromPresenter(boolean Success)
     * This receives a status message when a profile is deleted.
     * @param Success   // true or false on deletion of a profile.
     */
    void ProfileDeletedFromPresenter(boolean Success);
}
