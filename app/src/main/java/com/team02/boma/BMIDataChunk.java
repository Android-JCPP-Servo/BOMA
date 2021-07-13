package com.team02.boma;

import java.util.Date;

/**
 * BMIDataChunk is used to store every BMI calculation into a BMIProfile object
 */
public class BMIDataChunk{
    public int age;
    public float bmi;
    public float inches;
    public float weight;
    public Date day;

    // Initialize all member variables
    public BMIDataChunk() {
        bmi = 0f;
        inches = 0f;
        weight = 0f;
        age = 0;
        day = new Date(); // store the date on creation
    }
}
