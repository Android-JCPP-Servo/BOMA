package com.example.boma;

import java.util.ArrayList;
import java.util.List;

public class BMIProfile{
    public String name;
    public List<BMIDataChunk> data;

    public BMIProfile() {
        name = "";
        data = new ArrayList<>();
    }
}
