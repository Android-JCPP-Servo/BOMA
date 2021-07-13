package com.team02.boma;

import java.util.List;

/**
 * Interface to communication from the MVPModel to the MVPPresenter
 */
public interface ModelToPresenter {
    // Model to Presenter functions will need to use
    //    synchronized
    //  for the implementation.

    /**
     * ProfileNamesFromModel(List<String> ProfileNames)
     * MVPModel will send a List of profile names to this interface.
     * @param ProfileNames  // List of profile names
     */
    void ProfileNamesFromModel(List<String> ProfileNames);

    /**
     * ProfileDataFromModel(BMIProfile ProfileData)
     * MVPModel will send a BMIProfile object to this interface.
     * @param ProfileData   // BMIProfile object with profile data
     */
    void ProfileDataFromModel(BMIProfile ProfileData);

    /**
     * RequestedBMIFromModel(UserBMIData UserData)
     * MVPModel will send a UserBMIData object to this interface with the BMI calculation.
     * @param UserData  // UserBMIData object that contains user data
     */
    void RequestedBMIFromModel(UserBMIData UserData);

    /**
     * ProfileCreatedFromModel(boolean Success)
     * MVPModel will send a true or false when trying to create a profile
     * @param Success   // true or false when a profile is created.
     */
    void ProfileCreatedFromModel(boolean Success);

    /**
     * ProfileDeletedFromModel(boolean Success)
     * MVPModel will send a true or false when trying to delete a profile
     * @param Success   // true or false when a profile is deleted.
     */
    void ProfileDeletedFromModel(boolean Success);
}

