package com.team02.boma;

/**
 * Interface to communication from the MVPPresenter to the MVPModel
 * The presenter must call each interface with a new thread.
 */
public interface PresenterToModel {

    /*
    For each of these interfaces, parameters must be removed.
    This is needed because threads are not created for a function with arguments/parameters.
    The implementation will need to use member variables instead of parameters.
     */

    /**
     * RequestProfileNames()
     * Request a list of profile names from the model
     * The requested data is pushed to
     *  ProfileNamesFromModel(List<String> ProfileNames);
     */
    void RequestProfileNames();

    /**
     * RequestProfileData()
     * Request all the stored data for a specific profile.
     * The requested data is pushed to
     *  ProfileDataFromModel(BMIProfile ProfileData)
     */
    void RequestProfileData();

    /**
     * CreateProfile()
     * Create a profile; RequestBMI() may also be used to create a profile
     * The creation status is pushed to
     *  ProfileCreatedFromModel(boolean Success)
     */
    void CreateProfile();

    /**
     * DeleteProfile()
     * Delete a profile
     * The deletion status is pushed to
     *  ProfileDeletedFromModel(boolean Success)
     */
    void DeleteProfile();

    /**
     * RequestBMI()
     * Request a BMI calculation from the model.
     * If the profile doesn't exist, it will be created.
     * The requested data is pushed to
     *  RequestedBMIFromModel(UserBMIData UserData)
     */
    void RequestBMI();
}


