package com.example.boma;

import java.util.Date;

public class BMIDataChunk{
    public float bmi;
    public float inches;
    public float weight;
    public Date day;

    public BMIDataChunk() {
        bmi = 0f;
        inches = 0f;
        weight = 0f;
        day = new Date();
    }
}
