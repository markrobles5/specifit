package com.example.ivanwl.specifit;

public class CalorieCounter {
    private double calories;
    private double consumed;

    public CalorieCounter(double cal) {
        calories = cal;
        consumed = 0;
    }
    public void removeCalories(double cal) {
        consumed += cal;
    }
    public double getCalories(){
        return calories - consumed;
    }
    public double getConsumed(){
        return consumed;
    }
    public void updateCalories(double cal){
        calories = cal;
    }

}
