package com.team02.boma;

import java.util.Date;

public class BMIDataChunk{
    public static int age;
    public float bmi;
    public float inches;
    public float weight;
    public Date day;

    public BMIDataChunk() {
        bmi = 0f;
        inches = 0f;
        weight = 0f;
        age = 0;
        day = new Date();
    }
}
