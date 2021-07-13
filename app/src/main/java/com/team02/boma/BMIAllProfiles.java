package com.team02.boma;

import java.util.ArrayList;
import java.util.List;

/**
 * BMIAllProfiles is used to group all profiles into a single class for GSON to serialize
 * all the data into a string
 */
public class BMIAllProfiles {

    public String LastLoadedProfile;    // store the last used profile
    public List<BMIProfile> profile;    // store a list of all the profiles and their data

    // Initialize the member variables
    public BMIAllProfiles() {
        LastLoadedProfile = "";
        profile = new ArrayList<>();
    }
}
