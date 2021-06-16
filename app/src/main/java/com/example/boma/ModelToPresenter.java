package com.example.boma;

import java.util.List;

public interface ModelToPresenter {
    // Model to Presenter functions will need to use
    //    synchronized
    //  for the implementation.
    void ProfileNamesFromModel(List<String> ProfileNames);
    void ProfileDataFromModel(BMIProfile ProfileData);
    void RequestedBMIFromModel(UserBMIData UserData);
}

