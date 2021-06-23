package com.team02.boma;

import java.util.List;

/**
 * PresenterToView
 * Interface for the Presenter to send data to the View
 */
public interface PresenterToView {
    void ProfileNamesFromPresenter(List<String> ProfileNames);
    void ProfileDataFromPresenter(BMIProfile ProfileData);
    void RequestedBMIFromPresenter(UserBMIData UserData);
    void ProfileCreatedFromPresenter(boolean Success);
}
