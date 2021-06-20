package com.example.boma;

import java.util.List;

public interface PresenterToView {
    void ProfileNamesFromPresenter(List<String> ProfileNames);
    void ProfileDataFromPresenter(BMIProfile ProfileData);
    void RequestedBMIFromPresenter(UserBMIData UserData);
}
