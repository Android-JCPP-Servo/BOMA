package com.example.boma;

import java.util.Date;

public class
BMIDataChunk{
    public float bmi;
    public float inches;
    public float weight;
    public int age;
    public Date day;

    public BMIDataChunk() {
        bmi = 0f;
        inches = 0f;
        weight = 0f;
        age = 0;
        day = new Date();
    }
}
