package com.example.boma;

import java.util.ArrayList;
import java.util.List;

public interface ModelToPresenter {
    // Model to Presenter functions will need to use
    //    synchronized
    //  for the implementation.
    void ProfileNamesFromModel(List<String> ProfileNames);
    void ProfileDataFromModel(BMIProfile ProfileData);
    void RequestedBMIFromModel(UserBMIData UserData);

    // This is used to group all profiles into a single class for GSON to serialize
    // all the data into a string
    class BMIAllProfiles {

        public String LastLoadedProfile;
        public List<BMIProfile> profile;

        public BMIAllProfiles() {
            LastLoadedProfile = "";
            profile = new ArrayList<>();
        }
    }
}

