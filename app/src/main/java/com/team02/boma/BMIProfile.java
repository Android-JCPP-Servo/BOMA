package com.team02.boma;

import java.util.ArrayList;
import java.util.List;

public class BMIProfile{
    public String name;
    public String gender;
    int age;
    float lastHeight;
    float lastWeight;
    float lastBMI;
    public List<BMIDataChunk> data;

    public BMIProfile() {
        name = "";
        data = new ArrayList<>();
    }
}
