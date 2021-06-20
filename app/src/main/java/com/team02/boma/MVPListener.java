package com.team02.boma;

import java.util.List;

/*
An activity class should implement this interface to use Listeners for the MVP data
 */


public interface MVPListener {
    // you can define any parameter as per your requirement
    public void ProfileNamesListener(List<String> profileNames);
    public void ProfileDataListener(BMIProfile ProfileData);
    public void UserBMIListener(UserBMIData UserData);
}
