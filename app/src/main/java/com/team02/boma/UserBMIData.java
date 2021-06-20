package com.team02.boma;

import java.util.Date;

/**
 *  UserBMIData
 *      This class holds all information for calculating BMI and the returned values
 *      from processing
 */
public class UserBMIData {
    String ProfileName;
    String Gender;
    float Height;   // height in inches
    float Weight;   // weight in pounds
    float BMI;      // calculated BMI
    int age;        // age of profile user
    Date date;      // sample date/time
    String Note;    // Inspirational quote or recommendation

    /**
     * UserBMIData
     *      This container holds data needed to process and return BMI information
     * @param profileName // Name of the User Profile
     * @param gender // Gender
     * @param height // Height
     * @param weight // Weight
     */

    public UserBMIData(String profileName, String gender, float height, float weight) {
        this.ProfileName = profileName;
        this.Gender = gender;
        this.Height = height;
        this.Weight = weight;
        this.BMI = 0f;
        this.age = 0;
        this.date = null;
        this.Note = "";
    }

    /**
     * UserBMIData
     *      Holds data needed to process and return BMI information
     */
    public UserBMIData() {
        this.ProfileName = "";
        this.Gender = "";
        this.Height = 0f;
        this.Weight = 0f;
        this.BMI = 0f;
        this.date = null;
        this.Note = "";
    }
}
