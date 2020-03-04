package com.example.ivanwl.specifit;

public class CalorieCounter {
    private double calories;

    public CalorieCounter(double cal) {
        calories = cal;
    }
    public double removeCalories(double cal) {
        calories -= cal;
        return calories;
    }
    public double getCalories(){
        return calories;
    }
    public double setCalories(double cal){
        calories = cal;
        return calories;
    }

}
