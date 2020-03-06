package com.example.ivanwl.specifit;

import java.util.HashMap;

public class BMR {
    private Double bmr = 0.0;
    HashMap<String, Object> settings;

    public BMR(HashMap<String, Object> settings){
        this.settings = settings;

    }

    public Double getBMR(){
        if(this.settings.get("sex").toString().equals("female")){
            bmr = 655 + (4.3 * Double.valueOf(this.settings.get("Weight").toString())) + (4.7 * Double.valueOf(this.settings.get("Height").toString())) - (4.7 * Double.valueOf(this.settings.get("Age").toString()));
        }
        else{
            bmr = 66 + (6.3 * Double.valueOf(this.settings.get("Weight").toString())) + (12.9 * Double.valueOf(this.settings.get("Height").toString())) - (6.8 * Double.valueOf(this.settings.get("Age").toString()));
        }
        bmr *= Double.valueOf(this.settings.get("Activity").toString());

        bmr += 3500 / 7 * Double.valueOf(this.settings.get("Goal").toString());
        return bmr;
    }

    public void update(HashMap<String, Object> settings){
        this.settings = settings;
    }
}
