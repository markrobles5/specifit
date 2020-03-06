package com.example.ivanwl.specifit;

import android.app.Application;

public class CalorieCounter extends Application {
    private double calories = 0;
    private double consumed = 0;

    public void consumeCalories(double cal) {
        consumed += cal;
    }
    public double getTotalCalories(){return calories;}
    public double getCaloriesRemaining(){
        return calories - consumed;
    }
    public double getConsumed(){
        return consumed;
    }
    public void setCalories(double cal){
        calories = cal;
    }
    public void setConsumed(double cal){
        consumed = cal;
    }

}
