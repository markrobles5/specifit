package com.example.ivanwl.specifit;

import java.util.HashMap;

public class BMR {
    private Double bmr = 0.0;

    public BMR(HashMap<String, Object> settings){
        if(settings.get("sex").toString().equals("female")){
            bmr = 655 + (4.3 * Double.valueOf(settings.get("Weight").toString())) + (4.7 * Double.valueOf(settings.get("Height").toString())) - (4.7 * Double.valueOf(settings.get("Age").toString()));
        }
        else{
            bmr = 66 + (6.3 * Double.valueOf(settings.get("Weight").toString())) + (12.9 * Double.valueOf(settings.get("Height").toString())) - (6.8 * Double.valueOf(settings.get("Age").toString()));
        }
        bmr *= Double.valueOf(settings.get("Activity").toString());
    }

    public Double getBMR(){
        return bmr;
    }
}
