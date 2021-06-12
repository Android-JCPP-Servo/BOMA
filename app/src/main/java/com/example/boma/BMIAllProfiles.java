package com.example.boma;

import java.util.ArrayList;
import java.util.List;

// This is used to group all profiles into a single class for GSON to serialize
// all the data into a string
public class BMIAllProfiles {

    public String LastLoadedProfile;
    public List<BMIProfile> profile;

    public BMIAllProfiles() {
        LastLoadedProfile = "";
        profile = new ArrayList<>();
    }
}
