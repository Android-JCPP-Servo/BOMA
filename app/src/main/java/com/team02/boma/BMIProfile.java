package com.team02.boma;

import java.util.ArrayList;
import java.util.List;

/**
 * BMIProfile stores all the information about a user profile
 */
public class BMIProfile{
    public String name;         // Profile name
    public String gender;       // gender
    int age;                    // age
    float lastHeight;           // Last entered Height
    float lastWeight;           // Last entered Weight
    float lastBMI;              // Last calculated BMI
    public List<BMIDataChunk> data; // array of prior BMI calculations

    // initialize the member variables
    public BMIProfile() {
        name = "";
        data = new ArrayList<>();
    }
}
