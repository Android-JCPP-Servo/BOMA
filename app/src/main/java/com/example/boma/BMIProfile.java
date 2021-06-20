package com.example.boma;

import java.util.ArrayList;
import java.util.List;

public class BMIProfile{
    public String name;
    public String gender;
    int age;
    float lastHeight;
    float lastWeight;
    public List<BMIDataChunk> data;

    public BMIProfile() {
        name = "";
        data = new ArrayList<>();
    }
}
