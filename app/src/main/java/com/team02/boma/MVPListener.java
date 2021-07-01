package com.team02.boma;

import java.util.List;

/*
An activity class should implement this interface to use Listeners for the MVP data
 */


public interface MVPListener {
    // you can define any parameter as per your requirement
    void ProfileNamesListener(List<String> profileNames);
    void ProfileDataListener(BMIProfile ProfileData);
    void UserBMIListener(UserBMIData UserData);
    void ProfileCreatedListener(boolean Success);
    void ProfileDeletedListener(boolean Success);
}
